package com.taotao.manage.service.impl;

import com.github.abel533.entity.Example;
import com.taotao.common.ItemCatData;
import com.taotao.common.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    @Override
    public ItemCatResult queryAllToTree() {
        ItemCatResult itemCatResult = new ItemCatResult();
        itemCatResult.setItemCats(getTreeFromParentId(0l));
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
