package com.kangmin.app.config;

import com.kangmin.app.interceptor.AdminPrivilegeHandlerInterceptor;
import com.kangmin.app.interceptor.ExcludeAdminHandlerInterceptor;
import com.kangmin.app.interceptor.LoginSessionHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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


    // == spring-boot make it so easy for auto-configurations ==
    // == most of things can be done in application.properties ==

    private static final long MAX_AGE_SECS = 60;

    // == if we do development by separating frontend and backend ==
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",
                "http://localhost:3001",
                "http://localhost:4200",
                "http://localhost:5000"
            )
            .allowedMethods(
                "HEAD",
                "OPTIONS",
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE"
            )
            .maxAge(MAX_AGE_SECS);
    }
}
