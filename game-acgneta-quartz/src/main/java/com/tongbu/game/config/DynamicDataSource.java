package com.tongbu.game.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author jokin
 * @date 2018/9/29 13:57
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("数据源为{}",JdbcContextHolder.get());
        return JdbcContextHolder.get();
    }
}
