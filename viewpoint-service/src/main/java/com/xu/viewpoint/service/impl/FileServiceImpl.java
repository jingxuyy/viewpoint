package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.service.FileService;
import com.xu.viewpoint.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: xuJing
 * @date: 2024/12/4 13:37
 */

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FastDFSUtil fastDFSUtil;
    /**
     * 文件分片上传
     *
     * @param file
     * @param fileMd5
     * @param sliceNo
     * @param totalSliceNo
     */
    @Override
    public String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws IOException {
        String filepath = fastDFSUtil.uploadFileSlices(file, fileMd5, sliceNo, totalSliceNo);
        return filepath;
    }
}
