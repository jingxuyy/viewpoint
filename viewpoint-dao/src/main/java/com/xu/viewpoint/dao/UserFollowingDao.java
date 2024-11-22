package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86136
 */
@Mapper
public interface UserFollowingDao {

    /**
     * 删除关注信息
     * @param userId
     * @param followingId
     */
    Integer deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    /**
     * 新增关注信息
     * @param userFollowing
     */
    Integer addUserFollowing(@Param("userFollowing") UserFollowing userFollowing);

    /**
     * 根据userId查询用户关注列表
     * @param userId
     */
    List<UserFollowing> getUserFollowingGroups(@Param("userId") Long userId);
}
