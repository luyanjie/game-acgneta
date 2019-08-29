package com.tongbu.game.common.enums;

/**
 * @author jokin
 * @date 2018/9/29 14:00
 */
public enum  DataSourceType {
    /**
     * 默认库
     * */
    Master("master"),
    /**
     * 第二个数据库
     * */
    Slave("slave");

    private String name;

    DataSourceType(String name){this.name = name;}

    public String getName() {
        return this.name;
    }
}
