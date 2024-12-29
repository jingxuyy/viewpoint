package com.xu.viewpoint.service.v2;

import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUserService {

    /**
     * 用户注册
     * @param user
     */
    void register(User user);



    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    User getCurrentUser(Long userId);




    /**
     * 更新用户详细信息
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);



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
     * 更新用户基本信息
     * @param user
     */
    void updateUserBasicInfo(User user) throws Exception;


    // --------------------------------other------------------------------------

    /**
     * 根据id查询用户基本信息
     * @param id
     */
    User getUserById(Long id);


    /**
     * 根据id集合批量查询用户详细信息
     * @param ids
     */
    List<UserInfo> getUserInfoByUserIds(Set<Long> ids);
}
