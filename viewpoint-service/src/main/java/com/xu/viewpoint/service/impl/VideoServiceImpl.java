package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.VideoDao;
import com.xu.viewpoint.dao.domain.*;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.service.UserCoinService;
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

    @Autowired
    private UserCoinService userCoinService;


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

    /**
     * 更新收藏的视频，主要是更新收藏视频的分组
     *
     * @param videoCollection
     * @param userId
     */
    @Override
    public void updateVideoCollection(VideoCollection videoCollection, Long userId) {
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if(videoId == null || groupId == null){
            throw new ConditionException("参数错误！");
        }

        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("视频已删除！");
        }
        // TODO 是否检查分组id是否合法
        videoCollection.setUserId(userId);
        videoDao.updateVideoCollection(videoCollection);
    }


    /**
     * 视频投币
     *
     * @param videoCoin
     * @param userId
     */
    @Transactional
    @Override
    public void addVideoCoins(VideoCoin videoCoin, Long userId) {
        Long videoId = videoCoin.getVideoId();
        Integer amount = videoCoin.getAmount();
        if(videoId == null || amount<=0){
            throw new ConditionException("参数错误！");
        }

        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("视频已删除！");
        }

        // 查询当前用户硬币总数，从而判断是否有足够硬币
        Integer userCoinsAmount = userCoinService.getUserCoinsAmount(userId);
        userCoinsAmount = userCoinsAmount == null ? 0 : userCoinsAmount;
        if(amount > userCoinsAmount){
            throw new ConditionException("硬币数量不够！");
        }

        Date date = new Date();
        videoCoin.setUserId(userId);
        // 查询是否之前投币过，若是，则变成修改
        VideoCoin dbVideoCoin = videoDao.getVideoCoinsByUserIdAndVideoId(userId, videoId);

        if(dbVideoCoin!=null){
            // 之前投币过，更新
            videoCoin.setUpdateTime(date);
            this.updateVideoCoins(videoCoin);
        }else {
            // 之前未投币，新增
            videoCoin.setCreateTime(date);
            videoDao.addVideoCoins(videoCoin);
        }
        // 修改用户硬币总数
        userCoinService.updateUserCoinsAmount(userId, amount);
    }

    /**
     * 根据视频id查看投币总数
     *
     * @param videoId
     * @param userId
     */
    @Override
    public Map<String, Object> getVideoCoins(Long videoId, Long userId) {

        // 查看此视频投币总数
        Long count = videoDao.getVideoCoinsAmounts(videoId);

        // 如果当前登录，则查看自己是否有投币过
        VideoCoin videoCoin = videoDao.getVideoCoinsByUserIdAndVideoId(userId, videoId);

        boolean coin = videoCoin != null;

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("coin", coin);

        return result;
    }


    //--------------------------------private--------------

    /**
     * 修改视频投币数, 仅支持用户再投币
     * @param videoCoin
     */
    private void updateVideoCoins(VideoCoin videoCoin){
        videoDao.updateVideoCoins(videoCoin);
    }
}
