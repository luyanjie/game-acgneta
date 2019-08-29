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
 * @date 2019/1/15 15:43
 * <p>
 * 动漫星空：http://acg.gamersky.com/news/
 */
@Slf4j
public class GamerSkyImpl extends AbstractService {
    // 模块Id
    private static final int MODULE_ID = 8;

    private static List<String> URL_LIST = new ArrayList<>();

    static {
        URL_LIST.add("http://acg.gamersky.com/news/animation");
        URL_LIST.add("http://acg.gamersky.com/news/comic");
    }

    @Override
    public void exec() {

        URL_LIST.forEach(webUrl -> {
            try {

                String responseContent = HttpClientUtils.get(webUrl);
                if (StringUtils.isNoneEmpty(responseContent)) {

                    Document document = Jsoup.parse(responseContent);
                    Elements allContents = document.select("li[class=ptxt]");

                    List<GameNewEntity> list = new ArrayList<>();
                    for (Element element : allContents) {

                        try {
                            Element elementTitle = element.selectFirst("div[class=img]").selectFirst("a");
                            // 详细页地址
                            String url = elementTitle.attr("href");
                            if (!StringUtils.isNoneEmpty(url) || !url.contains(".shtml")) {
                                continue;
                            }

                            int webUrlId = Integer.getInteger(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".shtml")));
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


                            Element elementCon = element.selectFirst("div[class=con]");

                            // 发布时间
                            String pushTime = elementCon.selectFirst("div[class=time]").text();

                            // 简介
                            String about = elementCon.selectFirst("div[class=txt]").text();

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
                    /*
                    if (list.size() > 0) {
                        list.stream().sorted(Comparator.comparing(GameNewEntity::getWebUrlId)).forEach(x -> {
                            try {
                                //写入基本信息
                                int newId = insertNews(x);
                                getDetails(newId,x.getWebUrl());

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
    public void getDetails(int newId, String webUrl, int moduleId, int source) {
        if (newId > 0) {
            String response = HttpClientUtils.get(webUrl);
            if (StringUtils.isNoneEmpty(response)) {
                Document doc = Jsoup.parse(response);
                Element elementDetail = doc.selectFirst("div[class=detail]");

                if (source == 0) {
                    Elements elementAuthor = elementDetail.select("span[class=txt]");
                    //作者
                    String author = "";
                    if (elementAuthor.size() > 3) {
                        author = elementAuthor.get(2).text().replace("作者：", "");
                    }
                    if (!StringUtils.isEmpty(author)) {
                        // 更新作者
                        updateAuthor(newId, author);
                    }
                }


                //文章内容
                StringBuilder sb = new StringBuilder();
                String content = doc.selectFirst("div[class=MidL_con]").html();
                if (!StringUtils.isNoneEmpty(content)) {
                    log.warn(webUrl + "：未取到网页信息！");
                    // 未获得详细信息数据，变更新闻状态，以便再次获取
                    updateStatus(-1, newId);
                } else {
                    Element elementPage = doc.selectFirst("span[class=pagecss]");
                    String strPageInfo = elementPage.toString();
                    content = content.replace(strPageInfo, "");
                    sb.append(content);

                    //获取第二页、第三页的信息
                    for (int i = 2; i < 10; i++) {
                        String url = webUrl.replace(".shtml", "") + "_" + i + ".shtml";
                        content = getArtileContent(url);
                        if (!StringUtils.isNoneEmpty(content)) {
                            break;
                        }
                        sb.append(content);
                    }
                    insertNewsContent(newId, sb.toString(), moduleId, source);
                }
            }
        }
    }


    /**
     * 获取文章内容
     *
     * @param url
     * @return
     */
    private String getArtileContent(String url) {
        String content = "";
        try {
            String responseContent = HttpClientUtils.get(url);
            if (!StringUtils.isNoneEmpty(responseContent)) {
                return "";
            } else {

                if (responseContent.contains("error/404")) {
                    //页面不存在
                    return "";
                } else {
                    Document document = Jsoup.parse(responseContent);
                    content = document.selectFirst("div[class=MidL_con]").html();
                    Element elementPage = document.selectFirst("span[class=pagecss]");
                    String strPageInfo = elementPage.toString();
                    content = content.replace(strPageInfo, "");
                    return content;
                }
            }
        } catch (Exception e) {
            log.error(url, e);
        }
        return content;

    }
}
