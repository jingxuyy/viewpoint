package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.dao.domain.VideoTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoDao {

    /**
     * 数据库插入视频信息
     * @param video
     */
    Integer addVideo(@Param("video") Video video);


    /**
     * 添加视频标签
     * @param videoTagList
     */
    Integer batchAddVideoTags(@Param("videoTagList") List<VideoTag> videoTagList);

    /**
     * 查询满足条件分页查询的总条目数
     * @param params
     */
    Integer pageCountVideos(@Param("params") Map<String, Object> params);

    /**
     * 根据条件分页查询
     * @param params
     */
    List<Video> pageListVideos(@Param("params") Map<String, Object> params);
}
