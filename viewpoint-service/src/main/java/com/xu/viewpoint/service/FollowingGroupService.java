package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.FollowingGroup;

import java.util.List;

/**
 * @author 86136
 */
public interface FollowingGroupService {

    /**
     * 根据分组类型查询关注分组
     * @param type
     */
    public FollowingGroup getByType(String type);

    /**
     * 根据分组id查询关注分组
     * @param id
     */
    public FollowingGroup getById(Long id);

    /**
     * 根据用户id查询所有分组
     * @param userId
     */
    List<FollowingGroup> getByUserId(Long userId);
}
