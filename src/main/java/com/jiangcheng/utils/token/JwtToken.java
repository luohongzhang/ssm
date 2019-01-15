package com.jiangcheng.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：JwtToken<br>
 * 类描述：<br>
 * 创建时间：2019年01月15日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class JwtToken {

    /**
     * 公共秘钥-保存在服务端，客户端不必知道
     */
    private static final String SECRET = "jiangcheng05";

    /**
     * 生成token
     * @return
     * @throws Exception
     */
    public static String creatToken() throws Exception {

        //签发时间
        Date iatDate = new Date();

        //过期时间 1分钟
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,1);
        Date expiresDate = nowTime.getTime();

        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");

        String token = JWT.create()
                .withHeader(map) //header
                .withClaim("name","jiangcheng05") //playload
                .withClaim("age","30")
                .withClaim("org","sinochem")
                .withExpiresAt(expiresDate) //设置过期时间-过期时间要大于签发时间
                .withIssuedAt(iatDate) //设置签发时间
                .sign(Algorithm.HMAC256(SECRET)); //加密

        return token;

    }

    /**
     * 解密token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String,Claim> verifyToken(String token) throws Exception {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .build();

        DecodedJWT jwt = null;

        try {

            jwt = verifier.verify(token);
        } catch (Exception e) {

            throw new RuntimeException("登录凭证已过去，请重新登陆");
        }

        return jwt.getClaims();
    }
}
