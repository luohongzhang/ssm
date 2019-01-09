package com.jiangcheng.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiangcheng.bean.User;
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
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		boolean result = userService.login(user);
		if(result){
			return "/user/success";
		}else{
			return "/user/fail";
		}
	}

	/**
	 * 另一个写法 使用到了model
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("login.do")
	public String doLogin(User user, Model model) {
		if (userService.login(user)) {
			model.addAttribute("successMsg","登陆成功!");
			return "/success";
		} else {
			model.addAttribute("failMsg","用户不存在或密码错误!");
			return "/fail";
		}
	}
}
