package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.Video;

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
}
