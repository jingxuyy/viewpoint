package com.xu.viewpoint.api.v2;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.UserFollowing;
import com.xu.viewpoint.service.v2.impl.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/12/28 17:04
 */
@RestController
public class UserFollowingApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;


    /**
     * 添加用户关注
     * @param userFollowing
     */
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }


    /**
     * 取消关注（删除关注）
     * @param followingId
     */
    @DeleteMapping("/user-followings")
    public JsonResponse<String> deleteUserFollowing(@RequestParam Long followingId){
        Long userId = userSupport.getCurrentUserId();
        userFollowingService.deleteUserFollowing(userId, followingId);
        return JsonResponse.success();
    }


    /**
     * 获取当前用户关注列表，以不同分组展示
     */
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> result = userFollowingService.getUserFollowingGroups(userId);
        return JsonResponse.success(result);
    }
}
