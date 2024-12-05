package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.VideoDao;
import com.xu.viewpoint.dao.domain.*;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.service.VideoService;
import com.xu.viewpoint.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author: xuJing
 * @date: 2024/12/5 11:33
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;


    @Autowired
    private FastDFSUtil fastDFSUtil;


    /**
     * 添加视频，视频投稿
     *
     * @param video
     */
    @Transactional
    @Override
    public void addVideos(Video video) {

        Date date = new Date();
        // 1. 向数据库添加video
        video.setCreateTime(date);
        videoDao.addVideo(video);

        // 2. 向数据库添加video的标签
        Long videoId = video.getId();
        List<VideoTag> videoTagList = video.getVideoTagList();
        videoTagList.forEach(item -> {
            item.setCreateTime(date);
            item.setVideoId(videoId);
        });
        videoDao.batchAddVideoTags(videoTagList);
    }


    /**
     * 按照分区分页查询
     *
     * @param size
     * @param no
     * @param area
     */
    @Override
    public PageResult<Video> pageListVideo(Integer size, Integer no, String area) {

        if(size == null || no ==null){
            throw new ConditionException("参数异常！");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("start", (no-1)*size);
        params.put("limit", size);
        params.put("area", area);

        // 1. 查询总条数
        List<Video> list = new ArrayList<>();
        Integer total = videoDao.pageCountVideos(params);

        // 2. 查询结果
        if(total > 0){
            list = videoDao.pageListVideos(params);
        }

        return new PageResult<>(total, list);
    }


    /**
     * 在线播放视频
     *
     * @param request
     * @param response
     * @param url
     */
    @Override
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        fastDFSUtil.viewVideoOnlineBySlices(request, response, url);
    }


    /**
     * 视频点赞
     *
     * @param videoId
     * @param userId
     */
    @Override
    public void addVideoLike(Long videoId, Long userId) {

        // 1. 查询数据库是否存在当前视频
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("视频已删除！");
        }

        // 2. 查看当前视频是否已经被当前用户点赞过
        VideoLike videoLike = videoDao.getVideoLikeByVideoIdAndUserId(videoId, userId);
        if(videoLike != null){
            throw new ConditionException("已点赞过！");
        }

        // 3. 至此，需要完成点赞，即插入数据
        videoLike = new VideoLike();
        videoLike.setCreateTime(new Date());
        videoLike.setUserId(userId);
        videoLike.setVideoId(videoId);

        videoDao.addVideoLike(videoLike);
    }

    /**
     * 视频取消点赞
     *
     * @param videoId
     * @param userId
     */
    @Override
    public void deleteVideoLike(Long videoId, Long userId) {
        // 1. 查询数据库是否存在当前视频
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("视频已删除！");
        }
        // 2. 至此，删除记录
        videoDao.deleteVideoLike(videoId, userId);
    }

    /**
     * 查看视频点赞数
     *
     * @param videoId
     * @param userId
     */
    @Override
    public Map<String, Object> getVideoLikes(Long videoId, Long userId) {

        // 1. 查询当前视频点赞总数
        Long count = videoDao.getVideoLikes(videoId);

        // 2. 如果当前用户登录，需要查询当前用户是否给当前视频点赞过
        VideoLike videoLike = videoDao.getVideoLikeByVideoIdAndUserId(videoId, userId);

        boolean like = videoLike != null;
        Map<String, Object> result = new HashMap<>();

        // count 是当前视频点赞总数， like是布尔值，当为true说明当前视频被当前用户点赞过
        result.put("count", count);
        result.put("like", like);
        return result;
    }


    /**
     * 添加收藏视频
     *
     * @param userId
     * @param videoCollection
     */
    @Transactional
    @Override
    public void addVideoCollection(Long userId, VideoCollection videoCollection) {
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if(videoId == null || groupId == null){
            throw new ConditionException("参数错误！");
        }

        // 1. 查询视频，是否存在
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("视频已删除");
        }

        // 2. 采用先删数据再添加数据策略
        videoDao.deleteVideoCollection(videoId, userId);
        videoCollection.setUserId(userId);
        videoCollection.setCreateTime(new Date());
        videoDao.addVideoCollection(videoCollection);
    }

    /**
     * 取消收藏视频
     *
     * @param userId
     * @param videoId
     */
    @Override
    public void deleteVideoCollection(Long userId, Long videoId) {
        videoDao.deleteVideoCollection(userId, videoId);
    }

    /**
     * 查看当前视频收藏总数
     *
     * @param userId
     * @param videoId
     * @return
     */
    @Override
    public Map<String, Object> getVideoCollections(Long userId, Long videoId) {

        // 1. 查询当前视频收藏总数
        Long count = videoDao.getVideoCollections(videoId);

        // 2. 如果当前用户登录，需要查询当前用户是否给当前视频收藏过
        VideoCollection videoCollection = videoDao.getVideoCollectionByVideoIdAndUserId(videoId, userId);

        boolean collect = videoCollection != null;
        Map<String, Object> result = new HashMap<>();

        // count 是当前视频点赞总数， like是布尔值，当为true说明当前视频被当前用户点赞过
        result.put("count", count);
        result.put("collect", collect);
        return result;
    }
}
