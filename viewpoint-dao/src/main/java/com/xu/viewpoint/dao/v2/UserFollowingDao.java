package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFollowingDao {

    /**
     * 根据 userId和followingId删除信息
     * @param userId
     * @param followingId
     */
    Integer deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);


    /**
     * 根据 userFollowing添加记录
     * @param userFollowing
     */
    Integer addUserFollowing(@Param("userFollowing") UserFollowing userFollowing);

    /**
     * 根据userId查询记录
     * @param userId
     */
    List<UserFollowing> getUserFollowings(@Param("userId") Long userId);

    /**
     * 根据followingId查询记录
     * @param followingId
     */
    List<UserFollowing> getUserFans(@Param("followingId") Long followingId);

    /**
     * 更新被关注用户的分组 将关注的用户移动到其它分组
     * @param userFollowing
     */
    Integer updateUserFollowing(@Param("userFollowing") UserFollowing userFollowing);
}
