package com.tongbu.game.service.task.act10110;

import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.common.enums.EnumAct10110Resources;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author jokin
 * @date 2019/1/15 14:21
 *
 * 其他网站咨询文章抓取
 *
 *
 */
@Service
@Slf4j
@TaskHandlerType("10110")
public class Act10110ServiceImpl extends AbstractHandler {
    @Override
    public void start(JobEntity job) {
        log.info("Act10110ServiceImpl start");
        log.info("重新更新一次 没有获取到详细信息的新闻");
        EnumAct10110Resources.SUPPLEMENT.getAct10110NewsResources().exec();
        log.info("开始抓取最新的新闻信息.........");
        // 动漫星空
        EnumAct10110Resources.GAMESKY.getAct10110NewsResources().exec();
        // 动漫之家
        EnumAct10110Resources.GAMEHOME.getAct10110NewsResources().exec();
        // ACG狗屋
        EnumAct10110Resources.ACGDOGE.getAct10110NewsResources().exec();
    }
}
