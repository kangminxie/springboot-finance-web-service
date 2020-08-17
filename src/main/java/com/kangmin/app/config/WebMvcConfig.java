package com.kangmin.app.config;

import com.kangmin.app.interceptor.AdminPrivilegeHandlerInterceptor;
import com.kangmin.app.interceptor.LoginSessionHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.kangmin.app")
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    LoginSessionHandlerInterceptor loginSessionFilter() {
        return new LoginSessionHandlerInterceptor();
    }

    @Bean
    AdminPrivilegeHandlerInterceptor adminPrivilegeFilter() {
        return new AdminPrivilegeHandlerInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginSessionFilter())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/favicon.ico",
                        "/public/**",
                        "", "/", "/index",
                        "/login",
                        "/logout",
                        "/register",
                        "/H2/**",
                        "/error/**"
                );

        registry.addInterceptor(adminPrivilegeFilter())
                .addPathPatterns("/admin/**");
    }
}
