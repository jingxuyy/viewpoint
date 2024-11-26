package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 刷新令牌记录表
 * @TableName t_refresh_token
 */

public class RefreshTokenDetail implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 用户id
     */

    private Long userId;

    /**
     * 刷新令牌
     */

    private String refreshToken;

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
     * 刷新令牌
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * 刷新令牌
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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