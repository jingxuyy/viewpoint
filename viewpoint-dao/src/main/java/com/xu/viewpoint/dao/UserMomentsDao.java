package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMomentsDao {

    /**
     * 插入一条动态信息
     * @param userMoment
     */
    Integer addUserMoments(@Param("userMoment") UserMoment userMoment);
}
