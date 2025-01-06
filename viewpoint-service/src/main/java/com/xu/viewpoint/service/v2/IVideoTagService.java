package com.xu.viewpoint.service.v2;

import com.xu.viewpoint.dao.domain.VideoTag;

import java.util.List;

public interface IVideoTagService {

    /**
     * 批量插入数据
     * @param tagList
     */
    void batchAddVideoTags(List<VideoTag> tagList);
}
