package com.xu.viewpoint.api.v2;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.service.v2.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xuJing
 * @date: 2025/1/6 15:01
 */
@RestController
public class VideoApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private IVideoService videoService;


    /**
     * 视频投稿
     * @param video
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return JsonResponse.success();
    }
}
