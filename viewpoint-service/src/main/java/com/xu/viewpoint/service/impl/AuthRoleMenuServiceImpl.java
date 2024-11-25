package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.AuthRoleMenuDao;
import com.xu.viewpoint.dao.domain.auth.AuthRoleMenu;
import com.xu.viewpoint.service.AuthRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author: xuJing
 * @date: 2024/11/25 17:07
 */
@Service
public class AuthRoleMenuServiceImpl implements AuthRoleMenuService {

    @Autowired
    private AuthRoleMenuDao authRoleMenuDao;


    /**
     * 根据角色id列表查询 角色与页面展示关联表
     *
     * @param roleIdSet
     */
    @Override
    public List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet) {

        List<AuthRoleMenu> result = authRoleMenuDao.getRoleMenusByRoleIds(roleIdSet);
        return result;
    }
}
