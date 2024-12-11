package com.xu.viewpoint.dao.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基本信息表
 * @author 86136
 * @TableName t_user_info
 */
@Document(indexName = "user-infos")
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 用户id
     */

    private Long userId;

    /**
     * 昵称
     */
    @Field(type = FieldType.Text)
    private String nick;

    /**
     * 头像
     */

    private String avatar;

    /**
     * 签名
     */

    private String sign;

    /**
     * 性别：0男 1女 2未知
     */

    private String gender;

    /**
     * 生日
     */

    private String birth;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    private Date updateTime;


    private Boolean followed;

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 昵称
     */
    public String getNick() {
        return nick;
    }

    /**
     * 昵称
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 签名
     */
    public String getSign() {
        return sign;
    }

    /**
     * 签名
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 性别：0男 1女 2未知
     */
    public String getGender() {
        return gender;
    }

    /**
     * 性别：0男 1女 2未知
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 生日
     */
    public String getBirth() {
        return birth;
    }

    /**
     * 生日
     */
    public void setBirth(String birth) {
        this.birth = birth;
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