//package com.xu.viewpoint.api.aspect;
//
//import com.xu.viewpoint.api.support.UserSupport;
//import com.xu.viewpoint.dao.annotation.ApiLimitedRole;
//import com.xu.viewpoint.dao.domain.auth.UserRole;
//import com.xu.viewpoint.dao.domain.exception.ConditionException;
//import com.xu.viewpoint.service.UserRoleService;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @author: xuJing
// * @date: 2024/11/26 9:38
// */
//@Aspect
//@Component
//@Order(1)
//public class ApiLimitedRoleAspect {
//
//    @Autowired
//    private UserSupport userSupport;
//
//    @Autowired
//    private UserRoleService userRoleService;
//
//
//    @Pointcut("@annotation(com.xu.viewpoint.dao.annotation.ApiLimitedRole)")
//    public void check(){}
//
//    @Before("check() && @annotation(apiLimitedRole)")
//    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole){
//        Long userId = userSupport.getCurrentUserId();
//        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
//        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
//
//        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
//        Set<String> userRoleSet = userRoleList.stream()
//                .map(UserRole::getRoleCode)
//                .collect(Collectors.toSet());
//
//        userRoleSet.retainAll(limitedRoleCodeSet);
//        if(userRoleSet.size() > 0){
//            throw new ConditionException("无权限！");
//        }
//    }
//}
