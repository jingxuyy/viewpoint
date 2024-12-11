package com.xu.viewpoint.dao.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 视频投稿记录表
 * @TableName t_video
 */
@Document(indexName = "videos")
public class Video implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Field(type = FieldType.Long)
    private Long userId;

    /**
     * 视频链接
     */
    private String url;

    /**
     * 封面链接
     */
    private String thumbnail;

    /**
     * 视频标题
     */
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 视频类型：0原创 1转载
     */
    private String type;

    /**
     * 视频时长
     */
    private String duration;

    /**
     * 所在分区：0鬼畜 1音乐 2电影
     */
    private String area;

    /**
     * 视频简介
     */
    @Field(type = FieldType.Text)
    private String description;

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

    /**
     * 视频标签列表
     */
    private List<VideoTag> videoTagList;

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
     * 视频链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 视频链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 封面链接
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 封面链接
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 视频标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 视频标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 视频类型：0原创 1转载
     */
    public String getType() {
        return type;
    }

    /**
     * 视频类型：0原创 1转载
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 视频时长
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 视频时长
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 所在分区：0鬼畜 1音乐 2电影
     */
    public String getArea() {
        return area;
    }

    /**
     * 所在分区：0鬼畜 1音乐 2电影
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 视频简介
     */
    public String getDescription() {
        return description;
    }

    /**
     * 视频简介
     */
    public void setDescription(String description) {
        this.description = description;
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

    public List<VideoTag> getVideoTagList() {
        return videoTagList;
    }

    public void setVideoTagList(List<VideoTag> videoTagList) {
        this.videoTagList = videoTagList;
    }
}