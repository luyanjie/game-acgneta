package com.tongbu.game.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总条数
     */
    private long totalElements;

    /**
     * 分页数据
     */
    private List<T> content;
}
