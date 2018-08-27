package com.jiangcheng.entity;

import lombok.Data;

import java.util.List;

/**
 * 类名称：Person<br>
 * 类描述：<br>
 * 创建时间：2018年08月25日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */
@Data
public class Person {
    private String name;
    private int age;
    private String sex;
    private List<Person> children;
}
