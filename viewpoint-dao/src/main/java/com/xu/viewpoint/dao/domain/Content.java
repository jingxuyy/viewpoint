package com.xu.viewpoint.dao.domain;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * 动态内容表
 * @TableName t_content
 */

public class Content implements Serializable {
    /**
     * 
     */

    private Long id;

    /**
     * 内容详情
     */

    private JSONObject contentDetail;

    /**
     * 创建时间
     */

    private Date createTime;


    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    public JSONObject getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(JSONObject contentDetail) {
        this.contentDetail = contentDetail;
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