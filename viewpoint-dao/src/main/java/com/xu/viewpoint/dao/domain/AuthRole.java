package com.xu.viewpoint.dao.domain;

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

    /**
     * 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 角色唯一编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 角色唯一编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}