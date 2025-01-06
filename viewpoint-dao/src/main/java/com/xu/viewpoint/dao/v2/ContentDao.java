package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentDao {

    /**
     * 向数据库插入数据
     * @param content
     */
    Integer addContent(@Param("content") Content content);
}
