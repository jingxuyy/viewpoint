package com.xu.viewpoint.service.v2;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    /**
     * 获取文件的MD5字符串
     * @param file
     */
    String getFileMD5(MultipartFile file) throws Exception;

    /**
     * 文件分片上传
     * @param slice 已经分片的文件片段
     * @param fileMd5 总体文件的MD5码
     * @param sliceNo 当前文件分片的分片号
     * @param totalSliceNo 总的分片数量
     */
    String uploadFileBySlices(MultipartFile slice, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws IOException;
}
