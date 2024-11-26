package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.auth.UserAuthorities;

public interface UserAuthService {

    /**
     * 获取当前用户所拥有的权限
     * @param userId
     */
    UserAuthorities getUserAuthorities(Long userId);

    /**
     * 用户注册，添加默认角色
     * @param id
     */
    void addUserDefaultRole(Long id);
}
