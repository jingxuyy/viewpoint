package com.xu.viewpoint.api;

import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: xuJing
 * @date: 2024/12/4 13:34
 */

@RestController
public class FileApi {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传，分片上传
     * @param file
     * @param fileMd5
     * @param sliceNo
     * @param totalSliceNo
     */
    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile file,
                                                   String fileMd5,
                                                   Integer sliceNo,
                                                   Integer totalSliceNo) throws IOException {
        String filePath = fileService.uploadFileBySlices(file, fileMd5, sliceNo, totalSliceNo);
        return JsonResponse.success(filePath);
    }
}
