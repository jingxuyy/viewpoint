package com.xu.viewpoint.service.v2.impl;

import com.xu.viewpoint.dao.domain.File;
import com.xu.viewpoint.dao.v2.FileDao;
import com.xu.viewpoint.service.util.FastDFSUtil;
import com.xu.viewpoint.service.util.MD5Util;
import com.xu.viewpoint.service.v2.IFileService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author: xuJing
 * @date: 2025/1/4 16:00
 */
@Service
public class FileService implements IFileService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;


    /**
     * 获取文件的MD5字符串
     *
     * @param file
     */
    @Override
    public String getFileMD5(MultipartFile file) throws Exception {
        return MD5Util.getFileMD5(file);
    }

    /**
     * 文件分片上传
     *
     * @param slice        已经分片的文件片段
     * @param fileMd5      总体文件的MD5码
     * @param sliceNo      当前文件分片的分片号
     * @param totalSliceNo 总的分片数量
     */
    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws IOException {
        File dbFile = fileDao.getFileByMD5(fileMd5);
        if(dbFile != null){
            return dbFile.getUrl();
        }
        String url = fastDFSUtil.uploadFileSlices(slice, fileMd5, sliceNo, totalSliceNo);
        if(!StringUtil.isNullOrEmpty(url)){
            dbFile = new File();
            dbFile.setCreateTime(new Date());
            dbFile.setMd5(fileMd5);
            dbFile.setUrl(url);
            dbFile.setType(fastDFSUtil.getFileType(slice));
            fileDao.addFile(dbFile);
        }
        return url;
    }
}
