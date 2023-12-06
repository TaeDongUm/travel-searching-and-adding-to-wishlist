package com.ssafy.vue.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
//@Component
public class TokenCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        String secretKey = "";
        log.info(token);
        if(token == null) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
             // preflight false로 돌려보내기 전에 cors 에러 해결
            return false;
        } else {
            return true;
        }



//        if (token != null && token.startsWith("Bearer ")) {
//            try {
//                // Bearer 다음의 공백을 제거하고 토큰 검증
//                String jwtToken = token.substring(7);
//                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
//
//                // 토큰이 만료되었는지 확인
//                if (claims.getExpiration().before(new java.util.Date())) {
//                    // 토큰이 만료되었으면 예외 발생
//                    throw new ExpiredJwtException(null, null, "Access Token has expired");
//                }
//
//                // 토큰이 유효하면 계속 진행
//                return true;
//            } catch (ExpiredJwtException e) {
//                // 토큰이 만료되었을 때의 처리
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Access Token has expired");
//                return false;
//            } catch (Exception e) {
//                // 그 외의 예외가 발생하면 인증 실패
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid Access Token");
//                return false;
//            }
//        }
//
//        // 토큰이 없는 경우 또는 형식이 올바르지 않은 경우
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write("Missing or invalid Access Token");
//        return false;
    }
}
