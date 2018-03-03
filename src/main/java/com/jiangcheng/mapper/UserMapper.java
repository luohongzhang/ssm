package com.jiangcheng.mapper;

import com.jiangcheng.bean.UserBean;

/**
 * 用户dao类
 * @author Chan Kiang
 *
 */
public interface UserMapper {
	
	int login(UserBean user);
}
