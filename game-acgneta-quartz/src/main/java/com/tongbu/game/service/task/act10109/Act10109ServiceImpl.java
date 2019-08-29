package com.tongbu.game.service.task.act10109;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.dao.GameAnimationsLoveMapper;
import com.tongbu.game.dao.GameAnimationsPlayLinkMapper;
import com.tongbu.game.dao.TaskNumMapper;
import com.tongbu.game.dao.UmengDeviceTokenMapper;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.entity.task.act10109.GameAnimationsPlayLinkEntity;
import com.tongbu.game.entity.task.act10109.TaskNumEntity;
import com.tongbu.game.entity.task.act10109.UmengDeviceTokenEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vip.xinba.core.request.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jokin
 * @date 2018/12/27 10:29
 * 动画更新集数后通知 追番的用户
 */
@Service
@Slf4j
@TaskHandlerType("10109")
public class Act10109ServiceImpl extends AbstractHandler {

    @Autowired
    TaskNumMapper mapper;

    @Autowired
    GameAnimationsPlayLinkMapper playLinkMapper;

    @Autowired
    GameAnimationsLoveMapper loveMapper;

    @Autowired
    UmengDeviceTokenMapper deviceTokenMapper;

    @Value("${message.send.url}")
    String sendUrl;

    @Override
    public void start(JobEntity job) {
        log.info("Act10109ServiceImpl start");
        // 获取指定表的任务执行
        List<TaskNumEntity> list = mapper.list(1);
        list.forEach(x -> {
            switch (x.getName()) {
                case "gameAnimationsPlayLink": // 追番更新 消息发送
                    execGameAnimationsPlayLink(x.getId(), x.getMaxId());
                    break;
                case "UmengDeviceToken": // 更新deviceToken到总表
                    execUmengDeviceToken(x.getId(), x.getMaxId());
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 追番更新 消息发送
     */
    private void execGameAnimationsPlayLink(int id, int maxId) {
        Map<Integer, String> map = new HashMap<>();
        List<GameAnimationsPlayLinkEntity> list = playLinkMapper.list(maxId);
        if (list.size() > 0) {
            int dbMaxId = maxId;
            for (GameAnimationsPlayLinkEntity item : list) {
                if (item.getId() > dbMaxId) {
                    dbMaxId = item.getId();
                }
                map.putIfAbsent(item.getAnimationId(), item.getEpisode());
            }

            log.info("有更新的动画：" + JSON.toJSONString(map));
            if (dbMaxId > maxId) {
                mapper.update(id, dbMaxId);
            }

            // 判断有哪些账号订阅 追番该动画
            // 2019-03-15 因存在侵权问题 停止消息发送 与推送
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                String episode = entry.getValue();
                int animationId = entry.getKey();
                loveMapper.list(animationId).forEach(x -> {
                    int uid = x.getUid();
                    String animationName = x.getAnimationName();
                    log.info("订阅追番的人：" + JSON.toJSONString(x));
                    // 调用 消息发送接口。这种在人数少的时候可以单条发送，以后用户人数增加时需要改为批量发送
                    String url = null;
                    try {
                        url = sendUrl + String.format("/user?SendMsg&uid=%s&title=%s&message=%s&t=%s&token=tb.test&grade=3" +
                                        "&toUid=%s&commentId=0&typeSource=0&typeId=%s&msgType=1&pushModule=0", uid, "你在追的番有更新啦！",
                                URLEncoder.encode(StringUtils.join("《", animationName, "》 更新到", episode, "话"), "UTF-8"), System.currentTimeMillis() / 1000, uid, x.getAnimationId());
                        HttpClientUtils.get(url);
                    } catch (UnsupportedEncodingException e) {
                        log.error("execGameAnimationsPlayLink 编码异常", e);
                    }
                    log.info(url);
                });
            }
        }
    }

    /**
     * 更新deviceToken到总表
     */
    private void execUmengDeviceToken(int id, int maxId) {
        List<UmengDeviceTokenEntity> list = deviceTokenMapper.list(maxId);
        if (list == null || list.size() <= 0) {
            return;
        }
        mapper.update(id, list.get(list.size() - 1).getId());
        list.forEach(x -> deviceTokenMapper.insert(x.getDeviceToken(), x.getAppSource(), x.getInsertTime()));
    }
}
