package com.tongbu.game.controller.blog;

import com.tongbu.game.dao.ContentsDao;
import com.tongbu.game.entity.blog.Contents;
import com.tongbu.game.entity.response.PageTotal;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
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
 * @date 2019/1/29 14:12
 */
@RestController
@RequestMapping("/blog")
public class ContentsController {

    @Autowired
    ContentsDao contentsDao;

    /**
     * @param id 内部id
     * @return com.tongbu.game.entity.Animations
     * @author jokin
     * @date 2018/12/7 17:37
     * 查询一个
     **/
    @RequestMapping("/select/{id}")
    public MessageResponse<Contents> select(@PathVariable String id) {
        Optional<Contents> optional = contentsDao.findById(id);
        return ResponseUtil.success(optional.orElseGet(Contents::new));
    }

    /**
     * 2、查  ++:全文检索（根据整个实体的所有属性，可能结果为0个）
     *
     * @param q 查询关键字
     * @return
     */
    @RequestMapping("/search/{q}")
    public MessageResponse<List<Contents>> search(@PathVariable String q) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
        Iterable<Contents> searchResult = contentsDao.search(builder);
        Iterator<Contents> iterator = searchResult.iterator();
        List<Contents> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return ResponseUtil.success(list);
    }

    @RequestMapping("/{page}/{size}/{q}")
    public MessageResponse<PageTotal<Integer>> searchName(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q) {
        // 分页参数
        Pageable pageable = PageRequest.of(page - 1, size);

        // 分数，并自动按分排序
        // 权重：name 1000分
        FunctionScoreQueryBuilder functionScoreQueryBuilder1
                = QueryBuilders.functionScoreQuery(QueryBuilders.matchQuery("title", q), ScoreFunctionBuilders.weightFactorFunction(1000));
        // 有效状态status=1
        QueryBuilder queryBuilder = QueryBuilders.termQuery("status", true);

        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder1).withFilter(queryBuilder)
                .build();
        Page<Contents> searchPageResults = contentsDao.search(searchQuery);

        PageTotal<Integer> pageTotal = new PageTotal<>();
        pageTotal.setTotalCount(searchPageResults.getTotalElements());
        pageTotal.setTotalPage(searchPageResults.getTotalPages());

        searchPageResults.getContent().forEach(x -> pageTotal.getList().add(x.getId()));
        return ResponseUtil.success(pageTotal);
    }

    /**
     * 4、增
     *
     * @param animations 动画
     * @return
     */
    @PostMapping("/insert")
    public MessageResponse<Contents> insert(Contents animations) {
        contentsDao.save(animations);
        return ResponseUtil.success(animations);
    }

    /**
     * 5、删 id
     *
     * @param id 动画id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public MessageResponse<Contents> delete(@PathVariable String id) {
        Contents animations = contentsDao.findById(id).get();
        contentsDao.deleteById(id);
        return ResponseUtil.success(animations);
    }

    /**
     * 6、改
     *
     * @param animations 动画
     * @return
     */
    @PutMapping("/update")
    public MessageResponse<Contents> update(Contents animations) {
        contentsDao.save(animations);
        return ResponseUtil.success(animations);
    }
}
