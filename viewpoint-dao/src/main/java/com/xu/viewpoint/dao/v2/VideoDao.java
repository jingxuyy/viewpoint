package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoDao {

    /**
     * 添加视频数据
     * @param video
     */
    Integer addVideos(@Param("video") Video video);
}
