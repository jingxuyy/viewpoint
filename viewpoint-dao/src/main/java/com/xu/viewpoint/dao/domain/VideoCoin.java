package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频硬币表
 * @TableName t_video_coin
 */
public class VideoCoin implements Serializable {
    /**
     * 视频投稿id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频投稿id
     */
    private Long videoId;

    /**
     * 投币数
     */
    private Integer amount;

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
     * 视频投稿id
     */
    public Long getId() {
        return id;
    }

    /**
     * 视频投稿id
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
     * 投币数
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 投币数
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
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