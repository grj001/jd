package com.zhiyou100.jd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class InfoUtils {

	public static String getBaseUrl(){
		Properties pro = new Properties();
		InputStream in = InfoUtils.class.getClassLoader().getResourceAsStream("pro.properties");
		try {
			pro.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("** getBaseUrl\t"+pro.getProperty("BASEURL"));
		return pro.getProperty("BASEURL");
	}

	public static void setBaseUrl(){
		Properties pro = new Properties();
		pro.setProperty("BASEURL", "http://localhost:8080/solr");
		//System.out.println(System.getProperty("user.dir"));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(InfoUtils.class.getClassLoader().getResource("")+"pro.properties"));
			pro.store(fos, "BASEURL");
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
