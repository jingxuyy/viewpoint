package com.xu.viewpoint.dao.domain.auth;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/25 16:00
 */

public class UserAuthorities {

    private List<AuthRoleElementOperation> authRoleElementOperationList;

    private List<AuthRoleMenu> authRoleMenuList;


    public List<AuthRoleElementOperation> getAuthRoleElementOperationList() {
        return authRoleElementOperationList;
    }

    public void setAuthRoleElementOperationList(List<AuthRoleElementOperation> authRoleElementOperationList) {
        this.authRoleElementOperationList = authRoleElementOperationList;
    }

    public List<AuthRoleMenu> getAuthRoleMenuList() {
        return authRoleMenuList;
    }

    public void setAuthRoleMenuList(List<AuthRoleMenu> authRoleMenuList) {
        this.authRoleMenuList = authRoleMenuList;
    }
}
