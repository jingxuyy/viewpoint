package com.xu.viewpoint.dao.v2;

import com.xu.viewpoint.dao.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowingGroupDao {

    /**
     * 根据type查询分组
     * @param type
     */
    FollowingGroup getByType(@Param("type") String type);

    /**
     * 根据id查询分组
     * @param id
     */
    FollowingGroup getById(@Param("id") Long id);

    /**
     * 根据userId查询用户创建的分组
     * @param userId
     */
    List<FollowingGroup> getByUserId(@Param("userId") Long userId);
}
