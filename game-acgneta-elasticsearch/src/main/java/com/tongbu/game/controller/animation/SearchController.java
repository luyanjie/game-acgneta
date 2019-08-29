package com.tongbu.game.controller.animation;

import com.tongbu.game.common.enums.AnimationSearchEnum;
import com.tongbu.game.entity.response.AnimationCount;
import com.tongbu.game.entity.response.PageTotal;
import com.tongbu.game.service.IAnimationPageCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

import java.util.ArrayList;

/**
 * 动画新闻搜索
 */
@RestController
@RequestMapping("/animation")
public class SearchController {

    @Qualifier("animationNewsServiceImpl")
    @Autowired
    IAnimationPageCountService newService;


    @Qualifier("animationQuestionServiceImpl")
    @Autowired
    IAnimationPageCountService questionService;

    @RequestMapping(value = "/wildcard/count", method = RequestMethod.GET)
    public MessageResponse totalCount(String q) {
        return ResponseUtil.success(count(1, 10, q, AnimationSearchEnum.EMPTY));
    }

    /**
     * 捏报新闻根据关键字通配符查询，不进行分词
     *
     * @param q 关键字
     * @return 返回条数
     */
    @RequestMapping(value = "/news/wildcard/count", method = RequestMethod.GET)
    public MessageResponse newTotalCount(String q) {
        return ResponseUtil.success(count(1, 10, q, AnimationSearchEnum.NEWS));
    }

    /**
     * 捏报新闻根据关键字通配符查询，不进行分词
     *
     * @param q    关键字
     * @param page 页码
     * @param size 每页显示条数
     * @return 返回内容
     */
    @RequestMapping(value = "/news/wildcard/{page}/{size}", method = RequestMethod.GET)
    public MessageResponse newWildcard(@PathVariable Integer page, @PathVariable Integer size,String q) {
        return ResponseUtil.success(count(page, size, q, AnimationSearchEnum.NEWS));
    }

    /**
     * 问答根据关键字通配符查询，不进行分词
     *
     * @param q 关键字
     * @return 返回条数
     */
    @RequestMapping(value = "/question/wildcard/count", method = RequestMethod.GET)
    public MessageResponse questionTotalCount(String q) {
        return ResponseUtil.success(count(1, 10, q, AnimationSearchEnum.QUESTION));
    }

    /**
     * 问答根据关键字通配符查询，不进行分词
     *
     * @param q    关键字
     * @param page 页码
     * @param size 每页显示条数
     * @return 返回内容
     */
    @RequestMapping(value = "/question/wildcard/{page}/{size}", method = RequestMethod.GET)
    public MessageResponse questionWildcard(@PathVariable Integer page, @PathVariable Integer size, String q) {
        return ResponseUtil.success(count(page, size, q, AnimationSearchEnum.QUESTION));
    }

    private AnimationCount count(int page, int size, String q, AnimationSearchEnum type) {
        if (page < 1) page = 1;
        PageTotal<Integer> newPageTotal = newService.wildcard(type == AnimationSearchEnum.NEWS ? page : 1, size, q);
        PageTotal<Integer> questionPageTotal = questionService.wildcard(type == AnimationSearchEnum.QUESTION ? page : 1, size, q);

        return new AnimationCount<>(newPageTotal.getTotalCount()
                , newPageTotal.getTotalPage(), questionPageTotal.getTotalCount(), questionPageTotal.getTotalPage()
                , type.equals(AnimationSearchEnum.NEWS) ? newPageTotal.getList()
                : type.equals(AnimationSearchEnum.QUESTION)
                ? questionPageTotal.getList()
                : new ArrayList<>());
    }
}
