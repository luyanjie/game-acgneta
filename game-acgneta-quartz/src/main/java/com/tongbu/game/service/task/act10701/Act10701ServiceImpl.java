package com.tongbu.game.service.task.act10701;

import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import com.tongbu.game.service.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 季度榜、人气榜更新
 */
@Service
@Slf4j
@TaskHandlerType("10701")
public class Act10701ServiceImpl extends AbstractHandler {

    @Qualifier("runQuarterService")
    @Autowired
    TaskService runQuarter;

    @Qualifier("runCollectionService")
    @Autowired
    TaskService runCollection;

    @Override
    public void start(JobEntity job) {

        // 季度榜
        new Thread(()-> runQuarter.execute(),"RunQuarterService thread").start();
        // 人气排行榜
        new Thread(()-> runCollection.execute(),"RunCollectionService thread").start();
    }
}
