package com.tongbu.game.entity.task.act2037;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/11/19 17:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Act2307Properties implements Serializable {
    private static final long serialVersionUID = -8992860566085233526L;


    /**
     * 签名
     * */
    private String teamName;
    /**
     * 开始时间
     * */
    private String startTime;
    /**
     * 需要获取的状态 默认0 对应数据库 -10 ，其他状态对应数据库的active=-10 or active=9
     * */
    private int active;
}
