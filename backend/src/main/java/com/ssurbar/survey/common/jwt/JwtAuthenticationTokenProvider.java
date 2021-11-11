package com.ssurbar.survey.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssurbar.survey.common.model.common.UserInfo;
import com.ssurbar.survey.db.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationTokenProvider implements AuthenticationTokenProvider{

    private static final String SECRET_KEY = "HIHIHI";
    private static final long ACCESS_EXPIRATION = 1000 *60 *60 * 24;
    private static final String TOKEN_NAME = "authorization";

    private final Key key;

    public JwtAuthenticationTokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public HttpHeaders setTokenHeader(HttpHeaders header, String token) {
        header.set(TOKEN_NAME, token);
        return header;
    }

    @Override
    public String parseTokenString(HttpServletRequest request) {
//        Enumeration<String> a =request.getHeaderNames();
//        while(a.hasMoreElements()){
//            System.out.println(a.nextElement());
//        }

        String token = request.getHeader(TOKEN_NAME);

        if(token != null){
            return token;
        }
        return null;
    }

    @Override
    public AuthenticationToken issue(UserInfo user) {
        return JwtAuthenticationToken.builder().token(createToken(user)).build();
    }

    @Override
    public UserInfo getTokenUser(String token) {

        try {
            Map<String, Object> claims =  Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token)
                    .getBody();

            UserInfo user = UserInfo.builder()
                    .userId((String)claims.get("userId"))
                    .userType(UserRole.valueOf((String)claims.get("userType")))
                    .build();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명");
        } catch (ExpiredJwtException e){
            System.out.println("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e){
            System.out.println("지원되지 않는 JWT 토큰");
        } catch(IllegalArgumentException e){
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private String createToken(UserInfo user){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireAt = now.plus(ACCESS_EXPIRATION, ChronoUnit.MILLIS);

        ObjectMapper objMapper = new ObjectMapper();
        Map<String, Object> payloads = objMapper.convertValue(user, HashMap.class);
        return Jwts.builder()
                .setSubject("user")
                .setClaims(payloads)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
