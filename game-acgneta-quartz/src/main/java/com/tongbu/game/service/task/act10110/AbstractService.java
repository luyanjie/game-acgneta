package com.tongbu.game.service.task.act10110;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.config.ApplicationContextProvider;
import com.tongbu.game.dao.AnimationNewMapper;
import com.tongbu.game.entity.task.act10110.GameNewEntity;
import com.tongbu.game.service.task.Act10110NewsResources;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

import static com.tongbu.game.common.TaskUtil.replaceHtmlTag;

/**
 * @author jokin
 * @date 2019/1/15 17:45
 */
@Slf4j
public abstract class AbstractService implements Act10110NewsResources {

    private static final AnimationNewMapper mapper = ApplicationContextProvider.getBeanClass(AnimationNewMapper.class);

    void newsDetail(List<GameNewEntity> list) {
        if (list.size() > 0) {
            list.stream().sorted(Comparator.comparing(GameNewEntity::getWebUrlId)).forEach(x -> {
                try {
                    if ((x.getModuleId() == 10 || x.getModuleId() == 8) && !StringUtils.isEmpty(x.getImg())) {
                        x.setImg(x.getImg().replace("http://", "https://"));
                    }
                    int newId = insertNews(x);
                    getDetails(newId, x.getWebUrl(), x.getModuleId(), 0);
                } catch (Exception e) {
                    log.error(JSON.toJSONString(x), e);
                }
            });
        }
    }

    /**
     * 判断页面是否已经抓取过
     *
     * @param webUrlId 抓取网页对应的id
     * @param moduleId 所属模块id，对应gameAnimationsResources_Category中的id
     * @return bool
     */
    boolean exists(int webUrlId, int moduleId) {
        int id = mapper.select(webUrlId, moduleId);
        return id > 0;
    }

    /**
     * 插入新闻基本信息
     *
     * @param obj 新闻基本信息
     * @return int 返回新闻自增id
     * @author jokin
     * @date 2019/1/16 9:34
     **/
    int insertNews(GameNewEntity obj) {
        if (mapper.insertNews(obj) > 0) {
            return obj.getId();
        }
        return 0;
    }

    /**
     * 更新状态
     *
     * @param status 状态 0：无效 1：有效 -1：未获得外站详细信息
     * @param id     新闻id
     * @return int
     */
    int updateStatus(int status, int id) {
        return mapper.updateStatus(status, id);
    }

    int updateAuthor(int id, String author) {
        return mapper.updateAuthor(id, author);
    }

    /**
     * 插入新闻详细信息
     *
     * @param newId,  新闻id
     * @param content 新闻内容
     * @author jokin
     * @date 2019/1/16 9:42
     **/
    void insertNewsContent(int newId, String content, int moduleId, int source) {
        if (!StringUtils.isNoneEmpty(content)) {
            // 未获得详细信息数据，变更新闻状态，以便再次获取
            JSONObject json = new JSONObject();
            json.put("message", "获取外站信息异常");
            json.put("newId", newId);
            log.error(JSON.toJSONString(json));
            updateStatus(-1, newId);
        } else {
            // 替换html标签
            content = replaceHtmlTag(content);

            if (moduleId == 10 || moduleId == 9) {
                content = content.replace("http://", "https://");
            }
            // 入库
            int count = mapper.insertNewsContent(newId, content);
            // 如果是补抓的，需要变更状态，动漫之家改为0，其他改为1
            if (source == 1 && count > 0) {
                updateStatus(moduleId == 9 ? 0 : 1, newId);
            }
        }
    }

    /**
     * 获取详细信息失败的新闻内容
     *
     * @return List
     */
    List<GameNewEntity> undoneList() {
        return mapper.undoneList();
    }
}
