package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 86136
 */
@Mapper
public interface UserDao {
    /**
     * 根据phone获取用户
     * @param phone
     */
    User getUserByPhone(@Param("phone") String phone);

    /**
     * 添加用户
     * @param user
     */
    Integer addUser(@Param("user") User user);

    /**
     * 添加用户详情信息
     * @param userInfo
     */
    Integer addUserInfo(@Param("userInfo") UserInfo userInfo);

    /**
     * 根据userId获取用户
     * @param id
     */
    User getUserById(@Param("id") Long id);

    /**
     * 根据用户id查询用户信息
     * @param userId
     */
    UserInfo getUserInfoById(@Param("userId")Long userId);

    /**
     * 根据phone和email其中之一查询用户（phone 和 email只会有一个存在）
     * @param phoneOrEmail
     */
    User getUserByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail);

    /**
     * 更新用户
     * @param user
     */
    Integer updateUser(@Param("user") User user);
}
