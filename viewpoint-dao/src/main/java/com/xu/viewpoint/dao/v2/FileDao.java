package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: xuJing
 * @date: 2025/1/4 16:04
 */
@Mapper
public interface FileDao {

    /**
     * 根据fileMd5查询文件
     * @param fileMd5
     */
    File getFileByMD5(@Param("fileMd5") String fileMd5);

    /**
     * 插入数据
     * @param dbFile
     */
    Integer addFile(@Param("dbFile") File dbFile);


}
