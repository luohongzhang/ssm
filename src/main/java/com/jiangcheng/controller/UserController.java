package com.jiangcheng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiangcheng.bean.User;
import com.jiangcheng.service.UserService;

import java.io.IOException;

/**
 * 
 * @author Chan Kiang
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request){

	    logger.info("==========登陆开始===========");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		boolean result = userService.login(user);
        logger.info("登录操作result" + result);

		if(result){
            logger.info("==========登陆结束===========");
			return "/user/success";
		}else{
            logger.info("==========登陆结束===========");
			return "/user/fail";
		}
	}

    /**
     * 根据用户id查询用户
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/showUser.do")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        long userId = Long.parseLong(request.getParameter("id"));
        User user = this.userService.selectUser(userId);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }

	/**
	 * model登陆
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
