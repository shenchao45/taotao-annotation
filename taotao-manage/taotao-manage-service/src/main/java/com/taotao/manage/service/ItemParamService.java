package com.taotao.manage.service;

import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;

/**
 * Created by shenchao on 2017/2/15.
 */
public interface ItemParamService extends BaseService<ItemParam> {
    EasyUIResult queryItemList(Integer page, Integer rows);
}
