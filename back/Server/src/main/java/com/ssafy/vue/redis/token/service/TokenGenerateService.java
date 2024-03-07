package com.ssafy.vue.redis.token.service;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.util.JwtTokenProvider;

@Service
public class TokenGenerateService {

	public String generateTokenSalt() {
		String salt = UUID.randomUUID().toString();
		salt = Base64.getEncoder().encodeToString(salt.getBytes());
		return salt;
	}
	
	public String generateAccessToken(Member m, String salt) {
		String access_token= JwtTokenProvider.createAccessToken(m, salt);
		return access_token;
	}
	
	public String generateRefreshToken(Member m, String salt) {
		String refresh_token=JwtTokenProvider.createRefreshToken(m.getUser_id(), salt);
		return refresh_token;
	}
}
