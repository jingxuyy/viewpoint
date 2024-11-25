package com.xu.viewpoint.dao;

import com.xu.viewpoint.dao.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleDao {

    /**
     * 根据用户userId查询
     * @param userId
     */
    List<UserRole> getUserRoleByUserId(@Param("userId") Long userId);
}
