package com.tongbu.game.service.task.act10701;

import lombok.Getter;

@Getter
public enum RankType {

    /**
     * 季度
     */
    Quarter("gameAnimationRankByQuarter"),

    /**
     * 人气
     */
    Popular("gameAnimationRankByCollection"),

    /**
     * 热度
     */
    Hot("gameAnimationRankByHot"),

    /**
     * 欧美
     */
    Europe("gameAnimationRankByEurope"),

    /**
     * 国漫榜
     */
    China("gameAnimationRankByChina");

    private String table;

    RankType(String table){
        this.table = table;
    }
}
