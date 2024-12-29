package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.RefreshTokenDetail;
import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 根据用户id集合查询用户详细信息
     * @param ids
     * @return
     */
    List<UserInfo> getUserInfoByUserIds(@Param("ids") Set<Long> ids);

    /**
     * 更新用户详细信息
     * @param userInfo
     */
    Integer updateUserInfo(@Param("userInfo") UserInfo userInfo);

    /**
     * 条件分页查询总数
     * @param param
     * @return
     */
    Integer pageCountUserInfo(@Param("param") Map<String, Object> param);

    /**
     * 条件分页查询
     * @param param
     */
    List<UserInfo> pageListUserInfo(@Param("param") Map<String, Object> param);

    /**
     * 根据userId删除refreshToken
     * @param refreshToken
     * @param userId
     */
    Integer deleteRefreshToken(@Param("refreshToken") String refreshToken,
                               @Param("userId") Long userId);

    /**
     * 添加refreshToken
     * @param refreshToken
     * @param userId
     * @param date
     */
    Integer addRefreshToken(@Param("refreshToken") String refreshToken,
                            @Param("userId") Long userId,
                            @Param("date") Date date);

    /**
     * 根据refreshToken查询RefreshTokenDetail
     * @param refreshToken
     */
    RefreshTokenDetail getRefreshTokenDetailByRefreshToken(@Param("refreshToken") String refreshToken);

    /**
     * 根据userId查询 refreshToken
     * @param userId
     * @return
     */
    String getRefreshTokenByUserId(@Param("userId") Long userId);
}
