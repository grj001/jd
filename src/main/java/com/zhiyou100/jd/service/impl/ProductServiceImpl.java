package com.zhiyou100.jd.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou100.jd.dao.ProductDao;
import com.zhiyou100.jd.model.Result;
import com.zhiyou100.jd.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao pd;
	
	@Override
	public Result findAllProduct(String queryString,String catalog_name, String price, String sort) {
		return pd.findAllProduct(queryString,catalog_name, price, sort);
	}

}
