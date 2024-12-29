//package com.xu.viewpoint.service.impl;
//
//import com.xu.viewpoint.dao.AuthRoleDao;
//import com.xu.viewpoint.dao.domain.auth.AuthRole;
//import com.xu.viewpoint.dao.domain.auth.AuthRoleElementOperation;
//import com.xu.viewpoint.dao.domain.auth.AuthRoleMenu;
//import com.xu.viewpoint.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * @author: xuJing
// * @date: 2024/11/25 16:08
// */
//@Service
//public class AuthRoleServiceImpl implements AuthRoleService {
//
//
//    @Autowired
//    private AuthRoleElementOperationService authRoleElementOperationService;
//
//    @Autowired
//    private AuthRoleMenuService authRoleMenuService;
//
//    @Autowired
//    private AuthRoleDao authRoleDao;
//
//
//    /**
//     * 根据角色id列表查询 角色与操作关联表
//     *
//     * @param roleIdSet
//     */
//    @Override
//    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
//        List<AuthRoleElementOperation> result = authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdSet);
//        return result;
//    }
//
//    /**
//     * 根据角色id列表查询 角色与页面展示关联表
//     *
//     * @param roleIdSet
//     */
//    @Override
//    public List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIdSet) {
//        List<AuthRoleMenu> result = authRoleMenuService.getRoleMenusByRoleIds(roleIdSet);
//        return result;
//    }
//
//    /**
//     * 根据角色编码获取角色
//     *
//     * @param roleCode
//     */
//    @Override
//    public AuthRole getRoleByCode(String roleCode) {
//        AuthRole authRole = authRoleDao.getRoleByCode(roleCode);
//        return authRole;
//    }
//}
