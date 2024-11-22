package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.FollowingGroupDao;
import com.xu.viewpoint.dao.UserFollowingDao;
import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserFollowing;
import com.xu.viewpoint.dao.domain.constant.UserConstant;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.service.FollowingGroupService;
import com.xu.viewpoint.service.UserFollowingService;
import com.xu.viewpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
}
