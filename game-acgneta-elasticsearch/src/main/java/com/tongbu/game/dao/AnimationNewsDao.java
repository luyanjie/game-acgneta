package com.tongbu.game.dao;

import com.tongbu.game.entity.AnimationNews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AnimationNewsDao extends ElasticsearchRepository<AnimationNews,Integer> {
}
