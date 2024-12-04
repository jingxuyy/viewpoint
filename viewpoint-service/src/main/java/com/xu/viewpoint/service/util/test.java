package com.xu.viewpoint.service.util;

/**
 * @author: xuJing
 * @date: 2024/12/4 14:41
 */
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
public class test {
    public static MultipartFile getMultipartFile(File file) {
        FileItem fileItem = new DiskFileItemFactory().createItem("file", "multipart/form-data", true, file.getName());
        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }
        return new CommonsMultipartFile(fileItem);
    }
}


