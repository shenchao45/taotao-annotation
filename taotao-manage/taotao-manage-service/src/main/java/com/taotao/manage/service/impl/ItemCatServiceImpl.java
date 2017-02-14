package com.taotao.manage.service.impl;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> queryItemCatListByParentId(long id) {
        ItemCat record = new ItemCat();
        record.setParentId(id);
        return itemCatMapper.select(record);
    }
}
