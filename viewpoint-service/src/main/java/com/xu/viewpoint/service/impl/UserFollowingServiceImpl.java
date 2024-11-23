package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.FollowingGroupDao;
import com.xu.viewpoint.dao.UserFollowingDao;
import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserFollowing;
import com.xu.viewpoint.dao.domain.UserInfo;
import com.xu.viewpoint.dao.domain.constant.UserConstant;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.service.FollowingGroupService;
import com.xu.viewpoint.service.UserFollowingService;
import com.xu.viewpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: xuJing
 * @date: 2024/11/22 10:05
 */
@Service
public class UserFollowingServiceImpl implements UserFollowingService {

    @Autowired
    private UserFollowingDao userFollowingDao;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserService userService;

    @Transactional
    public void addUserFollowings(UserFollowing userFollowing){

        // 1. 添加关注，获取关注的分组id，如果为空，则默认将当前关注设置为默认分组
        Long groupId = userFollowing.getGroupId();
        if(groupId == null){
            FollowingGroup defaultFollowingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(defaultFollowingGroup.getId());
        }else {
            // 2. 如果存在分组id，则查询分组是否存在
            FollowingGroup userSetFollowingGroup = followingGroupService.getById(groupId);
            if(userSetFollowingGroup == null){
                throw new ConditionException("分组不存在！");
            }
        }

        // 3. 根据关注的用户id查询，被关注的用户是否存在
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if(user == null){
            throw new ConditionException("关注的用户不存在！");
        }

        // 4. 首先删除可能存在的关注，主要是因为将修改和新增放在同一个方法里，若是修改则先删除原本的，再添加；若是新增，则删除操作相当于没用
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);

        // 5. 设置新增时间，新增
        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }

    /**
     * 获取当前用户的关注分组列表，需要返回所有分组，及其每个分组对应的关注用户信息
     * @param userId
     */
    public List<FollowingGroup> getUserFollowingGroups(Long userId){

        // 1. 根据userId 查询当前用户关注用户,并取得所有关注用户id的集合
        List<UserFollowing> list = userFollowingDao.getUserFollowings(userId);
        Set<Long> followingIdSet = list.stream()
                .map(UserFollowing::getFollowingId)
                .collect(Collectors.toSet());

        // 2. 根据关注的用户id集合，查询对应的用户具体详细信息
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet.size()>0){
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }

        // 3. 根据每个userFollowing对象，设置其关注用户的具体信息
        for (UserFollowing userFollowing : list) {
            for (UserInfo userInfo : userInfoList) {
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }

        // 4. 根据用户id查询，用户创建的分组和默认分组
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);

        // 5. 提供一个全部分组，即把所有关注对象放在这个组里
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);

        // 6. 提供一个分组集合，将全部分组添加
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);

        // 7. 再根据不同分组，创建不同结果，装入分组集合
        for (FollowingGroup followingGroup : groupList) {
            List<UserInfo> infoList = new ArrayList<>();
            for (UserFollowing userFollowing : list) {
                if(followingGroup.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            followingGroup.setFollowingUserInfoList(infoList);
            result.add(followingGroup);
        }

        return result;
    }


    /**
     * 获取当前用户的粉丝列表
     * @param userId
     */
    public List<UserFollowing> getUserFans(Long userId){
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);

        Set<Long> fanIdSet = fanList.stream()
                .map(UserFollowing::getUserId)
                .collect(Collectors.toSet());

        // 2. 根据粉丝id集合，查询对应的用户具体详细信息
        List<UserInfo> userInfoList = new ArrayList<>();
        if(fanIdSet.size()>0){
            userInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }

        // 3. 查看有没有互相关注的情况
        // 3.1 获取当前用户的关注列表
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
        for (UserFollowing fan : fanList) {
            for (UserInfo userInfo : userInfoList) {
                if(fan.getUserId().equals(userInfo.getUserId())){
                    fan.setUserInfo(userInfo);
                    userInfo.setFollowed(false);
                }
            }
            // 3.2 如果互粉，则设置状态
            for (UserFollowing userFollowing : followingList) {
                if(fan.getUserId().equals(userFollowing.getFollowingId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }
}
