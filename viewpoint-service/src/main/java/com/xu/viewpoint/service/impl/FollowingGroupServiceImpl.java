package com.xu.viewpoint.service.impl;

import com.xu.viewpoint.dao.FollowingGroupDao;
import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.service.FollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/22 10:05
 */
@Service
public class FollowingGroupServiceImpl implements FollowingGroupService {

    @Autowired
    public FollowingGroupDao followingGroupDao;


    /**
     * 根据分组类型查询关注分组
     * @param type
     */
    @Override
    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }

    /**
     * 根据分组id查询关注分组
     * @param id
     */
    @Override
    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }


    /**
     * 根据用户id查询所有分组
     *
     * @param userId
     */
    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        List<FollowingGroup> groupList = followingGroupDao.getByUserId(userId);
        return null;
    }
}
