package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.Danmu;

import java.util.List;
import java.util.Map;

public interface DanmuService {

    /**
     * 添加弹幕
     * @param danmu
     */
    void addDanmu(Danmu danmu);

    /**
     * 查询指定时间内弹幕
     * @param params
     */
    List<Danmu> getDanmu(Map<String, Object> params);

    /**
     * 保存弹幕到redis
     * @param danmu
     */
    void addDanmuToRedis(Danmu danmu);
}
