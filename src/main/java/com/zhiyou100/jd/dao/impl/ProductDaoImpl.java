package com.zhiyou100.jd.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.Highlighter.Highlight;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.stereotype.Repository;

import com.zhiyou100.jd.dao.ProductDao;
import com.zhiyou100.jd.model.ProductModel;
import com.zhiyou100.jd.model.Result;
import com.zhiyou100.jd.utils.InfoUtils;

@Repository
public class ProductDaoImpl implements ProductDao {

	List<ProductModel> li = null;
	private ProductModel pro = null;
	private Result result = null;

	@Override
	public Result findAllProduct(String queryString,String catalog_name, String price, String sort) {
		System.out.println("** 进入findAllProduct **");

		// 创建连接
		SolrServer server = new HttpSolrServer(InfoUtils.getBaseUrl());
		System.out.println("** server:\t" + server);
		// 条件
		ModifiableSolrParams params = new ModifiableSolrParams();
		SolrQuery query = new SolrQuery();
		
		
		// 添加条件
		if(!queryString.equals("")){
			//不是空字符串, 有搜索
			query.set("q", "product_name:"+queryString);
			
			//设置高亮
			query.setHighlight(true);
			query.addHighlightField("product_name");
			query.setHighlightSimplePre("<span style='color:red'>");
			query.setHighlightSimplePost("</span>");
			
		}else{
			//是空字符串, 没输值, 默认是""
			query.set("q", "*:*");
		}
		query.add("fq", "product_catalog_name" + ":" + catalog_name);
		if(price.equals("*")){
			query.add("fq", "product_price" + ":" + price);
		}else if(price.contains("50")){
			query.add("fq", "product_price" + ":" + "[50 TO *]");
		}else{
			query.add("fq", "product_price" + ":" + "["+ price.replace("-", " TO ")+"]");
		}
		if (sort.equals("1")) {
			query.addSort("product_price", ORDER.asc);
		}
		if (sort.equals("0")) {
			query.addSort("product_price", ORDER.desc);
		}
		
		
		params.add(query);

		System.out.println("** params:\t" + params);
		// 查询
		QueryResponse response = null;
		try {
			System.out.println("** 查找response");
			response = server.query(params);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// 结果
		SolrDocumentList results = response.getResults();
		//高亮结果集
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		System.out.println("** results:\t" + results);
		/*
		 * 
		 * "id": "6", "product_name":"zakka杂货 情侣小鹿树脂摆件家居装饰品一对",
		 * "product_catalog_name": "幽默杂货", "product_price": 15,
		 * "product_picture":"2014031517190225.jpg", "_version_":
		 * 1579861592041848800
		 */

		// 遍历
		result = new Result();
		li = new ArrayList<>();
		List<String> scopeList = new ArrayList<>();
		for (SolrDocument document : results) {
			System.out.println("商品id" + "\t" + "" + "" + document.get("id"));
			System.out.println("商品名称" + "\t" + "" + "" + document.get("product_name"));
			System.out.println("商品分类" + "\t" + "" + "" + document.get("product_catalog_name"));
			System.out.println("商品价格" + "\t" + "" + "" + document.get("product_price"));
			System.out.println("商品图片" + "\t" + "" + "" + document.get("product_picture"));
			System.out.println("**");

			pro = new ProductModel();
			pro.setPid(document.get("id").toString());
			pro.setName(document.get("product_name").toString());
			pro.setCatalog_name(document.get("product_catalog_name").toString());
			pro.setPrice(Float.valueOf(document.get("product_price").toString()));
			pro.setPicture(document.get("product_picture").toString());
			getMaxAndMin(document);
			
			//添加高亮结果集
			if(!queryString.equals("") && !queryString.equals("*")){
				Map<String, List<String>> map = highlighting.get(document.get("id"));
				List<String> list = map.get("product_name");
				pro.setName(list.get(0));
			}
			
			li.add(pro);
		}
		
		result.setProductList(li);
		result.setScopeList(getScopeList());

		return result;
	}

	
	Float minPrice = 0F;
	Float maxPrice = 0F;
	// 几位数
	int maxCount = 1;
	int minCount = 1;

	public void getMaxAndMin(SolrDocument document) {
		// 提取价格范围
		int i = 0;
		// 如果这一次的价格小于上一次的min价格, 将最小价格赋给minPrice
		// 如果这一次的价格大于上一次的max价格, 将最大价格赋给maxPrice,
		if (i == 0) {
			// 最小值需要初始化
			minPrice = Float.valueOf(document.get("product_price").toString());
		}
		if (Float.valueOf(document.get("product_price").toString()) < minPrice) {
			minPrice = Float.valueOf(document.get("product_price").toString());
		}
		if (Float.valueOf(document.get("product_price").toString()) > maxPrice) {
			maxPrice = Float.valueOf(document.get("product_price").toString());
		}
		System.out.println(minPrice + "--" + maxPrice);
		
	}
	public List<String> getScopeList() {
		List<String> scopeList = new ArrayList<>();
		// 进行分组
		if (maxPrice < 150F) {
			scopeList.add("0-9");
			scopeList.add("10-19");
			scopeList.add("20-29");
			scopeList.add("30-39");
			scopeList.add("40-49");
			scopeList.add("50以上");
		}
		return scopeList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
