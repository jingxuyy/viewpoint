package com.xu.viewpoint.api.v2;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.UserFollowing;
import com.xu.viewpoint.service.v2.IUserFollowingService;
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
    private IUserFollowingService userFollowingService;


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


    /**
     * 获取用户所有粉丝，并标明互粉情况
     */
    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans(){
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> fanList = userFollowingService.getUserFans(userId);
        return JsonResponse.success(fanList);
    }


    /**
     * 将关注的用户移动到其它分组
     * @param userFollowing
     */
    @PutMapping("/user-followings")
    public JsonResponse<String> updateUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.updateUserFollowing(userFollowing);
        return JsonResponse.success();
    }


    /**
     * 用户添加自定义分组
     * @param followingGroup
     */
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroup(@RequestBody FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroup(followingGroup);
        return JsonResponse.success(groupId);
    }

    /**
     * 获取用户自己创建的分组
     */
    @GetMapping("/user-following-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroup(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> result = userFollowingService.getUserFollowingGroup(userId);
        return JsonResponse.success(result);
    }

}
