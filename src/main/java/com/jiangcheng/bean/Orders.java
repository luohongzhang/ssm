package com.jiangcheng.bean;

import lombok.Data;

/**
 * 类名称：Orders<br>
 * 类描述：<br>
 * 创建时间：2018年08月30日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public @Data class Orders {

    //订单ID
    private int id;

    //用户ID
    private int userId;

    //订单数量
    private String number;

    //和用户表构成一对一的关系，即一个订单只能由一个用户创建
    private User user;
}
