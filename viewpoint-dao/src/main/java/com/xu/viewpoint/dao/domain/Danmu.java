package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 弹幕记录表
 * @TableName t_danmu
 */
public class Danmu implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频Id
     */
    private Long videoId;

    /**
     * 弹幕内容
     */
    private String content;

    /**
     * 弹幕出现时间
     */
    private String danmuTime;

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
     * 视频Id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * 视频Id
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    /**
     * 弹幕内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 弹幕内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 弹幕出现时间
     */
    public String getDanmuTime() {
        return danmuTime;
    }

    /**
     * 弹幕出现时间
     */
    public void setDanmuTime(String danmuTime) {
        this.danmuTime = danmuTime;
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