package com.tongbu.game.service.task.act2037;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.common.TargetDataSource;
import com.tongbu.game.common.TaskHandlerType;
import com.tongbu.game.common.enums.DataSourceType;
import com.tongbu.game.dao.ShareDownloadUrlMapper;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.entity.task.act2037.ShareDownloadUrlEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jokin
 * @date 2018/11/19 17:33
 * <p>
 * 签名被封，自动上架，需要时启动，指定时间段间隔执行
 */
@Service
@TaskHandlerType("2037")
public class Act2037ServiceImpl extends AbstractHandler {

    @Autowired
    private ShareDownloadUrlMapper mapper;

    @Override
    @TargetDataSource(dataSourceType = DataSourceType.ITUNESDB)
    public void start(JobEntity job) {
        List<ShareDownloadUrlEntity> list = mapper.list("com.tongbu.tmsk.qmqj", "Shantou Golden Dragon Hotel Co.,Ltd.");
        System.out.println("list:" + JSON.toJSONString(list));
    }
}
