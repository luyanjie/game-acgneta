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
 * @date 2019/1/15 15:44
 * <p>
 * 动漫之家：https://news.dmzj.com/
 */

@Slf4j
public class GameHomeImpl extends AbstractService {

    // 模块Id
    private static final int MODULE_ID = 9;

    private static List<String> URL_LIST = new ArrayList<>();

    static {
        URL_LIST.add("https://news.dmzj.com/donghuaqingbao");
        URL_LIST.add("https://news.dmzj.com/manhuaqingbao");
        URL_LIST.add("https://news.dmzj.com/qingxiaoshuoqingbao");
        URL_LIST.add("https://news.dmzj.com/manhuazhoubian");
        URL_LIST.add("https://news.dmzj.com/shengyouqingbao");
    }

    @Override
    public void exec() {

        URL_LIST.forEach(webUrl -> {
            try {
                String responseContent = HttpClientUtils.get(webUrl);
                if (StringUtils.isNoneEmpty(responseContent)) {

                    Document document = Jsoup.parse(responseContent);
                    Elements allContent = document.select("div[class=briefnews_con_li]");

                    List<GameNewEntity> list = new ArrayList<>();

                    for (Element element : allContent) {
                        try {
                            Element elementTitle = element.selectFirst("div[class=li_content_img]").selectFirst("a");
                            // 详细页地址
                            String url = elementTitle.attr("href");
                            if (!StringUtils.isNoneEmpty(url) || !url.contains(".html")) {
                                continue;
                            }
                            int webUrlId = Integer.getInteger(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".html")));
                            // 判断是否已经抓取
                            if (exists(webUrlId, MODULE_ID)) {
                                break;
                            }
                            GameNewEntity gameNew = new GameNewEntity();

                            // 标题
                            String title = elementTitle.attr("title");
                            Element elementImg = elementTitle.selectFirst("img");
                            // 图片地址
                            String img = elementImg.attr("src");
                            Element li_img_de = element.selectFirst("div[class=li_img_de]");
                            Elements li_img_de_s = li_img_de.selectFirst("p[class=head_con_p_o]").select("span");
                            // 发布时间
                            String pushTime = li_img_de_s.get(0).text();
                            // 来源
                            // String source = li_img_de_s.get(1).text();
                            // 发布者
                            String author = li_img_de_s.get(2).text().replace("发布：", "");
                            // 简介
                            String about = li_img_de.selectFirst("p[class=com_about]").text();
                            gameNew.setWebUrl(url);
                            gameNew.setWebUrlId(webUrlId);
                            gameNew.setTitle(title);
                            // 动漫之家 存在防盗链，需要重新下载图片链接到本地
                            gameNew.setImg("");
                            gameNew.setImgWeb(img);
                            gameNew.setStatus(0);
                            gameNew.setPushTime(pushTime);
                            gameNew.setAuthor(author);
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
                String content = doc.selectFirst("div[class=news_content_con]").html();
                insertNewsContent(newId, content,moduleId,source);
            }
        }
    }
}
