package com.tongbu.game.service.task.act10108;


import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vip.xinba.core.request.HttpClientUtils;

/**
 * @author jokin
 * @date 2018/11/27 15:46
 */
@Service
@Slf4j
@TaskHandlerType("10108")
public class Act10108ServiceImpl extends AbstractHandler {

    @Value("${message.send.url}")
    String sendUrl;

    @Override
    public void start(JobEntity job) {
        String url = StringUtils.join(sendUrl, "/web?ClickGet");
        String responseContent = HttpClientUtils.get(url);
        log.info(responseContent);
    }
}
