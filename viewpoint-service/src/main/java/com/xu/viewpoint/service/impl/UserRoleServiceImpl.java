package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.UserRoleDao;
import com.xu.viewpoint.dao.domain.auth.UserRole;
import com.xu.viewpoint.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/25 16:07
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 根据用户的userId查询对应角色
     *
     * @param userId
     */
    @Override
    public List<UserRole> getUserRoleByUserId(Long userId) {
        List<UserRole> result = userRoleDao.getUserRoleByUserId(userId);
        return result;
    }

    /**
     * 添加 userRole
     *
     * @param userRole
     */
    @Override
    public void addUserRole(UserRole userRole) {
        userRoleDao.addUserRole(userRole);
    }
}
