package com.xu.viewpoint.api.support;

import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
//import com.xu.viewpoint.service.UserService;
import com.xu.viewpoint.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: xuJing
 * @date: 2024/11/21 13:39
 */
@Component
public class UserSupport {


    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        String token = requestAttributes.getRequest().getHeader("token");
        if(StringUtils.isNullOrEmpty(token)){
            throw new ConditionException("未登录！");
        }
        Long userId = TokenUtil.verifyToken(token, null);
        if(userId<0){
            throw new ConditionException("非法用户！");
        }
        return userId;
    }


//    //验证刷新令牌 弃用
//    private void verifyRefreshToken(Long userId){
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        String refreshToken = requestAttributes.getRequest().getHeader("refreshToken");
//        if(StringUtils.isNullOrEmpty(refreshToken)){
//            throw new ConditionException("未登录！");
//        }
////        String dbRefreshToken = userService.getRefreshTokenByUserId(userId);
////        if(!dbRefreshToken.equals(refreshToken)){
////            throw new ConditionException("非法用户！");
////        }
//
//    }
}
