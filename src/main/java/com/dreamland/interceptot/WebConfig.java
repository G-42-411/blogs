package com.dreamland.interceptot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private AbnormalLogin abnormalLogin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(abnormalLogin).addPathPatterns("/**").excludePathPatterns("/question/*","/","/css/**","/js/**","/fonts/**",
//                "https://github.com/*","https://api.github.com/*");
    }
}
