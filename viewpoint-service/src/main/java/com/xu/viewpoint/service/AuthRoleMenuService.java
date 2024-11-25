package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

public interface AuthRoleMenuService {

    /**
     * 根据角色id列表查询 角色与页面展示关联表
     * @param roleIdSet
     */
    List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet);
}
