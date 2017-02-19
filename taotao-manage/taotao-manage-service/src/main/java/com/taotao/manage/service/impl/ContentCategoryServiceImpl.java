package com.taotao.manage.service.impl;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenchao on 2017/2/15.
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {
    @Override
    public void deleteAll(ContentCategory contentCategory) {
        List<Object> ids = new ArrayList();
        getChildIds(contentCategory,ids);
        ids.add(contentCategory.getId());
        deleteByIds(ids, ContentCategory.class, "id");
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> contentCategories = queryListByWhere(record);
        if (contentCategories == null || contentCategories.isEmpty()) {
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            updateSelective(parent);
        }
    }

    public void getChildIds(ContentCategory contentCategory,List<Object> ids){
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getId());
        List<ContentCategory> childs = this.queryListByWhere(record);
        for (ContentCategory child : childs) {
            ids.add(child.getId());
            if (child.getIsParent()) {
                getChildIds(child,ids);
            }
        }
    }
}
