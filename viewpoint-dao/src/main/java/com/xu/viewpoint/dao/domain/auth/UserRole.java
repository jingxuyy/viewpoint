package com.xu.viewpoint.dao.domain.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 * @TableName t_user_role
 */

public class UserRole implements Serializable {
    /**
     * 
     */

    private Long id;

    /**
     * 用户id
     */

    private Long userId;

    /**
     * 角色id
     */

    private Long roleId;

    /**
     * 创建时间
     */

    private Date createTime;


    private String roleName;


    private String roleCode;


    private static final long serialVersionUID = 1L;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}