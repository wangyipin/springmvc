package com.apps.sys.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.apps.login.domain.User;
import com.framework.core.utils.Const;


/**
 *  请求地址拦截器,作用是对登陆地址进行筛选，如果是配置的符合要求的地址，则可以进入到对应的地址请求，如果不符合要求，则需要走登陆地址接口，才能进入系统
 *  
 * HandlerInterceptorAdapter 知识点，其有三个方法
 * 
 * preHandle在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
 * 
 * postHandle在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView
 * 
 * afterCompletion在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面），可以根据ex是否为null判断是否发生了异常，进行日志记录
 */

public class LoginInterceptor extends HandlerInterceptorAdapter
{
	
	private static List<String> PermitURI = new ArrayList<String>();
	static
	{
		PermitURI.add("LoginAction.do");
//		PermitURI.add("UserAction.do");
	}

	private static boolean ValidateURI(String URI)
	{
		boolean flag = false;
		for (String uri : PermitURI)
		{
			if (URI.indexOf(uri) != -1)
			{
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * preHandle在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		
		System.out.println(request.getRequestURI()+request.getQueryString());
		
		//如果请求地址符合要求，则走父类的预处理
		if (ValidateURI(request.getRequestURI() + "?" + request.getQueryString())) {
			return super.preHandle(request, response, handler);
		}
		User sessionUser = (User) request.getSession().getAttribute(Const.SESSION_USER);
		//如果session中存在已经登陆的用户名，则可以进入请求地址
		String suname = (String) request.getSession().getAttribute("sessionUserName");
		if (suname != null)
		{
			return super.preHandle(request, response, handler);
		}
		//如果session中存在已经登陆的用户实体类，则可以进入请求地址，否则回到首页。
		if (sessionUser == null)
		{
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return false;
		}

		return super.preHandle(request, response, handler);
	}
}
