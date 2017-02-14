package com.taotao.manage.service.impl;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
public abstract class BaseServiceImpl<T extends BasePojo> implements BaseService<T>{
    /**
     * 泛型注入
     */
    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }
    @Override
    public List<T> queryAll(){
        return mapper.select(null);
    }
    @Override
    public T queryOne(T record) {
        return mapper.selectOne(record);
    }
    @Override
    public List<T> queryListByWhere(T record) {
        return mapper.select(record);
    }
    @Override
    public PageInfo<T> queryPageListByWhere(T record, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> result = mapper.select(record);
        return new PageInfo<T>(result);
    }
    @Override
    public Integer save(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insert(t);
    }
    @Override
    public Integer saveSelective(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insertSelective(t);
    }
    @Override
    public Integer update(T t) {
        t.setUpdated(new Date());
        return mapper.updateByPrimaryKey(t);
    }
    @Override
    public Integer updateSelective(T t) {
        t.setUpdated(new Date());
        t.setCreated(null);
        return mapper.updateByPrimaryKeySelective(t);
    }
    @Override
    public Integer deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }
    @Override
    public Integer deleteByIds(List<Object> ids,Class clazz,String property) {
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(property, ids);
        return mapper.deleteByExample(example);
    }
    @Override
    public Integer deleteByWhere(T t) {
        return mapper.delete(t);
    }
}
