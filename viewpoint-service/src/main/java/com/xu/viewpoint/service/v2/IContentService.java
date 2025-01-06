package com.xu.viewpoint.service.v2;

import com.xu.viewpoint.dao.domain.Content;

public interface IContentService {
    /**
     * 添加动态具体信息
     * @param content
     */
    void addContent(Content content);
}
