package com.xu.viewpoint.api;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.Danmu;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/12/6 19:45
 */
@RestController
public class DanmuApi {

    @Autowired
    private DanmuService danmuService;

    @Autowired
    private UserSupport userSupport;


    @GetMapping("/danmus")
    public JsonResponse<List<Danmu>> getDanmus(@RequestParam Long videoId,
                                               String startTime,
                                               String endTime) throws ParseException {
        List<Danmu> list;
        try {
            // 判断当前身份有没有登录
            userSupport.getCurrentUserId();
            // 用户登录可以允许用户通过时间筛选弹幕
            list = danmuService.getDanmus(videoId, startTime, endTime);
        }catch (Exception ignored){

            // 未登录，不能通过时间筛选弹幕
            list = danmuService.getDanmus(videoId, null, null);
        }
        return JsonResponse.success(list);
    }
}
