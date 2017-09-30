package com.zhiyou100.jd.dao;


import com.zhiyou100.jd.model.Result;

public interface ProductDao {

	Result findAllProduct(String queryString,String catalog_name, String price, String sort);

}
