package com.tongbu.game.service.task.act10107;

import com.tongbu.game.common.enums.UrlEnum;
import com.tongbu.game.dao.VideoMapper;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.entity.task.Act10107UrlEntity;
import com.tongbu.game.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jokin
 * @date 2018/11/14 14:03
 */
@Service
public class Act10107ServiceImpl implements TaskService {

    @Autowired
    VideoMapper videoMapper;

    @Override
    public void start(JobEntity job) {
        List<Act10107UrlEntity> list = videoMapper.videoUrlList();

        list.stream().filter(x -> x.getVedioSource().equals("B站")).forEach(x -> {
            UrlEnum.BILIBILI.getUrlService().execute(x.getId(), x.getUrl(), x.getPageUrlId());
        });

        list.stream().filter(x -> x.getVedioSource().equals("A站")).forEach(x -> {
            UrlEnum.ACFUN.getUrlService().execute(x.getId(), x.getUrl(), x.getPageUrlId());
        });
    }
}
