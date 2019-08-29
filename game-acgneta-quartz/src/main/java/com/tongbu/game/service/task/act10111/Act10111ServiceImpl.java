package com.tongbu.game.service.task.act10111;

import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.entity.task.act10111.GameAnimationsEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定期 更新bgm分数
 */
@Service
@Slf4j
@TaskHandlerType("10111")
public class Act10111ServiceImpl extends AbstractHandler {

    @Autowired
    ScoreService scoreService;

    @Override
    public void start(JobEntity job) {
        log.info("Act10111ServiceImpl start");
        int id = 0;
        while (true) {
            List<GameAnimationsEntity> list = scoreService.gameAnimationsSmallList(id);
            list.forEach(x -> {
                if (!StringUtils.isEmpty(x.getSource()) && (x.getSource().contains("bgm.tv") || x.getSource().contains("bangumi.tv"))) {
                    double bgmScore = scoreService.getBgmScore(x.getId(), x.getSource());
                    if (bgmScore > 0) {
                        scoreService.updateScore(x.getId(), bgmScore);
                    }
                }
            });
            if (list.size() < 100) {
                break;
            }
            id = list.get(list.size() - 1).getId();
        }
    }
}
