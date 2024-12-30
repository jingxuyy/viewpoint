package com.xu.viewpoint.service.v2;

import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.domain.UserFollowing;

import java.util.List;

public interface IFollowingGroupService {

    /**
     * 根据type查询分组信息
     * @param type
     */
    FollowingGroup getByType(String type);

    /**
     * 根据id查询分组信息
     * @param id
     */
    FollowingGroup getById(Long id);

    /**
     * 根据userId查询，创建的分组  不仅需要把用户自己创建的分组查询出来，还要把系统默认的三个分组加进来
     * @param userId
     */
    List<FollowingGroup> getByUserId(Long userId);

    /**
     * 添加自定义分组
     *
     * @param followingGroup
     */
    void insertFollowingGroup(FollowingGroup followingGroup);

    /**
     * 获取用户自己创建的分组
     * @param userId
     */
    List<FollowingGroup> getUserFollowingGroup(Long userId);
}
