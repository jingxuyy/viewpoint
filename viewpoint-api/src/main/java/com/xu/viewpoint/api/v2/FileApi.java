package com.xu.viewpoint.api.v2;

import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.service.v2.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: xuJing
 * @date: 2025/1/4 15:57
 */
@RestController
public class FileApi {

    @Autowired
    private IFileService fileService;


    /**
     * 根据文件内容获取文件的 MD5
     * @param file
     * @throws Exception
     */
    @PostMapping("/md5files")
    public JsonResponse<String> getFileMD5(MultipartFile file) throws Exception {
        String fileMD5 = fileService.getFileMD5(file);
        return new JsonResponse<>(fileMD5);
    }

    /**
     * 文件分片上传
     * @param slice 分片文件
     * @param fileMd5 整体文件MD5
     * @param sliceNo 当前分片文件的序号
     * @param totalSliceNo 总的分片数量
     * @throws Exception
     */
    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile slice,
                                                   String fileMd5,
                                                   Integer sliceNo,
                                                   Integer totalSliceNo) throws Exception {
        String filePath = fileService.uploadFileBySlices(slice, fileMd5, sliceNo, totalSliceNo);
        return new JsonResponse<>(filePath);
    }
}
