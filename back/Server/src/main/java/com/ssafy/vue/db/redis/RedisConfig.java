package com.ssafy.vue.db.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ssafy.vue.redis.token.service.TokenStoreServiceImpl;

@SpringBootApplication
@Configuration
public class RedisConfig {
	
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.password}")
	private String redisPassword;

	@Bean
	RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisHost);
		redisStandaloneConfiguration.setPort(redisPort);
		redisStandaloneConfiguration.setPassword(redisPassword);
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return lettuceConnectionFactory;
	}

	@Bean
	RedisTemplate<String, String> redisTemplate() {
	    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(redisConnectionFactory());
	    redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setValueSerializer(redisTemplate.getStringSerializer());
	    return redisTemplate;
	}
	
    @Bean
    public HashOperations<String, String, String> hashOperations(RedisTemplate<String, String> redisTemplate) {

		return redisTemplate.opsForHash();
    }
	
//	@Bean
//	HashOperations<String, String, Object> hashOps() {
//		HashOperations<String, String, Object> hashOps = new HashOperations<>();
//		hashOps.setConnectionFactory(redisConnectionFactory());
//		hashOps.setKeySerializer(new StringRedisSerializer());
//		hashOps.setValueSerializer(hashOps.getStringSerializer());
//	    return hashOps;
//	}
}
//@Configuration
//public class RedisConfig {
//    @Value("${spring.redis.host}")
//    private String redisHost;
//    
//    @Value("${spring.redis.port}")
//    private int redisPort;
//    
//    @Value("${spring.redis.password}")
//    private String redisPassword;
//
//    @Bean
//    RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(redisHost);
//        redisStandaloneConfiguration.setPort(redisPort);
//        redisStandaloneConfiguration.setPassword(redisPassword);
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
//        return lettuceConnectionFactory;
//    }
//
//    @Bean
//    RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        // Use the default serializers for keys and values
//        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
//        redisTemplate.setValueSerializer(redisTemplate.getDefaultSerializer());
//        return redisTemplate;
//    }
//}
