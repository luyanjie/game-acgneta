package com.tongbu.game.service;

import com.tongbu.game.dao.AnimationQuestionDao;
import com.tongbu.game.entity.AnimationQuestion;
import com.tongbu.game.entity.response.PageTotal;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimationQuestionServiceImpl implements IAnimationPageCountService {

    @Autowired
    AnimationQuestionDao questionDao;

    @Override
    public PageTotal<Integer> wildcard(Integer page, Integer size, String q) {

        // 分页参数
        Pageable pageable = PageRequest.of(page - 1, size);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("status",1))
                .must(QueryBuilders.boolQuery()
                        .should(QueryBuilders.wildcardQuery("title.keyword", "*" + q.toUpperCase() + "*"))
                        .should(QueryBuilders.wildcardQuery("content.keyword", "*" + q.toUpperCase() + "*"))
                );
        // 排序
        SortBuilder sortBuilder = SortBuilders.fieldSort("inserttime").order(SortOrder.DESC);
        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .withSort(sortBuilder)
                .build();
        Page<AnimationQuestion> searchPageResults = questionDao.search(searchQuery);
        PageTotal<Integer> pageTotal = new PageTotal<>();
        pageTotal.setTotalCount(searchPageResults.getTotalElements());
        pageTotal.setTotalPage(searchPageResults.getTotalPages());
        List<Integer> list = new ArrayList<>();
        searchPageResults.getContent().forEach(x->list.add(x.getId()));
        pageTotal.setList(list);
        return pageTotal;
    }
}
