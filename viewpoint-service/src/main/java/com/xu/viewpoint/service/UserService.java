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
    String login(User user);
}
