package com.tongbu.game.dao;

import com.tongbu.game.entity.blog.Contents;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jokin
 * @date 2019/1/29 14:11
 */
public interface ContentsDao extends ElasticsearchRepository<Contents,String> {
}
