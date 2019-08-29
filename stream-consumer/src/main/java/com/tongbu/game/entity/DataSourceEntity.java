package com.tongbu.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jokin
 * @date 2018/9/29 14:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceEntity {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
