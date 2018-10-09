package com.tongbu.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/10/9 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse<T> implements Serializable {

    private static final long serialVersionUID = 7505997295595095971L;

    private String code;
    private String msg;
    private T data;

}
