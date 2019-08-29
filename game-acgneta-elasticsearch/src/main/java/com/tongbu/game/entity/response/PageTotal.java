package com.tongbu.game.entity.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jokin
 * @date 2018/12/11 10:45
 */
@Data
public class PageTotal<T> implements Serializable {
    private static final long serialVersionUID = 4125693222071168930L;
    /**
     * 总条数
     * */
    private long totalCount;

    /**
     * 总页数
     * */
    private int totalPage;

    /**
     * 当页数据
     * */
    private List<T> list = new ArrayList<>();
}
