package com.xu.viewpoint.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.DanmuDao;
import com.xu.viewpoint.dao.domain.Danmu;
import com.xu.viewpoint.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: xuJing
 * @date: 2024/12/6 16:13
 */
@Service
public class DanmuServiceImpl implements DanmuService {

    @Autowired
    private DanmuDao danmuDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 添加弹幕
     *
     * @param danmu
     */
    @Override
    public void addDanmu(Danmu danmu) {
        danmuDao.addDanmu(danmu);
    }

    /**
     * 查询指定时间内弹幕
     *
     * @param params
     */
    @Override
    public List<Danmu> getDanmu(Map<String, Object> params) {
        return danmuDao.getDanmus(params);
    }


    /**
     * 将弹幕存入redis
     * @param danmu
     */
    @Override
    public void addDanmuToRedis(Danmu danmu){
        String key = "danmu-video-" + danmu.getVideoId();
        String value = redisTemplate.opsForValue().get(key);
        List<Danmu> list = new ArrayList<>();
        if(!StringUtils.isNullOrEmpty(value)){
            list = JSONArray.parseArray(value, Danmu.class);
        }
        list.add(danmu);
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
    }
}
