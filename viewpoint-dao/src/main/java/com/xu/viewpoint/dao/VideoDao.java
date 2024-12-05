package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.dao.domain.VideoCollection;
import com.xu.viewpoint.dao.domain.VideoLike;
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

    /**
     * 根据id查询数据
     * @param videoId
     */
    Video getVideoById(@Param("videoId") Long videoId);

    /**
     * 根据用户id和视频id查看数据
     * @param videoId
     * @param userId
     */
    VideoLike getVideoLikeByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    /**
     * 向videoLike插入数据
     * @param videoLike
     */
    Integer addVideoLike(@Param("videoLike") VideoLike videoLike);

    /**
     * 从videoLike中删除数据
     * @param videoId
     * @param userId
     */
    Integer deleteVideoLike(@Param("videoId") Long videoId, @Param("userId") Long userId);

    /**
     * 获取指定视频的点赞总数
     * @param videoId
     * @return
     */
    Long getVideoLikes(@Param("videoId") Long videoId);

    /**
     * 根据videoId和userId在videoCollection删除数据
     * @param videoId
     * @param userId
     */
    Integer deleteVideoCollection(@Param("videoId") Long videoId, @Param("userId") Long userId);

    /**
     * 向videoCollection添加数据
     * @param videoCollection
     */
    Integer addVideoCollection(@Param("videoCollection") VideoCollection videoCollection);

    /**
     * 查看当前视频收藏总次数
     * @param videoId
     */
    Long getVideoCollections(@Param("videoId") Long videoId);

    /**
     * 根据videId和userId获取VideoCollection
     * @param videoId
     * @param userId
     */
    VideoCollection getVideoCollectionByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);
}
