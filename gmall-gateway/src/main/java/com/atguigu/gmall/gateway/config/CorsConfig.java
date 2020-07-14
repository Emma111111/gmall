package com.atguigu.gmall.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author shkstart
 * @create 2020-01-03 18:36
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        // 初始化CORS配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许的域,不要写*，否则cookie就无法使用了
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:1000");
        corsConfiguration.addAllowedOrigin("http://localhost:1000");
       // 允许的头信息
        corsConfiguration.addAllowedHeader("*");
        // 允许的请求方式
        corsConfiguration.addAllowedMethod("*");
        // 是否允许携带Cookie信息
        corsConfiguration.setAllowCredentials(true);
        // 添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);

    }
}
