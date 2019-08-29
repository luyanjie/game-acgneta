package com.tongbu.game.service.task.act10701;

import com.tongbu.game.dao.GameAnimationsMapper;
import com.tongbu.game.entity.task.act10701.RunEntity;
import com.tongbu.game.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 人气排行榜
 * 规则改为：  根据评论数，从高到低排行；如果评论数一致，根据最新评论时间，新的在前；统计时间范围：当前日期的一年内
 * 规则变更（20185-08-07）：开播时间在一年之内，评分数量从高到低排序
 */
@Service
public class RunCollectionService implements TaskService {

    @Autowired
    GameAnimationsMapper mapper;
    public void execute()
    {
        List<RunEntity> list = mapper.runCollection();
        AtomicInteger orderBy = new AtomicInteger();
        list.forEach(runEntity -> {
            orderBy.getAndIncrement();
            mapper.insertRunCollection(runEntity.getId(),runEntity.getCount(), orderBy.get());
        });
        mapper.deleteRunCollection();
        mapper.updateRunCollection();
    }
}
