package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户关注表
 * @author 86136
 * @TableName t_user_following
 */

public class UserFollowing implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 用户id
     */

    private Long userId;

    /**
     * 关注用户id
     */

    private Long followingId;

    /**
     * 关注分组id
     */

    private Long groupId;

    /**
     * 创建时间
     */

    private Date createTime;

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

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private static final long serialVersionUID = 1L;


}