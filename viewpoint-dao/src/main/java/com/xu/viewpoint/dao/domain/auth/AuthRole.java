package com.xu.viewpoint.dao.domain.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限控制--角色表
 * @TableName t_auth_role
 */

public class AuthRole implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 角色名称
     */

    private String name;

    /**
     * 角色唯一编码
     */

    private String code;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;


    private static final long serialVersionUID = 1L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}