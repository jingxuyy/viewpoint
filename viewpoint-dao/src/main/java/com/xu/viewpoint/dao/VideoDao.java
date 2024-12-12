package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.*;
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

    /**
     * 更新 VideoCollection
     * @param videoCollection
     */
    Integer updateVideoCollection(@Param("videoCollection") VideoCollection videoCollection);

    /**
     * 视频投币，向VideoCoin添加数据
     * @param videoCoin
     */
    Integer addVideoCoins(@Param("videoCoin") VideoCoin videoCoin);

    /**
     * 视频投币修改，仅支持再次投币
     * @param videoCoin
     */
    Integer updateVideoCoins(@Param("videoCoin") VideoCoin videoCoin);

    /**
     * 根据userId和videoId查询记录
     * @param userId
     * @param videoId
     */
    VideoCoin getVideoCoinsByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") Long videoId);

    /**
     * 根据videoId查询当前视频投币总数
     * @param videoId
     */
    Long getVideoCoinsAmounts(@Param("videoId") Long videoId);


    /**
     * 向VideoComment表中添加数据
     * @param videoComment
     */
    Integer addVideoComment(@Param("videoComment") VideoComment videoComment);

    /**
     * 根据videoId查询一级的评论数
     * @param params
     */
    Integer pageCountVideoComments(@Param("params") Map<String, Object> params);

    /**
     * 根据videoId分页查询所有的一级评论
     * @param params
     */
    List<VideoComment> pageListVideoComments(@Param("params") Map<String, Object> params);

    /**
     * 根据一级评论id批量查询二级评论数据
     * @param parentIdList
     */
    List<VideoComment> batchGetVideoCommentsByRootIds(@Param("rootIds") List<Long> rootIds);

    /**
     * 根据id查询信息
     * @param videoId
     */
    Video getVideoDetails(@Param("videoId") Long videoId);

    /**
     * 添加观看记录
     * @param videoView
     */
    Integer addVideoView(@Param("videoView") VideoView videoView);

    /**
     * 查询当天观看记录
     * @param params
     */
    VideoView getVideoView(@Param("params") Map<String, Object> params);

    /**
     * 查询视频播放量
     * @param videoId
     */
    Integer getVideoViewCounts(@Param("videoId") Long videoId);

    /**
     * 获取所有用户偏好视频和得分
     */
    List<UserPreference> getAllUserPreference();

    /**
     * 根据视频id集合批量查询视频
     * @param itemIds
     */
    List<Video> batchGetVideosByIds(@Param("itemIds") List<Long> itemIds);
}
