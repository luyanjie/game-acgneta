package com.tongbu.game.controller;

import com.tongbu.game.dao.AnimationsDao;
import com.tongbu.game.entity.Animations;
import com.tongbu.game.entity.response.PageTotal;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author jokin
 * @date 2018/12/7 17:30
 * <p>
 * 参考：https://blog.csdn.net/tianyaleixiaowu/article/details/72833940
 */
@RestController
@RequestMapping("/animation")
public class AnimationsController {

    @Autowired
    AnimationsDao animationsDao;

    /**
     * @param id 内部id
     * @return com.tongbu.game.entity.Animations
     * @author jokin
     * @date 2018/12/7 17:37
     * 查询一个
     **/
    @RequestMapping("/select/{id}")
    public MessageResponse<Animations> select(@PathVariable String id) {
        Optional<Animations> optional = animationsDao.findById(id);
        return ResponseUtil.success(optional.orElseGet(Animations::new));
    }

    /**
     * 2、查  ++:全文检索（根据整个实体的所有属性，可能结果为0个）
     *
     * @param q 查询关键字
     * @return
     */
    @RequestMapping("/search/{q}")
    public MessageResponse<List<Animations>> search(@PathVariable String q) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
        Iterable<Animations> searchResult = animationsDao.search(builder);
        Iterator<Animations> iterator = searchResult.iterator();
        List<Animations> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return ResponseUtil.success(list);
    }

    /**
     * 3、查   +++：分页、分数、分域（结果一个也不少）
     *
     * @param page 页码(从1 开始)
     * @param size 每页的大小
     * @param q    关键字
     * @return
     */
    @RequestMapping("/t/{page}/{size}/{q}")
    public MessageResponse<List<Animations>> searchAll(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q) {

        // 分页参数
        Pageable pageable = PageRequest.of(page - 1, size);

        // 分数，并自动按分排序
        // 权重：name 1000分
        FunctionScoreQueryBuilder functionScoreQueryBuilder1
                = QueryBuilders.functionScoreQuery(QueryBuilders.matchQuery("name", q), ScoreFunctionBuilders.weightFactorFunction(1000));
        // 权重：message 100分
        FunctionScoreQueryBuilder functionScoreQueryBuilder2
                = QueryBuilders.functionScoreQuery(QueryBuilders.matchQuery("briefintro", q), ScoreFunctionBuilders.weightFactorFunction(100));

        // 排序
        SortBuilder sortBuilder = SortBuilders.fieldSort("episodetype").order(SortOrder.ASC);

        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder1)
                .withQuery(functionScoreQueryBuilder2)
                .withSort(sortBuilder)
                .build();
        Page<Animations> searchPageResults = animationsDao.search(searchQuery);
        System.out.println(searchPageResults.getTotalPages());
        return ResponseUtil.success(searchPageResults.getContent());
    }

    @RequestMapping("/{page}/{size}/{q}")
    public MessageResponse<PageTotal<Integer>> searchName(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q)
    {
        // 分页参数
        Pageable pageable = PageRequest.of(page - 1, size);

        // 分数，并自动按分排序
        // 权重：name 1000分
        FunctionScoreQueryBuilder functionScoreQueryBuilder1
                = QueryBuilders.functionScoreQuery(QueryBuilders.matchQuery("name", q), ScoreFunctionBuilders.weightFactorFunction(1000));

        // 多个字段中进行匹配查询
        // QueryBuilders.multiMatchQuery(q,"name","content");
        // 匹配查询，不进行分词
        // QueryBuilders.termQuery("name",q);
        // 匹配查询，不进行分词,但是对于中文，因为我们用的是ik分词器，会导致查询不出来的问题，需要将该字段类型临时转成keyword类型
        //  QueryBuilders.termQuery("name.keyword",q);
        // 使用terms匹配多个查询
        // QueryBuilders.termsQuery("name", "changge","旅游");
        // 返回查询
        // QueryBuilder builder = QueryBuilders.rangeQuery("birthday").from("1990-01-01").to("2000-10-10").format("yyyy-MM-dd");
        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder1)
                .build();

        Page<Animations> searchPageResults = animationsDao.search(searchQuery);

        PageTotal<Integer> pageTotal = new PageTotal<>();
        pageTotal.setTotalCount(searchPageResults.getTotalElements());
        pageTotal.setTotalPage(searchPageResults.getTotalPages());

        searchPageResults.getContent().forEach(x-> pageTotal.getList().add(x.getId()));
        return ResponseUtil.success(pageTotal);
    }

    @RequestMapping("/term/{page}/{size}/{type}")
    public MessageResponse term(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer type){
        // 分页参数
        Pageable pageable = PageRequest.of(page - 1, size);
        // 匹配查询，不进行分词,但是对于中文，因为我们用的是ik分词器，会导致查询不出来的问题，需要将该字段类型临时转成keword类型
        QueryBuilder queryBuilder = QueryBuilders.termQuery("episodetype",type);
        // QueryBuilder queryBuilder = QueryBuilders.termQuery("episodetype.keyword",type);

        // 排序
        SortBuilder sortBuilder = SortBuilders.fieldSort("inserttime").order(SortOrder.ASC);
        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .withSort(sortBuilder)
                .build();

        Page<Animations> searchPageResults = animationsDao.search(searchQuery);
        PageTotal<Animations> pageTotal = new PageTotal<>();
        pageTotal.setTotalCount(searchPageResults.getTotalElements());
        pageTotal.setTotalPage(searchPageResults.getTotalPages());

        pageTotal.setList(searchPageResults.getContent());
        return ResponseUtil.success(pageTotal);
    }

    /**
     * 4、增
     *
     * @param animations 动画
     * @return
     */
    @PostMapping("/insert")
    public MessageResponse<Animations> insert(Animations animations) {
        animationsDao.save(animations);
        return ResponseUtil.success(animations);
    }

    /**
     * 5、删 id
     *
     * @param id 动画id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public MessageResponse<Animations> delete(@PathVariable String id) {
        Animations animations = animationsDao.findById(id).get();
        animationsDao.deleteById(id);
        return ResponseUtil.success(animations);
    }

    /**
     * 6、改
     *
     * @param animations 动画
     * @return
     */
    @PutMapping("/update")
    public MessageResponse<Animations> update(Animations animations) {
        animationsDao.save(animations);
        return ResponseUtil.success(animations);
    }
}
