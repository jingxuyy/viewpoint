package com.xu.viewpoint.dao.domain.auth;


import java.io.Serializable;
import java.util.Date;

/**
 * 权限控制--角色页面菜单关联表
 * @TableName t_auth_role_menu
 */

public class AuthRoleMenu implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 角色id
     */

    private Long roleId;

    /**
     * 页面菜单id
     */

    private Long menuId;

    /**
     * 创建时间
     */

    private Date createTime;


    private AuthMenu authMenu;


    private static final long serialVersionUID = 1L;


    public AuthMenu getAuthMenu() {
        return authMenu;
    }

    public void setAuthMenu(AuthMenu authMenu) {
        this.authMenu = authMenu;
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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}