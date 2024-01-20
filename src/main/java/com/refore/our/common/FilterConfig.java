//package com.refore.our.common;
//
//import com.refore.our.member.filter.MyFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//
//
//    @Bean
//    FilterRegistrationBean<MyFilter> filter() {
//        FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<>(new MyFilter());
//        bean.addUrlPatterns("/*");  // 모든 경로에 대해 필터 적용
//        bean.setOrder(0); // 낮은 번호 우선
//        return bean;
//    }
//
//
//}
