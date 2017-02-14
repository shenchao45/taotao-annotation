package com.taotao.manage.service;

import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.Item;

/**
 * Created by shenchao on 2017/2/14.
 */
public interface ItemService extends BaseService<Item> {
    Boolean saveItem(Item item, String desc,String itemParams);

    EasyUIResult queryItemList(Integer page, Integer rows);

    boolean updateItem(Item item, String desc,String itemParams);
}
