package com.tongbu.game.service.task.act10111;

import com.tongbu.game.entity.task.act10111.GameAnimationsEntity;

import java.util.List;

public interface ScoreService {

    List<GameAnimationsEntity> gameAnimationsSmallList(int id);

    double getBgmScore(int id, String source);

    void updateScore(int id,double bgmScore);
}
