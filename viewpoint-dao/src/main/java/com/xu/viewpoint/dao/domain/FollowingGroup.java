package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户关注分组表
 * @author 86136
 * @TableName t_following_group
 */

public class FollowingGroup implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 用户id
     */

    private Long userId;

    /**
     * 关注分组名称
     */

    private String name;

    /**
     * 关注分组类型：0特别关注  1悄悄关注 2默认分组  3用户自定义分组
     */

    private String type;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;

    private List<UserInfo> followingUserInfoList;

    public List<UserInfo> getFollowingUserInfoList() {
        return followingUserInfoList;
    }

    public void setFollowingUserInfoList(List<UserInfo> followingUserInfoList) {
        this.followingUserInfoList = followingUserInfoList;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    private static final long serialVersionUID = 1L;


}