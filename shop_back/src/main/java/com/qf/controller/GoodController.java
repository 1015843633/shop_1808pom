package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.serviceimpl.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author CZF
 * @Date 2019/1/16
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodController {
    @Reference
    private IGoodsService goodsService;

    //将fdfs里面的serverpath的属性值注入
    @Value("${fdfs.serverpath}")
    private String fdfsPath;

    //自动注入一个文件存储客户端
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    //查询所有商品信息
    @RequestMapping("/list")
    public String getList(Model model){
        //调用服务层获取商品信息
        List<Goods> goodsList=goodsService.getGoodsList();
        model.addAttribute("goodsList",goodsList);
        System.out.println(goodsList);
        model.addAttribute("fdfsPath",fdfsPath);
        return  "goodslist";
    }

    //商品添加
    @RequestMapping("/insert")
    public String insertGoods(Goods goods){
        //调用服务添加商品
        System.out.println(goods);
        goodsService.insert(goods);
        return "redirect:/goods/list";
    }

    //上传图片
    @RequestMapping("/uploadimg")
    @ResponseBody
    public String uploadImg(MultipartFile file) throws Exception {
        System.out.println("上传的文件大小:"+file.getSize());
        //将图片上传到fastDFS上
        //方法中需要放一个输入流,文件的大小，文件名,
        StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "png", null);
        System.out.println("上传到fastdfs中的文件路径:"+result.getFullPath());
        //必须要返回一个JSON数据不经过视图解析器,直接返回
        return  "{\"imgpath\":\""+result.getFullPath()+"\"}";
    }
}
