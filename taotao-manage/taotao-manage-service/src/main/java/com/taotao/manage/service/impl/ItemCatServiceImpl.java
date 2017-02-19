package com.taotao.manage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.taotao.common.ItemCatData;
import com.taotao.common.ItemCatResult;
import com.taotao.common.RedisService;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public ItemCatResult queryAllToTree() {
        String key = "TAOTAO_MANAGE_ITEM_CAT_API";
        String cacheData = this.redisService.get(key);
        if (StringUtils.isEmpty(cacheData)) {
            ItemCatResult itemCatResult = new ItemCatResult();
            itemCatResult.setItemCats(getTreeFromParentId(0l));
            String s = null;
            try {
                s = MAPPER.writeValueAsString(itemCatResult);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            this.redisService.set(key, s, 60 * 60 * 24 * 30 * 3);
            return itemCatResult;
        }
        ItemCatResult itemCatResult = null;
        try {
            itemCatResult = MAPPER.readValue(cacheData, ItemCatResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemCatResult;
    }

    public List<ItemCatData> getTreeFromParentId(Long parentId){
        List result = new ArrayList<>();
        Example example = new Example(ItemCat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", parentId);
        List<ItemCat> itemCats = this.mapper.selectByExample(example);
        if (itemCats != null) {
            for (ItemCat child : itemCats) {
                ItemCatData itemCatData = new ItemCatData();
                itemCatData.setUrl("/products/" + child.getId() + ".html");
                itemCatData.setNname("<a href='"+itemCatData.getUrl()+"'>"+child.getName()+"</a>");
                if (child.getIsParent()) {
                    itemCatData.setItems(getTreeFromParentId(child.getId()));
                    result.add(itemCatData);
                }else{
                    result.add(itemCatData.getUrl() + "|" + child.getName());
                }
            }
        }
        return result;
    }
}
