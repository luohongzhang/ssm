package com.jiangcheng.utils;

/**
 * 类名称：StringUtil<br>
 * 类描述：<br>
 * 创建时间：2018年08月25日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class StringUtil {
    /*
     * 判断字符串是否为空
     * */
    public static boolean isEmpty(String str){
        if(str != null){
            str=str.trim();
        }
        //return StringUtils.isEmpty(str);
        return "".equals(str);
    }
    /*
     * 判断字符串是否非空
     * */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
