package com.jiangcheng.service;

import com.jiangcheng.bean.User;

public interface UserService {

	/**
	 * 根据用户名和密码查询用户数
	 * @param user
	 * @return
	 */
	boolean login(User user);

	/**
	 * 根据用户id 查询用户信息
	 * @param userId
	 * @return
	 */
	User selectUser(long userId);
}
