package com.ssafy.vue.util;

import com.ssafy.vue.interceptor.TokenCheckInterceptor;
import com.ssafy.vue.redis.token.service.TokenStoreServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization") // 허용할 헤더
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 전체 경로에 다 되지만 exclude는 빼고 하자.
        registry.addInterceptor(tokenCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/member/login", "/member/join", "/member/idcheck",
                                    "/member/emailcheck", "/member/logout");
    }

    @Bean
    public TokenCheckInterceptor tokenCheckInterceptor() {
        return new TokenCheckInterceptor();
    }
}
