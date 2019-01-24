package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CZF
 * @Date 2019/1/17
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements ISearchService{
    //自动注入solrClient
    @Autowired
    private SolrClient solrClient;
    @Override
    public List<Goods> queryByIndexed(String keyword) {
        /**
         * 查询索引库
         */
        //创建一个查询实例
        SolrQuery solrQuery=new SolrQuery();
        if(keyword==null ){
            System.out.println("等于空");
            solrQuery.setQuery("*:*");
        }else if("".equals(keyword.trim())){
            System.out.println("等于空字符串");
            solrQuery.setQuery("*:*");
        }else {
            solrQuery.setQuery("gtitle:"+ keyword + "|| ginfo:" +keyword);
        }
        List<Goods> goodsList=new ArrayList<>();
        System.out.println("==============="+solrQuery);


        try {
            QueryResponse query=solrClient.query(solrQuery);
            System.out.println(query);
            SolrDocumentList results = query.getResults();
            System.out.println(results);
            for(SolrDocument document :results){
                String id= (String) document.get("id");
                String gtitle= (String) document.get("gtitle");
                String ginfo= (String) document.get("ginfo");
                Float gprice= (Float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage= (String) document.get("gimage");
                Goods goods=new Goods(
                        Integer.parseInt(id),
                        gtitle,
                        ginfo,
                        gcount,
                        0,
                        0,
                        gprice,
                        gimage
                );
                System.out.println(document);
                goodsList.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    /**
     * 将商品信息添加到搜索库中
     * @param goods
     * @return
     */
    @Override
    public int insertIndexed(Goods goods) {
        SolrInputDocument solrDocument=new SolrInputDocument();
        solrDocument.setField("id",goods.getId());
        solrDocument.setField("gtitle",goods.getTitle());
        solrDocument.setField("ginfo",goods.getGinfo());
        solrDocument.setField("gimage",goods.getGimage());
        solrDocument.setField("gcount",goods.getGcount());
        solrDocument.setField("gprice",goods.getPrice());
        try {
            solrClient.add(solrDocument);
            solrClient.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
