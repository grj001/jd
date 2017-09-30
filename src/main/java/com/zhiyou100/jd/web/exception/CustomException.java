package com.zhiyou100.jd.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomException implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object obj,
			Exception exception) {

		/**
		 * /* 当三层某一层发生了异常,就会被全局异常处理 
		 * 三个参数分别为 请求 响应 发生异常的对象 异常对象
		 * 返回值类型是ModelAndView,
		 * 我们可以进行异常界面的跳转
		 **/
		System.out.println("全局异常拦截"+""+""+""+req+"---"+res+"---"+obj+"----"+exception);
		ModelAndView mav = new ModelAndView();
		mav.addObject("obj",obj);
		mav.addObject("exception", exception);
		mav.setViewName("error");
		
		return mav;
	}

}
