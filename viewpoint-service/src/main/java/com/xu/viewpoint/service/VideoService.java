package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.*;
//import org.apache.mahout.cf.taste.common.TasteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface VideoService {

    /**
     * 添加视频，视频投稿
     * @param video
     */
    void addVideos(Video video);

    /**
     * 按照分区分页查询
     * @param size
     * @param no
     * @param area
     */
    PageResult<Video> pageListVideo(Integer size, Integer no, String area);

    /**
     * 在线播放视频
     * @param request
     * @param response
     * @param url
     */
    void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception;

    /**
     * 视频点赞
     * @param videoId
     * @param userId
     */
    void addVideoLike(Long videoId, Long userId);

    /**
     * 视频取消点赞
     * @param videoId
     * @param userId
     */
    void deleteVideoLike(Long videoId, Long userId);

    /**
     * 查看视频点赞数
     * @param videoId
     * @param userId
     */
    Map<String, Object> getVideoLikes(Long videoId, Long userId);


    /**
     * 添加收藏视频
     * @param userId
     * @param videoCollection
     */
    void addVideoCollection(Long userId, VideoCollection videoCollection);

    /**
     * 取消收藏视频
     * @param userId
     * @param videoId
     */
    void deleteVideoCollection(Long userId, Long videoId);

    /**
     * 查看当前视频收藏总数
     * @param userId
     * @param videoId
     * @return
     */
    Map<String, Object> getVideoCollections(Long userId, Long videoId);

    /**
     * 更新收藏的视频，主要是更新收藏视频的分组
     * @param videoCollection
     * @param userId
     */
    void updateVideoCollection(VideoCollection videoCollection, Long userId);

    /**
     * 视频投币
     * @param videoCoin
     * @param userId
     */
    void addVideoCoins(VideoCoin videoCoin, Long userId);

    /**
     * 根据视频id查看投币总数
     * @param videoId
     * @param userId
     */
    Map<String, Object> getVideoCoins(Long videoId, Long userId);


    /**
     * 添加视频评论
     * @param videoComment
     * @param userId
     */
    void addVideoComment(VideoComment videoComment, Long userId);


    /**
     * 分页查询视频评论
     * @param size
     * @param no
     * @param videoId
     */
    PageResult<VideoComment> pageListVideoComments(Integer size, Integer no, Long videoId);

    /**
     * 获取视频详情
     * @param videoId
     */
    Map<String, Object> getVideoDetails(Long videoId);

    /**
     * 添加视频观看记录
     * @param videoView
     * @param request
     */
    void addVideoView(VideoView videoView, HttpServletRequest request);

    /**
     * 查询视频播放量
     * @param videoId
     */
    Integer getVideoViewCounts(Long videoId);

    /**
     * 视频推荐
     * @param userId
     */
//    List<Video> recommend(Long userId) throws TasteException;
}
