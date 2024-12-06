package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.Danmu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DanmuDao {

    /**
     * 添加弹幕
     * @param danmu
     */
    Integer addDanmu(@Param("danmu") Danmu danmu);

    /**
     * 查询指定时间内弹幕
     * @param params
     */
    List<Danmu> getDanmus(@Param("params") Map<String, Object> params);
}
