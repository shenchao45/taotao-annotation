package com.taotao.manage.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemParamItemService;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @Override
    public Boolean saveItem(Item item, String desc,String itemParams) {
        try {
            item.setStatus(1);
            item.setId(null);
            this.save(item);
            ItemDesc itemDesc = new ItemDesc();
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            itemDescService.save(itemDesc);
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setParamData(itemParams);
            itemParamItem.setItemId(item.getId());
            itemParamItemService.save(itemParamItem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Item.class);
        example.setOrderByClause("created DESC");
        List<Item> items = this.mapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<>(items);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public boolean updateItem(Item item, String desc,String itemParams) {
        try {
            updateSelective(item);
            ItemDesc itemDesc = new ItemDesc();
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            itemDescService.update(itemDesc);
            ItemParamItem record = new ItemParamItem();
            record.setItemId(item.getId());
            ItemParamItem itemParamItem = itemParamItemService.queryOne(record);
            if (itemParamItem != null) {
                itemParamItem.setParamData(itemParams);
                itemParamItemService.updateSelective(itemParamItem);
            }else{
                record.setParamData(itemParams);
                itemParamItemService.save(record);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
