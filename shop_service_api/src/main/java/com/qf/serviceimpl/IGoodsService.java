package com.qf.serviceimpl;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {
    /**
     * 查询商品
     * @return
     */
    List<Goods> getGoodsList();

    /**
     * 添加商品
     * @param goods
     * @return
     */
    Goods insert(Goods goods);
}
