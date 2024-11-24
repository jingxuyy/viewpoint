package com.xu.viewpoint.dao.domain;

import java.util.List;

/**
 * @author: xuJing
 * @date: 2024/11/23 15:22
 */

public class PageResult<T> {

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 数据
     */
    private List<T> list;

    public PageResult(Integer total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
