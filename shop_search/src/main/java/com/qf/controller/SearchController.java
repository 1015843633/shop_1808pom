package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author CZF
 * @Date 2019/1/17
 * @Version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;
    /**
     * 根据关键字搜索
     * @param keyword
     * @return
     */
    @RequestMapping("/query")
    public String search(String keyword, Model model){
        System.out.println("调用查询"+keyword);
        List<Goods> goods = searchService.queryByIndexed(keyword);
        model.addAttribute("goodslist",goods);
        System.out.println(goods);
        return  "searchlist";
    }
}
