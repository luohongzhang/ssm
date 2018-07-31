package com.jiangcheng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiangcheng.bean.UserBean;
import com.jiangcheng.mapper.UserMapper;
import com.jiangcheng.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public boolean login(UserBean user) {
		int result = userMapper.login(user);
		if(result > 0){
			return true;
		}else{
			return false;
		}
		
	}

}
