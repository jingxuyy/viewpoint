package com.xu.viewpoint.api;

import com.xu.viewpoint.dao.domain.JsonResponse;
import com.xu.viewpoint.service.UserService;
import com.xu.viewpoint.service.util.RSAUtil;
import com.xu.viewpoint.dao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xuJing
 * @date: 2024/11/21 10:04
 */
@RestController
public class UserApi {

    @Autowired
    private UserService userService;

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
    public JsonResponse<String> login(@RequestBody User user){
        String token = userService.login(user);
        return JsonResponse.success(token);
    }
}
