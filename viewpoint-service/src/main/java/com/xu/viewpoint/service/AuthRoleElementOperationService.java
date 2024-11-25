package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.auth.AuthRoleElementOperation;

import java.util.List;
import java.util.Set;

public interface AuthRoleElementOperationService {

    /**
     * 根据角色id列表查询
     * @param roleIdSet
     */
    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet);
}
