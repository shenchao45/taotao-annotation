package com.taotao.manage.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenchao on 2017/2/15.
 */
@Service
public class ItemParamServiceImpl extends BaseServiceImpl<ItemParam> implements ItemParamService {
    @Override
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(ItemParam.class);
        example.setOrderByClause("created DESC");
        List<ItemParam> itemParams = this.mapper.selectByExample(example);
        PageInfo<ItemParam> pageInfo = new PageInfo<>(itemParams);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
