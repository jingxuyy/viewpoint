package com.xu.viewpoint.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.DanmuDao;
import com.xu.viewpoint.dao.domain.Danmu;
import com.xu.viewpoint.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * 异步添加弹幕
     * @param danmu
     */
    @Async
    @Override
    public void asyncAddDanmu(Danmu danmu) {
        danmuDao.addDanmu(danmu);
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

    /**
     * 查询弹幕，可以指定查询时间段内的弹幕
     *
     * @param videoId
     * @param startTime
     * @param endTime
     */
    @Override
    public List<Danmu> getDanmus(Long videoId, String startTime, String endTime) throws ParseException {

        // 优先查询redis中的弹幕，不存在则查询数据库中的弹幕，再把弹幕写入redis

        // 1. 查询redis
        String key = "danmu-video-" + videoId;
        String value = redisTemplate.opsForValue().get(key);
        List<Danmu> list;
        if(!StringUtils.isNullOrEmpty(value)){
            // 2. 查到弹幕，进行数据格式转换
            list = JSONArray.parseArray(value, Danmu.class);

            // 3. 若开始和结束时间不为空，则进行解析，筛选符合条件的弹幕
            if(!StringUtils.isNullOrEmpty(startTime) && !StringUtils.isNullOrEmpty(endTime)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = dateFormat.parse(startTime);
                Date endDate = dateFormat.parse(endTime);

                // 删选
                List<Danmu> childDanmuList = new ArrayList<>();
                for (Danmu danmu : list) {
                    Date createTime = danmu.getCreateTime();
                    if(createTime.after(startDate) && createTime.before(endDate)){
                        childDanmuList.add(danmu);
                    }
                }
                list = childDanmuList;
            }
        }else {

            // 4. redis中没有弹幕
            // 4.1 构造参数，从数据库中查询弹幕
            Map<String, Object> params = new HashMap<>();
            params.put("videoId", videoId);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            list = danmuDao.getDanmus(params);

            // 4.2 将弹幕存入redis
            String jsonString = JSONObject.toJSONString(list);
            redisTemplate.opsForValue().set(key, jsonString);
        }

        return list;
    }
}
