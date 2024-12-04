package com.xu.viewpoint.service.util;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * @author: xuJing
 * @date: 2024/12/3 15:34
 */

@Component
public class FastDFSUtil {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private AppendFileStorageClient appendFileStorageClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String DEFAULT_GROUP_NAME = "group1";

    private static final String PATH_KEY = "path-key";

    private static final String UPLOADED_SIZE_KEY = "uploaded-size-key";

    private static final String UPLOADED_NO_KEY = "uploaded-no-key";

    private static final int SLICE_SIZE = 1024*1024;

    /**
     * 获取文件类型
     * @param file
     */
    public String getFileType(MultipartFile file){
        if(file == null){
            throw new ConditionException("非法文件");
        }
        String filename = file.getOriginalFilename();
        if(StringUtils.isNullOrEmpty(filename)){
            throw new ConditionException("非法文件");
        }
        int index = filename.lastIndexOf(".");
        return filename.substring(index+1);
    }

    /**
     * 一次性上传文件（大文件不适用）
     * @param file
     * @throws IOException
     */
    // 上传文件
    public String uploadCommonFile(MultipartFile file) throws IOException {
        Set<MetaData> metaDataSet = new HashSet<>();
        String fileType = this.getFileType(file);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                file.getSize(),
                fileType,
                metaDataSet);
        return storePath.getPath();
    }

    // 断点上传文件

    /**
     * 分片上传文件（第一次上传）
     * @param file
     * @throws IOException
     */
    public String uploadAppenderFile(MultipartFile file) throws IOException {
        String fileType = this.getFileType(file);
        String filename = file.getOriginalFilename();
        StorePath storePath = appendFileStorageClient.uploadAppenderFile(DEFAULT_GROUP_NAME,
                file.getInputStream(),
                file.getSize(),
                fileType);
        return storePath.getPath();
    }

    /**
     * 分片上传文件（非第一次上传）
     * @param file
     * @param filePath
     * @param offset
     * @throws IOException
     */
    // 断点后续上传
    public void modifyAppenderFile(MultipartFile file, String filePath, long offset) throws IOException {
        appendFileStorageClient.modifyFile(DEFAULT_GROUP_NAME,
                filePath,
                file.getInputStream(),
                file.getSize(),
                offset);
    }

    /**
     * 完整的断点续传功能
     * @param file 文件
     * @param fileMd5 文件的MD5，用于实现秒传
     * @param sliceNo 当前上传的分片文件 =号
     * @param totalSliceNo 文件分片总数
     */
    // 完整的断点续传功能

    public String uploadFileSlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws IOException {
        if(file == null || sliceNo == null || totalSliceNo == null){
            throw new ConditionException("参数异常！");
        }

        String pathKey = PATH_KEY + fileMd5;
        String uploadedSizeKey = UPLOADED_SIZE_KEY + fileMd5;
        String uploadedNoKey = UPLOADED_NO_KEY + fileMd5;

        // 获取已经上传完成的数据大小
        String uploadedSizeStr = redisTemplate.opsForValue().get(uploadedSizeKey);
        Long uploadedSize = 0L;
        if(!StringUtils.isNullOrEmpty(uploadedSizeStr)){
            // 如果不为空，说明当前不是第一次上传，则已经上传的大小赋值给uploadedSize
            uploadedSize = Long.valueOf(uploadedSizeStr);
        }

        // 存储在redis中的表示已经上传完成的信息，例如已经上传完成的大小、分片号、路径

        String fileType = getFileType(file);
        if(sliceNo == 1){
            // 第一个分片，即文件第一次上传
            String path = this.uploadAppenderFile(file);
            System.out.println(path);
            if(StringUtils.isNullOrEmpty(path)){
                throw new ConditionException("上传失败");
            }

            // 第一次上传成功，将上传到FastDFS服务器的存储路径存入到redis中
            redisTemplate.opsForValue().set(pathKey, path);
            // 第一次上传，所以分片完成为1，设置到redis中
            redisTemplate.opsForValue().set(uploadedNoKey, "1");
        }else {
            // 不是第一次上传，从redis中获取之前分片的存储路径
            String filepath = redisTemplate.opsForValue().get(pathKey);
            if(StringUtils.isNullOrEmpty(filepath)){
                throw new ConditionException("上传失败");
            }
            // 断点上传，传入偏移量uploadedSize，也就是已经完成的数据大小
            this.modifyAppenderFile(file, filepath, uploadedSize);
//            this.modifyAppenderFile(file, filepath, file.getSize());

            // 将redis中分片完成数量+1
            redisTemplate.opsForValue().increment(uploadedNoKey);

        }
        // 获取当前分片的文件大小，并将其结果加入到uploadedSize中，并存入redis中，更新当前已完成的上传文件大小
        uploadedSize += file.getSize();
        redisTemplate.opsForValue().set(uploadedSizeKey, String.valueOf(uploadedSize));

        // 判断是否上传完毕，清空redis
        // 获取已经完成的分片数
        String uploadedNoStr = redisTemplate.opsForValue().get(uploadedNoKey);
        if(StringUtils.isNullOrEmpty(uploadedNoStr)){
            throw new ConditionException("上传失败");
        }
        // 如果已经完成的分片数和总的分片数相等，则表明上传完成，此时清空redis中的文件路径、文件已上传大小、文件分片数
        Integer uploadedNo = Integer.valueOf(uploadedNoStr);
        String resultPath = "";
        if(uploadedNo.equals(totalSliceNo)){
            resultPath = redisTemplate.opsForValue().get(pathKey);
            List<String> keyList = Arrays.asList(pathKey, uploadedSizeKey, uploadedNoKey);
            redisTemplate.delete(keyList);
        }
        // 最后一次完成上传时，会返回真正的路径，否则为""
        return resultPath;
    }


    /**
     * 删除文件
     * @param filePath
     */
    // 删除
    public void deleteFile(String filePath){
        fastFileStorageClient.deleteFile(filePath);
    }


    public void convertFileToSlices(MultipartFile multipartFile) throws IOException {
        // 将MultipartFile转换成Java原生File类型
        String filename = multipartFile.getOriginalFilename();
        String fileType = getFileType(multipartFile);
        File file = multipartFileToFile(multipartFile);

        // 获取文件大小，定义计数器
        long length = file.length();
        long count = 1;

        // 每SLICE_SIZE大小做一次循环，也就是将文件分片，每片大小为SLICE_SIZE
        for(long i =0; i<length; i+=SLICE_SIZE){

            // 借助RandomAccessFile获取文件定位读取指定大小
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(i);
            System.out.println(randomAccessFile.getFilePointer());

            byte[] bytes = new byte[SLICE_SIZE];
            int len = randomAccessFile.read(bytes);

            // 将分片的文件写入到指定位置
            String path = "C:\\Users\\86136\\Desktop\\temp\\" + count +"."+ fileType;
            File slice = new File(path);
            FileOutputStream fos = new FileOutputStream(slice);
            fos.write(bytes, 0, len);
            fos.close();
            randomAccessFile.close();
            count++;
        }

        // 删除之前文件
        file.delete();
    }
    public File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");

        String name = originalFilename.substring(0, index);
        String type = originalFilename.substring(index);
        File file = File.createTempFile(name, type);

        multipartFile.transferTo(file);
        return file;
    }








}
