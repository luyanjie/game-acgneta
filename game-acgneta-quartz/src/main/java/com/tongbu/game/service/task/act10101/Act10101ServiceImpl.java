package com.tongbu.game.service.task.act10101;

import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.dao.GameAnimationsOneWeekBaseMapper;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xinba.core.request.HttpClientUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 更新每周动画的话数
 * 每天间隔10分钟更新一次 0 0/10 * * * ? *
 * <p>
 * 未从vs上迁移过来
 */
@Service
@TaskHandlerType("10101")
public class Act10101ServiceImpl extends AbstractHandler {

    @Autowired
    GameAnimationsOneWeekBaseMapper mapper;


    @Override
    public void start(JobEntity job) {
        int baseId = mapper.getMaxId();
        if (baseId <= 0) return;

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (curWeek < 0) {
            curWeek = 0;
        }
        if (curWeek == 0) //星期天，转为跟数据库一样
        {
            curWeek = 7;
        }
        System.out.println("curWeek=" + curWeek);
        String beginTime = "00:00";
        if (DateUtils.isSameDay(DateUtils.addMinutes(date, -10), date)) {
            beginTime = DateFormatUtils.format(DateUtils.addMinutes(date, -10), "HH:mm");
        }
        String endTime = DateFormatUtils.format(date, "HH:mm");
        if (mapper.updateNum(baseId, curWeek, beginTime, endTime) > 0) {
            String url = "http://all.api.acgneta.com/animes?WeekAnimeList";
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("week", curWeek);
            paramMap.put("cc", 1);
            HttpClientUtils.get(url, paramMap);
        }
    }
}
