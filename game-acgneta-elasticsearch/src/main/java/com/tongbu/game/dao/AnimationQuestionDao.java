package com.tongbu.game.dao;

import com.tongbu.game.entity.AnimationQuestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AnimationQuestionDao extends ElasticsearchRepository<AnimationQuestion,Integer> {
}
