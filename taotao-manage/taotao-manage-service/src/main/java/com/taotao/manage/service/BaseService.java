package com.taotao.manage.service;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
public interface  BaseService<T extends BasePojo> {
    public T queryById(Long id);

    public List<T> queryAll();

    public T queryOne(T record);

    public List<T> queryListByWhere(T record);

    public PageInfo<T> queryPageListByWhere(T record,Integer page,Integer rows);

    public Integer save(T t);
    public Integer saveSelective(T t);
    public Integer update(T t);
    public Integer updateSelective(T t);

    public Integer deleteById(Long id);

    public Integer deleteByIds(List<Object> ids,Class clazz,String property);

    public Integer deleteByWhere(T t);
}
