package com.wang.wangblog.service;

import com.wang.wangblog.model.BlogTagCount;
import com.wang.wangblog.utils.PageQueryUtil;
import com.wang.wangblog.utils.PageResult;

import java.util.List;

public interface TagService {

    /**
     * 查询标签的分页数据
     *
     * @param pageUtil
     * @return
     */
    PageResult getBlogTagPage(PageQueryUtil pageUtil);

    int getTotalTags();

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] ids);

    List<BlogTagCount> getBlogTagCountForIndex();
}
