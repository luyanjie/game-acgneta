package com.tongbu.game.config;

import com.tongbu.game.common.enums.DataSourceType;

/**
 * @author jokin
 * @date 2018/9/29 13:58
 * <p>
 * 动态数据源的上下文
 */
public class JdbcContextHolder {
    private final static ThreadLocal<String> LOCAL = new ThreadLocal<>();

    /**
     * 设置当前数据源
     * */
    public static void set(String name) {
        LOCAL.set(name);
    }

    /**
     * 获取当前使用的数据源
     * */
    public static String get() {
        String dbName = LOCAL.get();
        return dbName != null ? dbName : DataSourceType.Master.getName();
    }

    /**
     * 清除当前数据源
     */
    public static void clear() {
        LOCAL.remove();
    }

}
