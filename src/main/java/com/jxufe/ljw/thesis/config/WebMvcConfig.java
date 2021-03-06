package com.jxufe.ljw.thesis.config;

/**
 * @Classname WebMvcConfig
 * @Author: LeJunWen
 * @Date: 2020/2/25 14:21
 */

import com.jxufe.ljw.thesis.intercepter.CommonIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CommonIntercepter commonIntercepter;

    /**
     * 这个方法是用来配置静态资源的，比如html，js，css，等等
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonIntercepter).addPathPatterns("/**")
                .excludePathPatterns("/classpath:/META-INF/resources/")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/classpath:/META-INF/resources/webjars/")
                .excludePathPatterns("/v2/api-docs", "/swagger-resources", "/v2/api-docs-ext")
                .excludePathPatterns("/login.html")
                .excludePathPatterns("/forget.html")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/amazeui/**")
                .excludePathPatterns("/error.html")
                .excludePathPatterns("/logincode")
                .excludePathPatterns("/druid/*")
                .excludePathPatterns("/forgetPassword")
                .excludePathPatterns("/login")
                .excludePathPatterns("/loginOut")
                .excludePathPatterns("/common/*.js")
                .excludePathPatterns("/js/*.js")
                .excludePathPatterns("/js/page/*.js")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/images/*.png")
                .excludePathPatterns("/images/*.jpg");
    }
}
