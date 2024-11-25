package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.domain.auth.AuthRoleElementOperation;
import com.xu.viewpoint.dao.domain.auth.AuthRoleMenu;
import com.xu.viewpoint.dao.domain.auth.UserAuthorities;
import com.xu.viewpoint.dao.domain.auth.UserRole;
import com.xu.viewpoint.service.AuthRoleService;
import com.xu.viewpoint.service.UserAuthService;
import com.xu.viewpoint.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: xuJing
 * @date: 2024/11/25 15:57
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthRoleService authRoleService;

    /**
     * 获取当前用户所拥有的权限
     *
     * @param userId
     */
    @Override
    public UserAuthorities getUserAuthorities(Long userId) {

        // 1. 根据用户id查询当前用户的角色，一个用户可能拥有多种角色，一对多
        // 连表查询，1）将用户与角色关联表 t_user_role 与 角色表 t_auth_role连表，一次性查询用户的角色信息
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);

        // 2. 获取当前用户拥有的角色id
        Set<Long> roleIdSet = userRoleList.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());

        // 3. 根据角色id查询角色对应的权限，①：页面访问权限 ②：按钮操作权限
        // 3.1 按钮操作权限
        List<AuthRoleElementOperation> roleElementOperationList = authRoleService.getRoleElementOperationsByRoleIds(roleIdSet);

        // 3.2 页面展示权限
        List<AuthRoleMenu> authRoleMenuList = authRoleService.getRoleMenusByRoleIds(roleIdSet);

        // 赋值返回
        UserAuthorities userAuthorities = new UserAuthorities();
        userAuthorities.setAuthRoleElementOperationList(roleElementOperationList);
        userAuthorities.setAuthRoleMenuList(authRoleMenuList);
        return userAuthorities;
    }
}
