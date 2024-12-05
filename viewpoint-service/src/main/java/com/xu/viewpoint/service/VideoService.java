package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.dao.domain.VideoCoin;
import com.xu.viewpoint.dao.domain.VideoCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
