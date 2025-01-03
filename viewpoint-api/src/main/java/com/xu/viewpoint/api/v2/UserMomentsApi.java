package com.xu.viewpoint.api.v2;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.UserMoment;
import com.xu.viewpoint.service.v2.IUserMomentsService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2025/1/2 15:35
 */

@RestController
public class UserMomentsApi {

    @Autowired
    private UserSupport userSupport;


    @Autowired
    private IUserMomentsService userMomentsService;


    /**
     * 用户发布动态
     * <br/>
     * @param userMoment
     */


    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    /**
     * 查询当前关注的用户的动态
     */
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments(){
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> list = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(list);
    }

    /**
     * 分页查询用户的动态，可以根据动态类型type进行查询，若type为空，则默认查询所有类型动态
     * @param size
     * @param no
     * @param type
     */
    @GetMapping("/moments")
    public JsonResponse<PageResult<UserMoment>> pageListMoments(@RequestParam("size") Integer size,
                                                                @RequestParam("no") Integer no,
                                                                String type){
        Long userId = userSupport.getCurrentUserId();
        PageResult<UserMoment> list = userMomentsService.pageListMoments(size, no, userId, type);
        return new JsonResponse<>(list);
    }
}
