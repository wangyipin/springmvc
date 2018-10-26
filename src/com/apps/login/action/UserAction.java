package com.apps.login.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.apps.login.domain.ResponseBean;
import com.apps.login.domain.User;
import com.apps.login.service.Impl.UserServiceImpl;

@Controller
@RequestMapping("/UserAction.do")
public class UserAction {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@RequestMapping(params = "method=doRegister")
	public String doRegister(ModelMap map,User user){
		
		ResponseBean responseBean = userServiceImpl.register(user);
		
		map.addAttribute("responseBean", responseBean);
		if(responseBean.getRetCode().equals("00")) {
			return "main";
		}else {
			return "error";
		}
	}
	@RequestMapping(params = "method=doRegisterAjax")
	public void doRegisterAjax(ModelMap map,User user,HttpServletResponse response) {
		
		ResponseBean responseBean = userServiceImpl.register(user);
		try {
			response.setContentType("text/html;charset=utf-8");
			//将对象转换为json字符串，返回到页面
			response.getWriter().write(JSON.toJSONString(responseBean));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
