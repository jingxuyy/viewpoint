package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.AuthRoleElementOperationDao;
import com.xu.viewpoint.dao.domain.auth.AuthRoleElementOperation;
import com.xu.viewpoint.service.AuthRoleElementOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author: xuJing
 * @date: 2024/11/25 17:04
 */

@Service
public class AuthRoleElementOperationServiceImpl implements AuthRoleElementOperationService {

    @Autowired
    private AuthRoleElementOperationDao authRoleElementOperationDao;


    /**
     * 根据角色id列表查询
     *
     * @param roleIdSet
     */
    @Override
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        List<AuthRoleElementOperation> result = authRoleElementOperationDao.getRoleElementOperationsByRoleIds(roleIdSet);
        return result;
    }
}
