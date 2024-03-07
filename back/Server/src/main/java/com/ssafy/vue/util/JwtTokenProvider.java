package com.ssafy.vue.util;
import java.util.Base64;
import java.util.Date;

import com.ssafy.vue.member.model.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenProvider {

	public static String createAccessToken(Member m, String salt) {

		// member의 정보를 payload에 담아서 전송
		Claims claims = Jwts.claims();
		claims.put("id",m.getUser_id());
		claims.put("name", m.getUser_name());
		claims.put("type", "access");

		Date now = new Date();

		String token="";
		token=Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims) //저장 데이터
				.setIssuedAt(now) //발행시간
				.setExpiration(new Date(now.getTime()+(1000L*60*5))) //만료시간 1분 // 20초 테스트 중
				.signWith(SignatureAlgorithm.HS256, salt.getBytes())
				.compact();

		return token;
	}

	public static String createRefreshToken(String id, String salt) {
		String token="";

		Claims claims=Jwts.claims();
		claims.put("id",id); //String
		claims.put("type", "refresh");

		Date now=new Date();

		token=Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims) //저장 데이터
				.setIssuedAt(now)//발행시간
				.setExpiration(new Date(now.getTime()+(1000L*60*30)))//만료시간 30분
				.signWith(SignatureAlgorithm.HS256,salt.getBytes())
				.compact();

		return token;
	}

	// Jwt Token의 유효성 및 만료 기간 검사합니다
	public static boolean validateToken(String jwtToken, String salt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	// Jwt Token에 담긴 Payload 정보를 꺼낸다.
	public static Claims getInformation(String token, String salt) {
		Claims claims =Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token).getBody();
		return claims;
	}

}