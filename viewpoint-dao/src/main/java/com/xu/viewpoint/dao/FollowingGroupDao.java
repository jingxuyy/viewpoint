package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 86136
 */
@Mapper
public interface FollowingGroupDao {

    /**
     * 根据type查询
     * @param type
     */
    FollowingGroup getByType(@Param("type") String type);


    /**
     * 根据id查询
     * @param id
     */
    FollowingGroup getById(@Param("id") Long id);
}
