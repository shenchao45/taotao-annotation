package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

import java.util.List;

/**
 * Created by shenchao on 2017/2/15.
 */
public interface ContentMapper extends Mapper<Content> {
    /**
     * 根据categoryId查询，update的倒序排序
     * @param categoryId
     * @return
     */
    public List<Content> queryContentList(Long categoryId);
}
