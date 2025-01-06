package com.xu.viewpoint.service.v2.impl;

import com.xu.viewpoint.dao.domain.Content;
import com.xu.viewpoint.dao.v2.ContentDao;
import com.xu.viewpoint.service.v2.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xuJing
 * @date: 2025/1/6 16:16
 */
@Service
public class ContentService implements IContentService {

    @Autowired
    private ContentDao contentDao;

    /**
     * 添加动态具体信息
     *
     * @param content
     */
    @Override
    public void addContent(Content content) {
        contentDao.addContent(content);
    }
}
