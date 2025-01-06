package com.xu.viewpoint.service.v2.impl;

import com.xu.viewpoint.dao.domain.VideoTag;
import com.xu.viewpoint.dao.v2.VideoTagDao;
import com.xu.viewpoint.service.v2.IVideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2025/1/6 15:35
 */
@Service
public class VideoTagService implements IVideoTagService {

    @Autowired
    private VideoTagDao videoTagDao;


    /**
     * 批量插入数据
     *
     * @param tagList
     */
    @Override
    public void batchAddVideoTags(List<VideoTag> tagList) {
        videoTagDao.batchAddVideoTags(tagList);
    }
}
