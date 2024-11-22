package com.xu.viewpoint.service;


import com.xu.viewpoint.dao.domain.User;

/**
 * @author: xuJing
 * @date: 2024/11/21 10:02
 */

public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 用户登录
     * @param user
     * @return token
     */
    String login(User user) throws Exception;

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
//    User getUserInfo(Long userId);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    User getCurrentUser(Long userId);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user) throws Exception;

    /**
     * 根据id查询用户
     * @param followingId
     */
    User getUserById(Long id);
}
