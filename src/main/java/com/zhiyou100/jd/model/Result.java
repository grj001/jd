package com.zhiyou100.jd.model;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private List<ProductModel> productList = new ArrayList<>();
	List<String> scopeList = new ArrayList<>();
	

	public Result() {
		super();
	}

	@Override
	public String toString() {
		return "Result [productList=" + productList + ", scopeList=" + scopeList + "]\n";
	}
	
	
	public List<String> getScopeList() {
		return scopeList;
	}

	public void setScopeList(List<String> scopeList) {
		this.scopeList = scopeList;
	}
	public List<ProductModel> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductModel> productList) {
		this.productList = productList;
	}
	
}
