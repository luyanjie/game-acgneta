package com.tongbu.game.service.task.act10107;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.config.ApplicationContextProvider;
import com.tongbu.game.dao.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import vip.xinba.core.request.HttpClientUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jokin
 * @date 2018/11/14 14:23
 */
@Slf4j
public class BilibiliImpl implements UrlService {

    private static final String PAGE_URL = "https://api.bilibili.com/x/web-interface/view";

    private static VideoMapper mapper = (VideoMapper) ApplicationContextProvider.getBean("videoMapper");

    @Override
    public void execute(int id, String url, int pageUrlId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("aid", String.valueOf(pageUrlId));
            String response = HttpClientUtils.get(PAGE_URL, paramMap);
            if (!StringUtils.isNoneEmpty(response)) {
                return;
            }
            JSONObject jsonObject = JSON.parseObject(response);
            if (jsonObject.getInteger("code") == 0) {
                JSONObject jsonData = jsonObject.getJSONObject("data");
                // tile
                String title = jsonData.getString("title");
                // 发布时间
                String pubdate = DateFormatUtils.format(jsonData.getLong("pubdate") * 1000, "yyyy-MM-dd HH:mm:ss");

                JSONObject jsonStat = jsonData.getJSONObject("stat");
                // 浏览次数 (播放次数)
                int view = jsonStat.getIntValue("view");
                // 弹幕数
                int danmaku = jsonStat.getIntValue("danmaku");
                // 收藏量
                int like = jsonStat.getIntValue("like");

                if (mapper == null) {
                    System.out.println("mapper is null");
                    return;
                }
                mapper.insert(id, url, title, view, danmaku, pubdate, DateFormatUtils.format(new Date(), "yyyy-MM-dd"), like);
            }
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("method", "BilibiliImpl");
            map.put("id", id);
            map.put("url", url);
            map.put("pageUrlId", pageUrlId);
            log.error(JSON.toJSONString(map), e);
        }
    }
}
