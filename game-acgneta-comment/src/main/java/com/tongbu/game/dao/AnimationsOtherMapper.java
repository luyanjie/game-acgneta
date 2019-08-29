package com.tongbu.game.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AnimationsOtherMapper {

    @Update("update gameAnimationsLove set associateId=#{associateId} where id=#{id}")
    int updateLove(@Param("associateId") String associateId,@Param("id") int id);

    @Update("update AnimationComments set associateId=#{associateId} where id=#{id}")
    int updateComment(@Param("associateId") String associateId,@Param("id") int id);

    @Update("update AnimationQuestion set associateId=#{associateId} where id=#{id}")
    int updateQuestion(@Param("associateId") String associateId,@Param("id") int id);
}
