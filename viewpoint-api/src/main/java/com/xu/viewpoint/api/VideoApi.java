package com.xu.viewpoint.api;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.dao.domain.VideoCollection;
import com.xu.viewpoint.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    /**
     * 在线播放视频
     * @param request
     * @param response
     * @param url
     */
    @GetMapping("/video-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request, response, url);
    }

    /**
     * 视频点赞
     * @param videoId
     */
    @PostMapping("/video-likes")
    public JsonResponse<String> addVideoLike(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoLike(videoId, userId);
        return JsonResponse.success();
    }


    /**
     * 视频取消点赞
     * @param videoId
     */
    @DeleteMapping("/video-likes")
    public JsonResponse<String> deleteVideoLike(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoLike(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 查询视频点赞数
     * @param videoId
     */
    @GetMapping("/video-likes")
    public JsonResponse<Map<String, Object>> getVideoLikes(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){

        }
        // 无论用户是否登录，都可以查看视频点赞总数
        Map<String, Object> result = videoService.getVideoLikes(videoId, userId);
        return JsonResponse.success(result);
    }





    /**
     * 收藏视频
     * @param videoCollection
     */
    @PostMapping("/video-collections")
    public JsonResponse<String> addVideoCollection(@RequestBody VideoCollection videoCollection){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCollection(userId, videoCollection);
        return JsonResponse.success();
    }


    /**
     * 取消收藏
     * @param videoId
     */
    @DeleteMapping("/video-collections")
    public JsonResponse<String> deleteVideoCollection(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoCollection(userId, videoId);
        return JsonResponse.success();
    }

    /**
     * 查询当前视频收藏数量
     * @param videoId
     */
    @GetMapping("/video-collections")
    public JsonResponse<Map<String, Object>> getVideoCollections(@RequestParam Long videoId){
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){

        }
        // 无论用户是否登录，都可以查看视频收藏总数
        Map<String, Object> result = videoService.getVideoCollections(userId, videoId);
        return JsonResponse.success(result);
    }

}
