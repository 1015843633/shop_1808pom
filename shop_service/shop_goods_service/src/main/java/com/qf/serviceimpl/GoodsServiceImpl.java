package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Goods;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author CZF
 * @Date 2019/1/16
 * @Version 1.0
 */
@Service
public class GoodsServiceImpl implements IGoodsService{

    @Reference
    private ISearchService searchService;


    @Autowired
    private IGoodsDao goodsDao;


    //注入rabbitmq模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Goods> getGoodsList() {
        return goodsDao.selectList(null);
    }

    @Override
    @Transactional
    public Goods insert(Goods goods) {

        goodsDao.insert(goods);

        //将商品存进索引库
        searchService.insertIndexed(goods);

        //通知详情工程生成静态页，提供方
        //将goods对象发送到队列中
        rabbitTemplate.convertAndSend("goods_exchange","",goods);

        return goods;
    }
}
