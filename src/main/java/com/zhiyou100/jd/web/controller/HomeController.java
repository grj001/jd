package com.zhiyou100.jd.web.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhiyou100.jd.model.Result;
import com.zhiyou100.jd.service.ProductService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	ProductService ps;
	
	private Result r = null;
	
	@RequestMapping(value="/list.action")
	public String listGet(
			Model md,
			@RequestParam(value="catalog_name",required=false,defaultValue="*")String catalog_name,
			@RequestParam(value="price",required=false,defaultValue="*")String price,
			@RequestParam(value="sort",required=false,defaultValue="")String sort,
			@RequestParam(value="queryString",required=false,defaultValue="")String queryString
			){
		System.out.println("** 进入home--"+catalog_name+"--"+price+"--"+sort+"--"+queryString);
		
		
		r = ps.findAllProduct(queryString,catalog_name,price,sort);
		
		System.out.println(r.getScopeList());
		
		md.addAttribute("result", r);
		//放入md域中,input hiden会取
		md.addAttribute("catalog_name", catalog_name);
		md.addAttribute("price", price);
		md.addAttribute("sort", sort);
		md.addAttribute("queryString", queryString);
		
		return "product_list";
	}

}
