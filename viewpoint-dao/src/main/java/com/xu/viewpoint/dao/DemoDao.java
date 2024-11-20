package com.xu.viewpoint.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoDao {

    public Long query(Long id);
}
