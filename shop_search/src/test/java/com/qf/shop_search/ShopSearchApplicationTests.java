package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {
	//测试类也可以用yml

	//注入solrClient客户端
	@Autowired
	private SolrClient solrClient;

	/*
		添加索引库
	 */
	@Test
	public void insert() throws IOException, SolrServerException {
		//创建一个document对象，就是一个商品
		SolrInputDocument document=new SolrInputDocument();
		//设置字段
		document.setField("id","3");
		document.setField("gtitle","苹果手机");
		document.setField("ginfo","太贵了的手机");
		document.setField("gprice","9999.9");
		document.setField("gimage","http://www.baidu.com");
		document.setField("gcount","500");
		//表示讲商品放入索引库
		solrClient.add(document);
		//需要进行提交
		solrClient.commit();
	}

	//删除
	@Test
	public void delete() throws IOException, SolrServerException {
		solrClient.deleteById("1");
		solrClient.deleteByQuery("*:*");//删除所有，有的时候会把其他带有字段的数据删除,一般不使用
		solrClient.commit();//要记得进行提交事物
	}

	//查询
	@Test
	public void query() {
		SolrQuery solrQuery=new SolrQuery();
		solrQuery.setQuery("*:*");
		try {
			//获取查询
			QueryResponse query = solrClient.query(solrQuery);
			//获取查询结果
			SolrDocumentList results = query.getResults();
			for(SolrDocument document:results){
				String id= (String) document.get("id");
				String gtitle= (String) document.get("gtitle");
				Float gprice= (Float) document.get("gprice");
				int gcount = (int) document.get("gcount");
				String gimage= (String) document.get("gimage");
				System.out.println(id+" "+gtitle+" "+gprice+" "+gcount+" "+gimage);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

