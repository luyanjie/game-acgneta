package com.tongbu.game.service.task.act10110;

import com.tongbu.game.entity.task.act10110.GameNewEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vip.xinba.core.request.HttpClientUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jokin
 * @date 2019/1/15 15:45
 * <p>
 * ACG狗屋：http://www.acgdoge.net/
 */
@Slf4j
public class AcgDogeImpl extends AbstractService {
    // 模块Id
    private static final int MODULE_ID = 10;

    private static List<String> URL_LIST = new ArrayList<>();

    static {
//        URL_LIST.add("http://www.acgdoge.net/page/3");
//        URL_LIST.add("http://www.acgdoge.net/page/2");
        URL_LIST.add("http://www.acgdoge.net/");
    }

    @Override
    public void exec() {
        URL_LIST.forEach(webUrl -> {

            try {
                String responseContent = HttpClientUtils.get(webUrl);
                if (StringUtils.isNoneEmpty(responseContent)) {

                    Document document = Jsoup.parse(responseContent);
                    Elements allContetn = document.select("article[class^=post-]");

                    List<GameNewEntity> list = new ArrayList<>();
                    for (Element element : allContetn) {
                        try {
                            Element elementPostT = element.selectFirst("div[class=post_t]");
                            Element elementImg = elementPostT.selectFirst("img");
                            Element elementTitle = element.selectFirst("h2[class=post_h]");

                            // 详细页地址
                            String url = elementTitle.selectFirst("a").attr("href");
                            if (!StringUtils.isNoneEmpty(url)) {
                                continue;
                            }

                            int webUrlId = Integer.getInteger(url.substring(url.lastIndexOf("/") + 1));
                            // 判断是否已经抓取
                            if (exists(webUrlId, MODULE_ID)) {
                                break;
                            }
                            GameNewEntity gameNew = new GameNewEntity();
                            // 标题
                            String title = elementTitle.selectFirst("a").text();

                            // 图片地址
                            String img = elementImg.selectFirst("img").attr("data-lazy-src");

                            Element elementPostTime = element.selectFirst("div[class=post_time]");
                            String postDay = element.selectFirst("div[class=post_t_d]").text();
                            String postTime = element.selectFirst("div[class=post_t_u]").text();
                            if (!StringUtils.isNoneEmpty(postDay) || !StringUtils.isNoneEmpty(postTime)) {
                                continue;
                            }

                            // 发布时间
                            String pushTime = "2019-" + postDay.replace("/", "-") + " " + postTime;

                            // 简介
                            String about = "";
                            Elements elementAbout = elementPostT.select("p");
                            if (elementAbout.size() > 1) {
                                about = elementAbout.get(1).selectFirst("p").text();
                            }

                            gameNew.setWebUrl(url);
                            gameNew.setWebUrlId(webUrlId);
                            gameNew.setTitle(title);
                            gameNew.setImg(img);
                            gameNew.setImgWeb(img);
                            gameNew.setStatus(1);
                            gameNew.setPushTime(pushTime);
                            gameNew.setAuthor("");
                            gameNew.setAbout(about);
                            gameNew.setModuleId(MODULE_ID);
                            list.add(gameNew);

                        } catch (Exception e) {
                            log.error(webUrl, e);
                        }
                    }

                    newsDetail(list);
                    /*if (list.size() > 0) {
                        list.stream().sorted(Comparator.comparing(GameNewEntity::getWebUrlId)).forEach(x -> {
                            try {
                                int newId = insertNews(x);
                                getDetails(newId, x.getWebUrl());
                            } catch (Exception e) {
                                log.error(x.getWebUrl(), e);
                            }
                        });
                    }*/

                }
            } catch (Exception e) {
                log.error(webUrl, e);
            }
        });
    }

    @Override
    public void getDetails(int newId, String webUrl,int moduleId,int source) {
        if (newId > 0) {
            // 基本信息插入成功，获取详细信息
            String response = HttpClientUtils.get(webUrl);
            if (StringUtils.isNoneEmpty(response)) {
                Document doc = Jsoup.parse(response);
                String content = doc.selectFirst("div[class=post_t]").html();
                insertNewsContent(newId, content,moduleId,source);
            }
        }
    }
}
