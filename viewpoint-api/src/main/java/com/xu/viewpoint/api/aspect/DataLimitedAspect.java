//package com.xu.viewpoint.api.aspect;
//
//import com.xu.viewpoint.api.support.UserSupport;
//import com.xu.viewpoint.dao.annotation.ApiLimitedRole;
//import com.xu.viewpoint.dao.domain.UserMoment;
//import com.xu.viewpoint.dao.domain.auth.UserRole;
//import com.xu.viewpoint.dao.domain.constant.AuthRoleConstant;
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
//public class DataLimitedAspect {
//
//    @Autowired
//    private UserSupport userSupport;
//
//    @Autowired
//    private UserRoleService userRoleService;
//
//
//    @Pointcut("@annotation(com.xu.viewpoint.dao.annotation.DataLimited)")
//    public void check(){}
//
//    @Before("check()")
//    public void doBefore(JoinPoint joinPoint){
//        Long userId = userSupport.getCurrentUserId();
//        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
//
//        Set<String> userRoleSet = userRoleList.stream()
//                .map(UserRole::getRoleCode)
//                .collect(Collectors.toSet());
//
//        Object[] args = joinPoint.getArgs();
//        for (Object arg : args) {
//            if(arg instanceof UserMoment){
//                UserMoment userMoment = (UserMoment) arg;
//                String type = userMoment.getType();
//                if(userRoleSet.contains(AuthRoleConstant.ROLE_CODE_LV1) && ! "0".equals(type)){
//                    // Lv1角色 只能发布类型为0的动态
//                    throw new ConditionException("参数异常！");
//                }
//                // TODO 数据权限控制框架完成，具体权限未完成
//            }
//        }
//
//    }
//}
