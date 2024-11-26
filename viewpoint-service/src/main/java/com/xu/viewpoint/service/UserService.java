package com.xu.viewpoint.service;


import com.alibaba.fastjson.JSONObject;
import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 根据id集合批量查询用户详细信息
     * @param ids
     */
    List<UserInfo> getUserInfoByUserIds(Set<Long> ids);

    /**
     * 更新用户详细信息
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 条件分页查询，条件  根据nick查询 可有可无
     * @param param
     */
    PageResult<UserInfo> pageListUserInfo(JSONObject param);

    /**
     * 双令牌登录
     * @param user
     */
    Map<String, Object> loginForDts(User user) throws Exception;

    /**
     * 退出登录
     * @param refreshToken
     * @param userId
     */
    void logout(String refreshToken, Long userId);

    /**
     * 根据refreshToken刷新accessToken
     * @param refreshToken
     */
    String refreshAccessToken(String refreshToken) throws Exception;
}
