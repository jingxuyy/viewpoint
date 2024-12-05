package com.xu.viewpoint.api;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xuJing
 * @date: 2024/12/5 11:34
 */
@RestController
public class VideoApi {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserSupport userSupport;

    /**
     * 视频投稿，添加视频
     * @param video
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return JsonResponse.success();
    }

    /**
     * 首页按照分区分页查询
     * @param size
     * @param no
     * @param area
     */
    @GetMapping("/videos")
    public JsonResponse<PageResult<Video>> pageListVideo(Integer size, Integer no, String area){
        PageResult<Video> result = videoService.pageListVideo(size, no, area);
        return JsonResponse.success(result);
    }

}
