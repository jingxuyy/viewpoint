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

    private Long userid;

    /**
     * 角色id
     */

    private Long roleId;

    /**
     * 创建时间
     */

    private Date createTime;


    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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