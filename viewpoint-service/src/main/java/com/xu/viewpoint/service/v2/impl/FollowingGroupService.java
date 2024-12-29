package com.xu.viewpoint.service.v2.impl;

import com.xu.viewpoint.dao.domain.FollowingGroup;
import com.xu.viewpoint.dao.v2.FollowingGroupDao;
import com.xu.viewpoint.service.v2.IFollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/12/29 16:01
 */
@Service
public class FollowingGroupService implements IFollowingGroupService {

    @Autowired
    private FollowingGroupDao followingGroupDao;

    /**
     * 根据type查询分组信息
     *
     * @param type
     */
    @Override
    public FollowingGroup getByType(String type) {
        return followingGroupDao.getByType(type);
    }


    /**
     * 根据id查询分组信息
     *
     * @param id
     */
    @Override
    public FollowingGroup getById(Long id) {
        return followingGroupDao.getById(id);
    }


    /**
     * 根据userId查询，创建的分组  不仅需要把用户自己创建的分组查询出来，还要把系统默认的三个分组加进来
     *
     * @param userId
     */
    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }
}
