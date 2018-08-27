package com.jiangcheng.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类名称：Base<br>
 * 类描述：可以获取对象类型<br>
 * 创建时间：2018年08月27日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class Base<T> {
    protected Class<T> clazz;

    public void getClassType(){
        Class clazz = getClass();

        while (clazz != Object.class){
            Type t = clazz.getGenericSuperclass();
            if (t instanceof ParameterizedType){
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class){
                    this.clazz = (Class<T>) args[0];
                    break;
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
