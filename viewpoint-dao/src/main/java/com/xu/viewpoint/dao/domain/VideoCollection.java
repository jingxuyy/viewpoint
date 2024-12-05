package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频收藏记录表
 * @TableName t_video_collection
 */
public class VideoCollection implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 视频投稿id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收藏分组id
     */
    private Long groupId;

    /**
     * 创建时间
     */
    private Date createTime;

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
     * 视频投稿id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * 视频投稿id
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
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
     * 收藏分组id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 收藏分组id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
}