package com.jiangcheng.bean;

import lombok.Data;

import java.util.List;

/**
 * 用户实体类
 * @author Chan.Kiang
 *
 */
public @Data class User {

	//用户id
	private int id;

	//用户姓名
	private String realname;

	//用户性别
	private String sex;

	//用户名
	private String username;

	//密码
	private String password;

	//一个用户能创建多个订单，用户和订单构成一对多的关系
    private List<Orders> orders;

}
