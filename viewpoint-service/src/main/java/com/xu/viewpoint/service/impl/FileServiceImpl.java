//package com.xu.viewpoint.service.impl;
//
//import com.mysql.cj.util.StringUtils;
//import com.xu.viewpoint.dao.FileDao;
//import com.xu.viewpoint.dao.domain.File;
//import com.xu.viewpoint.service.FileService;
//import com.xu.viewpoint.service.util.FastDFSUtil;
//import com.xu.viewpoint.service.util.MD5Util;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Date;
//
///**
// * @author: xuJing
// * @date: 2024/12/4 13:37
// */
//
//@Service
//public class FileServiceImpl implements FileService {
//
//    @Autowired
//    private FastDFSUtil fastDFSUtil;
//
//    @Autowired
//    private FileDao fileDao;
//
//
//
//    /**
//     * 文件分片上传
//     *
//     * @param file
//     * @param fileMd5
//     * @param sliceNo
//     * @param totalSliceNo
//     */
//    @Override
//    public String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws IOException {
//
//        // 1. 根据文件的md5在数据库中查询是否存在对应的文件
//        File dbFile = fileDao.getFileByMD(fileMd5);
//        if(dbFile != null){
//            // 2. 存在对应的文件 直接返回文件在FastDFS服务器中的路径，妙传
//            return dbFile.getUrl();
//        }
//        // 3. 不存在，说明当前文件是第一次上传，则上传到FastDFS服务器中
//        String url = fastDFSUtil.uploadFileSlices(file, fileMd5, sliceNo, totalSliceNo);
//
//        // 4. 上传成功，将所有信息存入到数据库
//        // 4.1 在uploadFileSlices将最后一个分片上传成功，返回的url才有值
//        // 4.2 此时再创建文件对象存入数据库中
//        if(!StringUtils.isNullOrEmpty(url)){
//            dbFile = new File();
//            dbFile.setCreateTime(new Date());
//            dbFile.setMd5(fileMd5);
//            dbFile.setUrl(url);
//            dbFile.setType(fastDFSUtil.getFileType(file));
//            fileDao.addFile(dbFile);
//        }
//
//        return url;
//    }
//
//
//    /**
//     * 获取文件的md5
//     *
//     * @param file
//     */
//    @Override
//    public String getFileMD5(MultipartFile file) throws Exception {
//        return MD5Util.getFileMD5(file);
//    }
//}
