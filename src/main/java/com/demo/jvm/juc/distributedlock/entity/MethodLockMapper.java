package com.demo.jvm.juc.distributedlock.entity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MethodLockMapper {
    int countByExample(MethodLockExample example);

    int deleteByExample(MethodLockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MethodLock record);

    int insertSelective(MethodLock record);

    List<MethodLock> selectByExample(MethodLockExample example);

    MethodLock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MethodLock record, @Param("example") MethodLockExample example);

    int updateByExample(@Param("record") MethodLock record, @Param("example") MethodLockExample example);

    int updateByPrimaryKeySelective(MethodLock record);

    int updateByPrimaryKey(MethodLock record);
}