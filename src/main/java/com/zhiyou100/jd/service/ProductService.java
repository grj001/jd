package com.zhiyou100.jd.service;


import com.zhiyou100.jd.model.Result;

public interface ProductService {

	Result findAllProduct(String queryString,String catalog_name, String price, String sort);

}
