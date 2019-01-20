package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {
    /**
     * 根据关键字查询索引库并返回商品列表
     * @param keyword
     * @return
     */
    List<Goods> queryByIndexed(String keyword);

    /**
     * 添加数据库索引
     */
    int insertIndexed(Goods goods);
}
