package com.tongbu.game.service.task.act10111;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.dao.GameAnimationsMapper;
import com.tongbu.game.entity.task.act10111.GameAnimationsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xinba.core.request.HttpClientUtils;

import java.util.List;

@Service
@Slf4j
public class BgmServiceImpl implements ScoreService {

    @Autowired
    GameAnimationsMapper mapper;

    @Override
    public List<GameAnimationsEntity> gameAnimationsSmallList(int id) {
        return mapper.gameAnimationsSmallList(id);
    }

    @Override
    public double getBgmScore(int id, String webUrl) {
        try {
            String response = HttpClientUtils.get(webUrl);
            if (StringUtils.isNoneEmpty(response)) {
                Document doc = Jsoup.parse(response);
                Element elementDetail = doc.selectFirst("span[class=number]");
                String bgmScore = elementDetail.html();
                if (StringUtils.isEmpty(bgmScore)) {
                    return 0;
                }
                return Double.parseDouble(bgmScore);
            }
            return 0;
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("webUrl", webUrl);
            log.error(JSON.toJSONString(json), e);
        }
        return 0;
    }

    @Override
    public void updateScore(int id, double bgmScore) {
        int count = mapper.updateScore(id, bgmScore);
        if (count < 0) {
            log.warn("更新失败：id:" + id + " bgmScore:" + bgmScore);
        }
    }
}
