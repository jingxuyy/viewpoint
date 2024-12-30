package com.xu.viewpoint.service.v2.impl;

import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserFollowing;
import com.xu.viewpoint.dao.domain.UserInfo;
import com.xu.viewpoint.dao.domain.constant.UserConstant;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.dao.v2.UserFollowingDao;
import com.xu.viewpoint.service.v2.IUserFollowingService;
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
 * @date: 2024/12/28 17:13
 */
@Service
public class UserFollowingService implements IUserFollowingService {

    @Autowired
    private UserFollowingDao userFollowingDao;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserService userService;

    /**
     * 添加用户关注
     *
     * @param userFollowing
     */
    @Transactional
    @Override
    public void addUserFollowings(UserFollowing userFollowing) {

        // . 根据关注的用户id查询，被关注的用户是否存在
        Long followingId = userFollowing.getFollowingId();
        if(followingId == null){
            throw new ConditionException("关注的用户不存在！");
        }

        User user = userService.getUserById(followingId);
        if(user == null){
            throw new ConditionException("关注的用户不存在！");
        }


        // . 添加关注，获取关注的分组id，如果为空，则默认将当前关注设置为默认分组
        Long groupId = userFollowing.getGroupId();
        if(groupId == null){
            FollowingGroup defaultFollowingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(defaultFollowingGroup.getId());
        }else {
            // . 如果存在分组id，则查询分组是否存在
            FollowingGroup userSetFollowingGroup = followingGroupService.getById(groupId);
            if(userSetFollowingGroup == null){
                throw new ConditionException("分组不存在！");
            }
        }



        // . 首先删除可能存在的关注，主要是因为将修改和新增放在同一个方法里，若是修改则先删除原本的，再添加；若是新增，则删除操作相当于没用
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);

        // . 设置新增时间，新增
        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }


    /**
     * 取消关注（删除关注）
     *
     * @param userId
     * @param followingId
     */
    @Override
    public void deleteUserFollowing(Long userId, Long followingId) {
        userFollowingDao.deleteUserFollowing(userId, followingId);
    }

    /**
     * 获取用户关注用户列表，以不同分组展示
     *
     * @param userId
     */
    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        // 1. 根据userId 查询当前用户关注的用户
        List<UserFollowing> list = userFollowingDao.getUserFollowings(userId);

        // 取得所有关注用户id的集合
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

        // 5. 提供一个全部分组，即把所有关注对象放在这个组里  这个是全部关注，即不分组的情况
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
     * 获取用户所有粉丝，并标明互粉情况
     *
     * @param userId
     */
    @Override
    public List<UserFollowing> getUserFans(Long userId) {

        // 从数据库t_user_following中查询关注情况，获取粉丝id 即 查询followingId = userId
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);

        // 获取粉丝用户id的集合
        Set<Long> fanIdSet = fanList.stream()
                .map(UserFollowing::getUserId)
                .collect(Collectors.toSet());

        // 2. 根据粉丝id集合，查询对应的用户具体详细信息
        List<UserInfo> userInfoList = new ArrayList<>();
        if(fanIdSet.size()>0){
            userInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }

        // 3. 查看有没有互相关注的情况  UserInfo 类 存在 followed属性，用来表示有没有互粉
        // 3.1 获取当前用户的关注列表
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);

        // 依次遍历粉丝关注集合
        for (UserFollowing fan : fanList) {
            // 依次遍历粉丝详细信息集合，将粉丝详细信息设置到 关注UserFollowing对象中
            for (UserInfo userInfo : userInfoList) {
                if(fan.getUserId().equals(userInfo.getUserId())){
                    fan.setUserInfo(userInfo);
                    userInfo.setFollowed(false);
                }
            }
            // 3.2 如果互粉，则设置状态
            // 遍历当前用户关注集合，若关注的用户id即followingId和粉丝的关注集合中userId相同则是互粉
            for (UserFollowing userFollowing : followingList) {
                if(fan.getUserId().equals(userFollowing.getFollowingId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    /**
     * 根据给定的用户详细列表，检查当前用户userId是否存在关注情况
     *
     * @param list
     * @param userId
     */
    @Override
    public List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId) {
        return null;
    }

    /**
     * 用户添加自定义分组
     *
     * @param followingGroup
     */
    @Override
    public Long addUserFollowingGroup(FollowingGroup followingGroup) {

        // 用添加自定义分组，用户id 分组名

        // 设置创建时间和类型
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.insertFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    /**
     * 获取用户所拥有的全部分组
     *
     * @param userId
     */
    @Override
    public List<FollowingGroup> getUserFollowingGroup(Long userId) {
        return followingGroupService.getUserFollowingGroup(userId);
    }



    /**
     * 修改关注信息（即修改关注人分组） 将关注的用户移动到其它分组
     *
     * @param userFollowing
     */
    @Override
    public void updateUserFollowing(UserFollowing userFollowing) {

        // 获取要移动的目的分组id
        Long groupId = userFollowing.getGroupId();
        if (groupId == null){
            throw new ConditionException("参数错误！");
        }

        // 查询分组是否存在
        FollowingGroup group = followingGroupService.getById(groupId);
        if (group == null){
            throw new ConditionException("参数错误！");
        }

        // TODO 需要查看移动的分组是不是当前用户创建的，不允许移动到别人创建的分组中

        // 更新分组
        userFollowingDao.updateUserFollowing(userFollowing);
    }
}
