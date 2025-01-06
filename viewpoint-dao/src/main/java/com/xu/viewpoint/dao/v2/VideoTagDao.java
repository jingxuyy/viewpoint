package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.VideoTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoTagDao {

    /**
     * 批量增加数据
     * @param tagList
     */
    Integer batchAddVideoTags(@Param("tagList") List<VideoTag> tagList);
}
