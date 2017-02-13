package com.shenchao.usermanager.bean;

import java.util.List;

/**
 * Created by shenchao on 2017/2/13.
 */
public class EasyUIResult<E> {
    private Long total;
    private List<E> rows;

    public EasyUIResult() {
    }

    public EasyUIResult(Long total, List<E> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }
}
