package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleElementOperationDao {

    /**
     * 根据角色id列表查询,连表查询
     * @param roleIdSet
     */
    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);
}
