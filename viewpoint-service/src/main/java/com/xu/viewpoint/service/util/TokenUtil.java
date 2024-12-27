package com.xu.viewpoint.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: xuJing
 * @date: 2024/11/21 11:18
 */

public class TokenUtil {

    private static final String ISSUER = "com.xu.viewpoint";

    public static void main(String[] args) throws Exception {
        String token = generateToken(100L);

        System.out.println(verifyToken(token, null));
        System.out.println(token.length());
        Thread.sleep(30000);
        System.out.println(verifyToken(token, null));

    }


    public static String generateToken(Long userId) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);

        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static Long verifyToken(String token, String refreshToken){
        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            if(StringUtils.isNullOrEmpty(refreshToken)){
                throw new ConditionException("40411", "登录过期！");
            }else {
                throw new ConditionException("40412", "登录过期！");
            }

        } catch (Exception e){
            throw new ConditionException("500", "系统错误！");
        }
    }

    public static String generateRefreshToken(Long id) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return JWT.create().withKeyId(String.valueOf(id))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }


    public static Long verifyRefreshToken(String refreshToken) {
        Long userId = TokenUtil.verifyToken(refreshToken, "refreshToken");
        return userId;
    }
}
