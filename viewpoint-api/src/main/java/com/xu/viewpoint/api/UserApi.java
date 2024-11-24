package com.xu.viewpoint.api;

import com.alibaba.fastjson.JSONObject;
import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.UserInfo;
import com.xu.viewpoint.service.UserFollowingService;
import com.xu.viewpoint.service.UserService;
import com.xu.viewpoint.service.util.RSAUtil;
import com.xu.viewpoint.dao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/21 10:04
 */
@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;

    /**
     * 获取公钥
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String publicKeyStr = RSAUtil.getPublicKeyStr();
        return JsonResponse.success(publicKeyStr);
    }

    /**
     * 注册用户
     * @param user
     */
    @PostMapping("/users")
    public JsonResponse<String> register(@RequestBody User user){
        userService.register(user);
        return JsonResponse.success();
    }

    /**
     * 用户登录
     * @param user
     * @return 返回登录token
     */
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        return JsonResponse.success(token);
    }

    /**
     * 获取当前登录用户
     * @return user
     */
    @GetMapping("/users")
    public JsonResponse<User> getCurrentUser(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getCurrentUser(userId);
        return JsonResponse.success(user);
    }

    /**
     * 更新用户信息
     * @param user
     */
    @PutMapping("/users")
    public JsonResponse<String> updateUser(@RequestBody User user) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUser(user);
        return JsonResponse.success();
    }


    /**
     * 更新用户详细信息
     */
    @PutMapping("/user-info")
    public JsonResponse<String> updateUserInfo(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfo(userInfo);
        return JsonResponse.success();
    }


    /**
     * 分页查询，有条件：根据nick查询，或者全部查询
     * @param no
     * @param size
     * @param nick
     */
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfo(@RequestParam Integer no, @RequestParam Integer size, String nick ){
        Long userId = userSupport.getCurrentUserId();
        JSONObject param = new JSONObject();
        param.put("no", no);
        param.put("size", size);
        param.put("nick", nick);
        param.put("userId", userId);
        PageResult<UserInfo> result = userService.pageListUserInfo(param);
        // 需要判断查询的用户是否被当前用户关注过
        if(result.getTotal() > 0){
            List<UserInfo> checkUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkUserInfoList);
        }
        return JsonResponse.success(result);
    }
}
