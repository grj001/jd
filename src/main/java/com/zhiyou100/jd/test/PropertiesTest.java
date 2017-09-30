package com.zhiyou100.jd.test;


import org.junit.Test;

import com.zhiyou100.jd.utils.InfoUtils;

public class PropertiesTest {

	@Test
	public void test01() throws Exception {
		
		InfoUtils.getBaseUrl();
	}
	@Test
	public void test02() throws Exception {
		
		System.out.println(PropertiesTest.class.getClassLoader().getResource(""));
		System.out.println(System.getProperty("user.dir"));
	}
}
