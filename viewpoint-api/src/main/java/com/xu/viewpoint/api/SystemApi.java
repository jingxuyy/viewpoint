package com.xu.viewpoint.api;

import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: xuJing
 * @date: 2024/12/11 18:30
 */
@RestController
public class SystemApi {

    @Autowired
    private ElasticSearchService elasticSearchService;


    @GetMapping("/contents")
    public JsonResponse<List<Map<String, Object>>> getContents(@RequestParam String keyword,
                                                         @RequestParam Integer pageNo,
                                                         @RequestParam Integer pageSize) throws IOException {
        List<Map<String, Object>> contents = elasticSearchService.getContents(keyword, pageNo, pageSize);
        return JsonResponse.success(contents);
    }
}
