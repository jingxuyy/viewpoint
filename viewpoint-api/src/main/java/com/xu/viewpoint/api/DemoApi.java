package com.xu.viewpoint.api;

import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.UserInfo;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.service.DemoService;
import com.xu.viewpoint.service.ElasticSearchService;
import com.xu.viewpoint.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/20 13:12
 */
@RestController
public class DemoApi {
    @Autowired
    private DemoService demoService;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping("/query")
    public Long query(Long id){
        return demoService.query(id);
    }

    @GetMapping("/slices")
    public void slices(MultipartFile file) throws IOException {
        fastDFSUtil.convertFileToSlices(file);
    }

    @GetMapping("/es-videos")
    public JsonResponse<Video> getEsVideos(@RequestParam String keyword){
        Video video =elasticSearchService.getVideoByTitle(keyword);
        return JsonResponse.success(video);
    }

    @PostMapping("/es-userInfos")
    public JsonResponse<String> saveEsUserInfo(UserInfo userInfo){
        elasticSearchService.addUserInfo(userInfo);
        return JsonResponse.success();
    }
}
