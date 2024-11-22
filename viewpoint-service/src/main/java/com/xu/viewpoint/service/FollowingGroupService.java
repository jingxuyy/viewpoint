package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.FollowingGroup;

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
}
