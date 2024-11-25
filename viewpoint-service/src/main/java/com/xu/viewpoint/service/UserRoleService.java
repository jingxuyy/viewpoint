package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.auth.UserRole;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/25 16:07
 */

public interface UserRoleService {

    /**
     * 根据用户的userId查询对应角色
     * @param userId
     */
    List<UserRole> getUserRoleByUserId(Long userId);
}
