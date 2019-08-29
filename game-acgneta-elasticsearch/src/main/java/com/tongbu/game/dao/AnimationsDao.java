package com.tongbu.game.dao;

import com.tongbu.game.entity.Animations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jokin
 * @date 2018/12/7 17:25
 */
public interface AnimationsDao extends ElasticsearchRepository<Animations,String> {
}
