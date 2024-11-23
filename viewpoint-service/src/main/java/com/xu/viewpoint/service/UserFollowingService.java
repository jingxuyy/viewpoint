package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.UserFollowing;

import java.util.List;

/**
 * @author 86136
 */
public interface UserFollowingService {

    /**
     * 添加用户关注
     * @param userFollowing
     */
    void addUserFollowings(UserFollowing userFollowing);

    /**
     * 获取用户关注用户列表，以不同分组展示
     * @param userId
     */
    List<FollowingGroup> getUserFollowingGroups(Long userId);

    /**
     * 获取用户所有粉丝，并标明互粉情况
     * @param userId
     */
    List<UserFollowing> getUserFans(Long userId);
}
