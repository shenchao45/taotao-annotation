package com.taotao.manage.service;

import com.taotao.common.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;

/**
 * Created by shenchao on 2017/2/14.
 */
public interface ItemCatService extends BaseService<ItemCat>{
    ItemCatResult queryAllToTree();
}
