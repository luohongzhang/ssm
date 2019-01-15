package com.jiangcheng.utils.token;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * 类名称：JwtTokenTest<br>
 * 类描述：<br>
 * 创建时间：2019年01月15日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */
public class JwtTokenTest {

    public static void main(String[] args) throws Exception {

        String token = JwtToken.creatToken();
        System.out.println("Token:" + token);

        Map<String,Claim> claims = JwtToken.verifyToken(token);
        System.out.println(claims.get("name").asString());
        System.out.println(claims.get("age").asString());
        System.out.println(claims.get("org") == null ? null : claims.get("org").asString());

        //使用过期后的token进行校验
        String tokenExpire = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmciOiJzaW5vY2hlbSIsIm5hbWUiOiJqaWFuZ2NoZW5nMDUiLCJleHAiOjE1NDc1MzYzMTYsImlhdCI6MTU0NzUzNjI1NiwiYWdlIjoiMzAifQ.axGJsFhnUbmEWNjNj6IMj6SkD2WN8WC4HrxmRjGEdPs";
        Map<String,Claim> claimsExpire = JwtToken.verifyToken(tokenExpire);

    }
}