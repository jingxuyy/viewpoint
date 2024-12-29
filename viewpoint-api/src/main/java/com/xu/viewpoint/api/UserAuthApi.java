//package com.xu.viewpoint.api;
//
//import com.xu.viewpoint.api.support.UserSupport;
//import com.xu.viewpoint.dao.domain.JsonResponse;
//import com.xu.viewpoint.dao.domain.auth.UserAuthorities;
//import com.xu.viewpoint.service.UserAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author: xuJing
// * @date: 2024/11/25 15:55
// */
//@RestController
//public class UserAuthApi {
//
//    @Autowired
//    private UserSupport userSupport;
//
//    @Autowired
//    private UserAuthService userAuthService;
//
//    /**
//     * 获取当前用户的所有权限
//     */
//    @GetMapping("/user-authorities")
//    public JsonResponse<UserAuthorities> getUserAuthorities(){
//        Long userId = userSupport.getCurrentUserId();
//        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
//        return JsonResponse.success(userAuthorities);
//    }
//}
