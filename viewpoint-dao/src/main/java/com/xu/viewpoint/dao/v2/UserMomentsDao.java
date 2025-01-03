package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMomentsDao {

    /**
     * 向动态数据库插入数据
     * @param userMoment
     */
    Integer addUserMoments(@Param("userMoment") UserMoment userMoment);

    /**
     * 根据条件查询总数
     * @param params
     */
    Integer pageCountMoments(@Param("params") Map<String, Object> params);

    /**
     * 根据条件查询信息
     * @param params
     */
    List<UserMoment> pageListMoments(@Param("params") Map<String, Object> params);
}
