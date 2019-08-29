package com.tongbu.game.service.task.act10106;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.dao.GameAnimationsPlayLinkMapper;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.entity.task.act10106.AnimationsUpdatePlanEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 动漫网页数据抓取
 */
@Service
@TaskHandlerType(value = "10106")
@Slf4j
public class Act10106ServiceImpl extends AbstractHandler {

    private static final HashMap<String, IWebRequest> actions = new HashMap<>();

    static {
        actions.put("bilibili", new BilibiliService());
        actions.put("dilidili",new DilidiliService());
        actions.put("halihali",new HalihaliService());
        actions.put("iqiyi",new IqiyiService());
        actions.put("qq",new QQService());
        actions.put("acfun", new AcfunService());
        actions.put("fjisu",new FjisuService());
    }

    @Autowired
    GameAnimationsPlayLinkMapper mapper;

    @Override
    public void start(JobEntity job) {

        String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        // 1、重跑已过更新日期(当前时间)未更新的
        getDate(mapper.updatePlanWeek(today), "Act10106 重跑已过更新日期(当前时间)未更新的");
        // 2、当天要更新
        getDate(mapper.updatePlanToday(today), "Act10106 当天要更新");
    }

    private void getDate(List<AnimationsUpdatePlanEntity> list, String method) {
        list.forEach(x -> {
            try {
                IWebRequest request = actions.getOrDefault(x.getPlatformCode(), (obj)
                        -> log.warn("PlatformCode is not found " + JSON.toJSONString(obj)));
                request.start(x);
            } catch (Exception e) {
                log.error(StringUtils.join("请求网页数据异常", JSON.toJSONString(x)), e);
            }
        });
    }
}
