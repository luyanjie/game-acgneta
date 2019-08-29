package com.tongbu.game.dao.dynamics;

import com.tongbu.game.entity.dynamics.DynamicsShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserDynamicsShowDao extends MongoRepository<DynamicsShow,String> {

    /**
     * 根据用户id获取动态数据
     * @param uid 用户id
     * @return List
     */
    List<DynamicsShow> findByUid(int uid);

    @Query(value = "{'uid':?0}")
    Page<DynamicsShow> findByUid(int uid, Pageable pageable);

    /**
     * 根据用户id，typeId，commentId删除
     * @param uid 用户id
     * @param typeId typeId
     * @param commentId 评论id 如果有
     */
    void deleteByUidAndTypeIdAndCommentId(int uid,int typeId,int commentId);
}
