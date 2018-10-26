package com.apps.login.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apps.login.domain.User;
import com.apps.login.service.Impl.LoginServiceImpl;

@Controller
@RequestMapping("/LoginAction.do")
public class LoginAction {

	
	private Logger logger = null;
    private String className = null;
	public LoginAction() {
		className = getClass().getName();
		logger =  LoggerFactory.getLogger(className);
	}
	
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	
	
	@RequestMapping(params = "method=doLogin")
	public String doLogin(ModelMap model,User user,HttpServletRequest request) {
		
		System.out.println(user.getUsername()+"----------------------"+user.getPwd());

		logger.info(user.getUsername()+"----------------------"+user.getPwd());
		
		boolean flag = loginServiceImpl.login(user);
		if(flag) {
			
			request.getSession().setAttribute("sessionUserName", user.getUsername());
			request.getSession().setAttribute("session_user", user);
			
			//字符串
			model.addAttribute("mainFlag", "这是主页标签MainFlag");
			//对象
			model.addAttribute("user", user);
			
			return "main";
		}
		model.addAttribute("errorFlag", "这是错误标签ErrorFlag");
		return "error";
	}
	
}
