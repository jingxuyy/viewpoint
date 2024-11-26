package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: xuJing
 * @date: 2024/11/26 16:33
 */

@Mapper
public interface AuthRoleDao {

    /**
     * 根据角色的code查询角色
     * @param roleCode
     */
    AuthRole getRoleByCode(@Param("roleCode") String roleCode);
}
