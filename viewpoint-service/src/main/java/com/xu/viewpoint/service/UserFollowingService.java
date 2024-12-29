//package com.xu.viewpoint.service;
//
//import com.xu.viewpoint.dao.domain.FollowingGroup;
//import com.xu.viewpoint.dao.domain.UserFollowing;
//import com.xu.viewpoint.dao.domain.UserInfo;
//
//import java.util.List;
//
///**
// * @author 86136
// */
//public interface UserFollowingService {
//
//    /**
//     * 添加用户关注
//     * @param userFollowing
//     */
//    void addUserFollowings(UserFollowing userFollowing);
//
//    /**
//     * 获取用户关注用户列表，以不同分组展示
//     * @param userId
//     */
//    List<FollowingGroup> getUserFollowingGroups(Long userId);
//
//    /**
//     * 获取用户所有粉丝，并标明互粉情况
//     * @param userId
//     */
//    List<UserFollowing> getUserFans(Long userId);
//
//    /**
//     * 根据给定的用户详细列表，检查当前用户userId是否存在关注情况
//     * @param list
//     * @param userId
//     */
//    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);
//
//    /**
//     * 用户添加自定义分组
//     * @param followingGroup
//     */
//    Long addUserFollowingGroup(FollowingGroup followingGroup);
//
//    /**
//     * 获取用户所拥有的全部分组
//     * @param userId
//     */
//    List<FollowingGroup> getUserFollowingGroup(Long userId);
//
//    /**
//     * 取消关注（删除关注）
//     * @param userId
//     * @param followingId
//     */
//    void deleteUserFollowing(Long userId, Long followingId);
//
//    /**
//     * 修改关注信息（即修改关注人分组）
//     * @param userFollowing
//     */
//    void updateUserFollowing(UserFollowing userFollowing);
//}
