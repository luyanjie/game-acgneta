package com.tongbu.game.service.task.act10107;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tongbu.game.config.ApplicationContextProvider;
import com.tongbu.game.dao.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import vip.xinba.core.request.HttpClientUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jokin
 * @date 2018/11/14 14:23
 */
@Slf4j
public class AcfunImpl implements UrlService {

    private static final String URL_API = "http://www.acfun.cn/content_view.aspx?contentId=";
    private static VideoMapper mapper = (VideoMapper) ApplicationContextProvider.getBean("videoMapper") ;

    @Override
    public void execute(int id, String url, int pageUrlId) {

        try {
            String contentUrl = URL_API + pageUrlId;
            String response = HttpClientUtils.get(contentUrl);
            if (!StringUtils.isNoneEmpty(response)) {
                return;
            }

            JSONArray jArray = JSONArray.parseArray(response);
            //播放次数
            int view = jArray.getIntValue(0);
            //弹幕数
            int danmaku = jArray.getIntValue(2);
            //收藏数
            int like = jArray.getIntValue(5);

            String contentResponse = HttpClientUtils.get(url);
            if(!StringUtils.isNoneEmpty(contentResponse)){
                return;
            }

            //标题
            String pattern = "(?<=<h1 class=\"title J_videotitle\">)([\\s\\S]*?)(?=</h1>)";
            String title = "";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(contentResponse);
            if(m.find()){
                title = m.group(0);
            }

            //发布时间
            String pubdate = "";
            pattern = "(?<=<span class=\"time\">)([\\s\\S]*?)(?=</span>)";
            r = Pattern.compile(pattern);
            m = r.matcher(contentResponse);
            if(m.find()){
                pubdate = m.group(0);
            }

            if(mapper == null){
                System.out.println("mapper is null");
                return;
            }
            mapper.insert(id, url, title, view, danmaku, pubdate, DateFormatUtils.format(new Date(), "yyyy-MM-dd"), like);

        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("method", "AcfunImpl");
            map.put("id", id);
            map.put("url", url);
            map.put("pageUrlId", pageUrlId);
            log.error(JSON.toJSONString(map), e);
        }
    }
}
