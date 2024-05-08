package com.refore.our.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
    //    config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOriginPattern("*");

        // 응답 헤더에서 허용할 헤더 목록을 추가합니다.
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Refresh-Token");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
