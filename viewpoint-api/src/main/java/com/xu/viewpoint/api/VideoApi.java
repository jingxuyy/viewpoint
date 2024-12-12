package com.xu.viewpoint.api;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.*;
import com.xu.viewpoint.service.ElasticSearchService;
import com.xu.viewpoint.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    @Autowired
    private ElasticSearchService elasticSearchService;

    /**
     * 视频投稿，添加视频
     * @param video
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        // 添加到es中
        elasticSearchService.addVideo(video);
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

    /**
     * 更新收藏的视频
     * @param videoCollection
     */
    @PutMapping("/video-collections")
    public JsonResponse<String> updateVideoCollection(@RequestBody VideoCollection videoCollection){
        Long userId = userSupport.getCurrentUserId();
        videoService.updateVideoCollection(videoCollection, userId);
        return JsonResponse.success();
    }




    /**
     * 视频投币
     * @param videoCoin
     */
    @PostMapping("/video-coins")
    public JsonResponse<String> addVideoCoins(@RequestBody VideoCoin videoCoin){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCoins(videoCoin, userId);
        return JsonResponse.success();
    }

    /**
     * 查看视频投币总数
     * @param videoId
     */
    @GetMapping("/video-coins")
    public JsonResponse<Map<String, Object>> getVideoCoins(@RequestParam Long videoId){
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){

        }
        // 无论用户是否登录，都可以查看视频投币总数
        Map<String, Object> result = videoService.getVideoCoins(videoId, userId);
        return JsonResponse.success(result);
    }


    /**
     * 视频添加评论
     * @param videoComment
     */
    @PostMapping("/video-comments")
    public JsonResponse<String> addVideoComment(@RequestBody VideoComment videoComment){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoComment(videoComment, userId);
        return JsonResponse.success();
    }


    /**
     * 分页查询视频评论
     * @param size
     * @param no
     * @param videoId
     */
    @GetMapping("/video-comments")
    public JsonResponse<PageResult<VideoComment>> pageListVideoComments(@RequestParam Integer size,
                                                                        @RequestParam Integer no,
                                                                        @RequestParam Long videoId){
        PageResult<VideoComment> result = videoService.pageListVideoComments(size, no, videoId);
        return JsonResponse.success(result);
    }


    /**
     * 获取视频详情
     * @param videoId
     */
    @GetMapping("/video-details")
    public JsonResponse<Map<String, Object>> getVideoDetails(@RequestParam Long videoId){
        Map<String, Object> result = videoService.getVideoDetails(videoId);
        return JsonResponse.success(result);
    }

    /**
     * 添加视频观看记录
     * @param videoView
     * @param request
     */
    @PostMapping("/video-views")
    public JsonResponse<String> addVideoView(@RequestBody VideoView videoView, HttpServletRequest request){
        Long userId;
        try {
            userId = userSupport.getCurrentUserId();
            videoView.setUserId(userId);
            videoService.addVideoView(videoView, request);
        }catch (Exception e){
            videoService.addVideoView(videoView, request);
        }
        return JsonResponse.success();
    }

    /**
     * 查询视频播放量
     * @param videoId
     */
    @GetMapping("/video-view-counts")
    public JsonResponse<Integer> getVideoViewCounts(@RequestParam Long videoId){
        Integer count = videoService.getVideoViewCounts(videoId);
        return JsonResponse.success(count);
    }

    /**
     * 视频内容推荐
     */
    @GetMapping("/recommendations")
    public JsonResponse<List<Video>> recommend(){
        Long userId = userSupport.getCurrentUserId();
        List<Video> list = videoService.recommend(userId);
        return JsonResponse.success(list);
    }

}
