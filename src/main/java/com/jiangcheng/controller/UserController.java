package com.jiangcheng.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiangcheng.bean.UserBean;
import com.jiangcheng.service.UserService;

/**
 * 
 * @author Chan Kiang
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserBean user = new UserBean();
		user.setUsername(username);
		user.setPassword(password);
		boolean result = userService.login(user);
		if(result){
			return "/user/success";
		}else{
			return "/user/fail";
		}
	}
}
