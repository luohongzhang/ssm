package com.jiangcheng.mapper;

import com.jiangcheng.bean.UserBean;
import org.springframework.stereotype.Repository;

/**
 * 用户dao类
 * @author Chan Kiang
 *
 */
@Repository
public interface UserMapper {
	
	int login(UserBean user);
}
