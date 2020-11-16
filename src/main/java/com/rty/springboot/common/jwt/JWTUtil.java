package com.huawei.shiro.jwt.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {

    /*
     *检验token是否正确
     * @param token 密匙
     * @param username 用户名
     * @param secret 用户密码
     * return 是否正确
     * */
    public static boolean verity(String token, String username, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier=JWT.require(algorithm).withClaim("username", username)
                .build();
        verifier.verify(token);
        return false;

    }


}
