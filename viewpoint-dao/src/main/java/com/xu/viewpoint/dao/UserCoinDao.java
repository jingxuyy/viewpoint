package com.xu.viewpoint.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserCoinDao {

    /**
     * 根据用户id查询总的硬币数
     * @param userId
     */
    Integer getUserCoinsAmount(@Param("userId") Long userId);

    /**
     * 修改用户硬币数
     * @param userId
     * @param amount
     * @param date
     */
    Integer updateUserCoinsAmount(@Param("userId") Long userId,
                                  @Param("amount") Integer amount,
                                  @Param("date") Date date);
}
