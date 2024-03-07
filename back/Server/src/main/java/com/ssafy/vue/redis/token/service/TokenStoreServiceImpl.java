package com.ssafy.vue.redis.token.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class TokenStoreServiceImpl implements TokenStoreService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private HashOperations<String, String, String> hashOps;

//    private final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

//    private RedisTemplate<String, Object> redisTemplate;
//    private final HashOperations<String, String, Object> hashOps = new HashOperations<>();

//	public TokenStoreServiceImpl() {
//		this.redisTemplate = redisTemplate;
////		this.hashOps = redisTemplate.opsForHash();
//	}

	public void saveTokens(String userId, String accessToken, String refreshToken, String tokenSalt) {
		System.out.println(userId);
		System.out.println("accessToken :" + accessToken);
		System.out.println("refreshToken :" + refreshToken);
		System.out.println("Tokensalt :" + tokenSalt);
        hashOps.put(userId, "access_token", accessToken);
        hashOps.put(userId, "refresh_token", refreshToken);
        hashOps.put(userId, "Tokensalt", tokenSalt);
		System.out.println(hashOps.get(userId, "Tokensalt"));
		System.out.println("pass");
	}
	
	public void saveUserIdAccessToken(String accessToken, String userId) {
		hashOps.put(accessToken, "userId", userId);
		hashOps.put(accessToken, "type", "access");
	}
	
	public void saveUserIdRefreshToken(String refreshToken, String userId) {
		hashOps.put(refreshToken, "userId", userId);
		hashOps.put(refreshToken, "type", "refresh");
	}
	
	public String getUserId(String accessToken) {
//		return (String) hashOps.get(accessToken, "userId");
        Object userId = hashOps.get(accessToken, "userId");
        return userId != null ? userId.toString() : null;
	}

	public String getAccessToken(String userId) {
        return (String) hashOps.get(userId, "access_token");
	}

	public String getRefreshToken(String userId) {
        return (String) hashOps.get(userId, "refresh_token");
	}

	public String getTokenSalt(String userId) {
		return hashOps.get(userId, "Tokensalt");
	}
	
	public int deleteTokens(String userId) {
		if(hashOps.delete(userId, "access_token", "refresh_token", "Tokensalt") !=0) return 1;
		else return 0;
	}
	
	public int deleteUserId(String accessToken) {
		if(hashOps.delete(accessToken, "userId") !=0 && hashOps.delete(accessToken, "type") !=0) return 1;
		else return 0;
	}

	@Override
	public String extractAndDecodePayload(String jwtToken) {
		try {
			// JWT 토큰을 "."을 기준으로 분리
			String[] parts = jwtToken.split("\\.");

			// Base64 디코딩 후 UTF-8 문자열로 변환
			byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
			return new String(decodedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
