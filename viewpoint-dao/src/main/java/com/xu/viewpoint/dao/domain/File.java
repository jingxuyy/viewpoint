package com.xu.viewpoint.dao.domain;

import java.io.Serializable;
import java.util.Date;


public class File implements Serializable {
    /**
     * 主键id
     */

    private Long id;

    /**
     * 文件存储路径
     */

    private String url;

    /**
     * 文件类型
     */

    private String type;

    /**
     * 文件md5唯一标识串
     */

    private String md5;

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
     * 文件存储路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 文件存储路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 文件类型
     */
    public String getType() {
        return type;
    }

    /**
     * 文件类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 文件md5唯一标识串
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 文件md5唯一标识串
     */
    public void setMd5(String md5) {
        this.md5 = md5;
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