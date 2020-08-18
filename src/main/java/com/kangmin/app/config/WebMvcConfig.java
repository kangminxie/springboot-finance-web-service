package com.kangmin.app.config;

import com.kangmin.app.interceptor.AdminPrivilegeHandlerInterceptor;
import com.kangmin.app.interceptor.ExcludeAdminHandlerInterceptor;
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

    @Bean
    ExcludeAdminHandlerInterceptor excludeAdminHandlerInterceptor() {
        return new ExcludeAdminHandlerInterceptor();
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
                        "/error/**",
                        "/H2/**"    // dev-H2-check
                );

        registry.addInterceptor(adminPrivilegeFilter())
                .addPathPatterns("/admin/**");

        registry.addInterceptor(excludeAdminHandlerInterceptor())
                .addPathPatterns(
                        "/funds/buy",
                        "/funds/sell",
                        "/requestCheck"
                );
    }
}
