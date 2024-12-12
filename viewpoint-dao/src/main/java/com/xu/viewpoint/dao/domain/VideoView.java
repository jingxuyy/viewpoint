package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频观看记录表
 * @TableName t_video_view
 */
public class VideoView implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * ip
     */
    private String ip;

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
     * 视频id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * 视频id
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
     * 客户端id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 客户端id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip
     */
    public void setIp(String ip) {
        this.ip = ip;
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