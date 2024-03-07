package com.ssafy.vue.redis.token.service;

public interface TokenStoreService {

	public void saveTokens(String userId, String accessToken, String refreshToken, String tokenSalt);
	public String getAccessToken(String userId);
	public String getRefreshToken(String userId);
	public String getTokenSalt(String userId);

	public String extractAndDecodePayload(String jwtToken);
}
