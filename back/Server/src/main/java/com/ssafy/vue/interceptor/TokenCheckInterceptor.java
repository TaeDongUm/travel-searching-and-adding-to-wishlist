package com.ssafy.vue.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.member.model.dao.MemberMapper;
import com.ssafy.vue.member.service.MemberService;
import com.ssafy.vue.redis.token.service.TokenStoreService;
import com.ssafy.vue.redis.token.service.TokenStoreServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
//@Component
public class TokenCheckInterceptor implements HandlerInterceptor {
    MemberMapper memberMapper;
    
	@Autowired
	private TokenStoreService tokenStoreService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        String secretKey = ""; 
        log.info(token);
        if(token == null) {
        	System.out.println("::::::::::::::");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
             // preflight false로 돌려보내기 전에 cors 에러 해결
            return false;
        } else {
            if (token.startsWith("Bearer ")) {
                try {
                    // Bearer 다음의 공백을 제거하고 토큰 검증
                    String jwtToken = token.substring(7);

                    // Payload에서 ID 추출
                    String payload = tokenStoreService.extractAndDecodePayload(jwtToken);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(payload);
                    System.out.println(jsonNode);
                    JsonNode idNode = jsonNode.get("id");
                    System.out.println(idNode);
//
                    String idValue = idNode.textValue();
                    System.out.println(idValue);
                    String salt = tokenStoreService.getTokenSalt(idValue);
                    
                    Claims claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwtToken).getBody();
                    log.info("claims : ", claims.toString());

                    // 토큰이 만료되었는지 확인
                    if (claims.getExpiration().before(new java.util.Date())) {
//                         토큰이 만료되었으면 예외 발생
                        throw new ExpiredJwtException(null, null, "Access Token has expired");
                    }

                    // 토큰이 유효하면 계속 진행
                    return true;
                } catch (ExpiredJwtException e) {
                    // 토큰이 만료되었을 때의 처리
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Access Token has expired");
                    return false;
                }
            }

            return true;
        }
    }
}

