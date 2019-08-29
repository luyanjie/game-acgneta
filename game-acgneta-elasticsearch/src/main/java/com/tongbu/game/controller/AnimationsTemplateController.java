package com.tongbu.game.controller;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jokin
 * @date 2018/12/7 18:13
 *
 * 简单使用 elasticsearchTemplate
 */
@RestController
@RequestMapping("/template")
public class AnimationsTemplateController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 查询所有
     * @throws Exception
     */
    @GetMapping("/all")
    public List<Map<String, Object>> searchAll() throws Exception {
        //这一步是最关键的
        Client client = elasticsearchTemplate.getClient();
        // @Document(indexName = "product", type = "book")
        SearchRequestBuilder srb = client.prepareSearch("anima").setTypes("doc");
        // 查询所有
        SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        // SearchResponse sr = srb.setQuery(QueryBuilders.matchQuery("name","最终幻想")).execute().actionGet();
        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
            System.out.println(hit.getSourceAsString());
        }
        return list;
    }
}
