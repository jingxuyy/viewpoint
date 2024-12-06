package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.Danmu;

import java.text.ParseException;
import java.util.List;

public interface DanmuService {

    /**
     * 添加弹幕
     * @param danmu
     */
    void addDanmu(Danmu danmu);

    /**
     * 异步添加弹幕
     * @param danmu
     */
    void asyncAddDanmu(Danmu danmu);


    /**
     * 保存弹幕到redis
     * @param danmu
     */
    void addDanmuToRedis(Danmu danmu);

    /**
     * 查询弹幕，可以指定查询时间段内的弹幕
     * @param videoId
     * @param startTime
     * @param endTime
     */
    List<Danmu> getDanmus(Long videoId, String startTime, String endTime) throws ParseException;
}
