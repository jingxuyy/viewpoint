package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleMenuDao {

    /**
     * 根据角色Id列表查询 角色与页面展示关联表
     * @param roleIdSet
     */
    List<AuthRoleMenu> getRoleMenusByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);
}
