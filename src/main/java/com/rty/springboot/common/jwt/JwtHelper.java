package com.rty.springboot.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtHelper {


    public static boolean verityJWT(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String username = getUsername(token);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /* 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /*
     *第一部分我们称它为头部（header),
     *
     *第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，
     *
     *第三部分是签证（signature).
     *
     * */
    public static String createJWT(String name, String userId, String role, String audience, String issuer,
                                   long TTLMillis, String base64Security) {

        Date date = new Date(System.currentTimeMillis() + TTLMillis);
        Algorithm algorithm = Algorithm.HMAC256(base64Security);
        // 附带username信息
        return JWT.create()
                .withClaim("username", name)
                .withExpiresAt(date)
                .sign(algorithm);

    }
}
