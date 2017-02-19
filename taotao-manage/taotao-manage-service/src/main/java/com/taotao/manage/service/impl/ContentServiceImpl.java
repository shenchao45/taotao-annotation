package com.taotao.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenchao on 2017/2/15.
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Override
    public EasyUIResult queryContentList(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Content> contents = ((ContentMapper) this.mapper).queryContentList(categoryId);
        PageInfo pageInfo = new PageInfo(contents);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
