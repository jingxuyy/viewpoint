package com.xu.viewpoint.dao.domain;


import java.io.Serializable;

import java.util.Date;


/**
* 用户表
* @author 86136
 * @TableName t_user
*/
public class User implements Serializable {

    /**
    * 主键
    */

    private Long id;
    /**
    * 手机号
    */

    private String phone;
    /**
    * 邮箱
    */

    private String email;
    /**
    * 密码
    */

    private String password;
    /**
    * 盐值
    */

    private String salt;
    /**
    * 创建时间
    */

    private Date createTime;
    /**
    * 更新时间
    */

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
