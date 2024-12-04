package com.xu.viewpoint.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * 文件分片上传
     * @param file
     * @param fileMd5
     * @param sliceNo
     * @param totalSliceNo
     */
    String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws IOException;
}
