package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.VideoDao;
import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.dao.domain.VideoTag;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: xuJing
 * @date: 2024/12/5 11:33
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;


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

        List<Video> list = new ArrayList<>();
        Integer total = videoDao.pageCountVideos(params);

        if(total > 0){
            list = videoDao.pageListVideos(params);
        }

        return new PageResult<>(total, list);
    }
}
