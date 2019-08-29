package com.tongbu.game.common;

import com.tongbu.game.common.enums.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jokin
 * @date 2018/11/19 17:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    DataSourceType dataSourceType() default DataSourceType.Master;
}
