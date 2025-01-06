package com.xu.viewpoint.service.v2;

import com.xu.viewpoint.dao.domain.Video;

public interface IVideoService {

    /**
     * 视频投稿
     * @param video
     */
    void addVideos(Video video);
}
