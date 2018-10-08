package com.tongbu.game.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * @author jokin
 * @date 2018/9/29 13:57
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        // logger.info("数据源为{}",JdbcContextHolder.getDataSource());
        return JdbcContextHolder.getDataSource();
    }
}
