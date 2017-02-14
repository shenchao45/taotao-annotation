package com.taotao.manage.service;

import com.taotao.manage.pojo.ItemCat;

import java.util.List;

/**
 * Created by shenchao on 2017/2/14.
 */
public interface ItemCatService {
    List<ItemCat> queryItemCatListByParentId(long id);
}
