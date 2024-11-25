package com.xu.viewpoint.dao.domain.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限控制--角色与元素操作关联表
 * @TableName t_auth_role_element_operation
 */

public class AuthRoleElementOperation implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 角色id
     */

    private Long roleId;

    /**
     * 元素操作id
     */

    private Long elementOperationId;

    /**
     * 创建时间
     */

    private Date createTime;

    private AuthElementOperation authElementOperation;


    private static final long serialVersionUID = 1L;

    public AuthElementOperation getAuthElementOperation() {
        return authElementOperation;
    }

    public void setAuthElementOperation(AuthElementOperation authElementOperation) {
        this.authElementOperation = authElementOperation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getElementOperationId() {
        return elementOperationId;
    }

    public void setElementOperationId(Long elementOperationId) {
        this.elementOperationId = elementOperationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}