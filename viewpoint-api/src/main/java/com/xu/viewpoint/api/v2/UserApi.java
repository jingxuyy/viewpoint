package com.xu.viewpoint.api.v2;

import com.xu.viewpoint.api.support.UserSupport;
import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserInfo;
import com.xu.viewpoint.service.util.RSAUtil;
import com.xu.viewpoint.service.util.TokenUtil;
import com.xu.viewpoint.service.v2.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class UserApi {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserSupport userSupport;



    /**
     * 获取公钥 2.0
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String publicKeyStr = RSAUtil.getPublicKeyStr();
        return JsonResponse.success(publicKeyStr);
    }


    @PostMapping("/users")
    public JsonResponse<String> register(@RequestBody User user){
        userService.register(user);
        return JsonResponse.success();
    }


    /** 2.0
     * 获取当前登录用户  包含用户所有详细信息
     * @return user
     */
    @GetMapping("/users")
    public JsonResponse<User> getCurrentUser(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getCurrentUser(userId);
        return JsonResponse.success(user);
    }

    /**
     * 更新用户基本信息 2.0
     * @param user
     */
    @PutMapping("/users")
    public JsonResponse<String> updateUserBasicInfo(@RequestBody User user) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUserBasicInfo(user);
        return JsonResponse.success();
    }


    /**
     * 更新用户详细信息 2.0
     */
    @PutMapping("/user-infos")
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
//    @GetMapping("/user-infos")
//    public JsonResponse<PageResult<UserInfo>> pageListUserInfo(@RequestParam Integer no, @RequestParam Integer size, String nick ){
//        Long userId = userSupport.getCurrentUserId();
//        JSONObject param = new JSONObject();
//        param.put("no", no);
//        param.put("size", size);
//        param.put("nick", nick);
//        param.put("userId", userId);
//        PageResult<UserInfo> result = userService.pageListUserInfo(param);
//        // 需要判断查询的用户是否被当前用户关注过
//        if(result.getTotal() > 0){
//            List<UserInfo> checkUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
//            result.setList(checkUserInfoList);
//        }
//        return JsonResponse.success(result);
//    }

    /**
     * 双令牌登录
     * @param user
     */
    @PostMapping("/user-dts")
    public JsonResponse<Map<String, Object>> loginForDts(@RequestBody User user) throws Exception {
        Map<String, Object> result = userService.loginForDts(user);
        return JsonResponse.success(result);
    }

    /**
     * 退出登录
     * @param request
     */
    @DeleteMapping("/refresh-tokens")
    public JsonResponse<String> logout(HttpServletRequest request){
        String refreshToken = request.getHeader("refreshToken");
        Long userId = userSupport.getCurrentUserId();
        userService.logout(refreshToken, userId);
        return JsonResponse.success();
    }

    /**
     * 从请求头refreshToken获取refreshToken
     * 1. 解析refreshToken
     * 1.1 成功：生成新的token返回给前端
     * 1.2 失败：
     * 1.2.1 解析异常，refreshToken本身错误，直接返回系统错误
     * 1.2.2 refreshToken解析发现过期，返回状态码40412 需要重新登录
     * @param request
     */
    @PostMapping("/access-tokens")
    public JsonResponse<String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("refreshToken");
        Long userId = TokenUtil.verifyRefreshToken(refreshToken);
        String token = TokenUtil.generateToken(userId);
        return JsonResponse.success(token);
    }
}
