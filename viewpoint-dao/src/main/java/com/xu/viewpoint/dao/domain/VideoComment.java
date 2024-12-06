package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 视频评论表
 * @TableName t_video_comment
 */
public class VideoComment implements Serializable {
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
     * 评论
     */
    private String comment;

    /**
     * 回复用户id
     */
    private Long replyUserId;

    /**
     * 根节点评论id
     */
    private Long rootId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private List<VideoComment> childList;


    private UserInfo userInfo;

    private UserInfo replyUserInfo;

    private static final long serialVersionUID = 1L;


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getReplyUserInfo() {
        return replyUserInfo;
    }

    public void setReplyUserInfo(UserInfo replyUserInfo) {
        this.replyUserInfo = replyUserInfo;
    }

    public List<VideoComment> getChildList() {
        return childList;
    }

    public void setChildList(List<VideoComment> childList) {
        this.childList = childList;
    }

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
     * 评论
     */
    public String getComment() {
        return comment;
    }

    /**
     * 评论
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 回复用户id
     */
    public Long getReplyUserId() {
        return replyUserId;
    }

    /**
     * 回复用户id
     */
    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    /**
     * 根节点评论id
     */
    public Long getRootId() {
        return rootId;
    }

    /**
     * 根节点评论id
     */
    public void setRootId(Long rootId) {
        this.rootId = rootId;
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