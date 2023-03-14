package com.example.demo.config;

import com.example.demo.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 22:12
 * @Description:
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Configuration
    public static class GoWebMvcConfigurerAdapter implements WebMvcConfigurer {
        /**
         * @Auther: GengXuelong
         * <p> 函数功能描述如下:
         * @Description:
         *     配置静态资源处理
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            //配置静态资源处理
            registry.addResourceHandler("/**")
                    .addResourceLocations("resources/", "static/", "public/",
                            "META-INF/resources/")
                    .addResourceLocations("classpath:resources/", "classpath:static/",
                            "classpath:public/", "classpath:META-INF/resources/")
                    .addResourceLocations("file:///tmp/webapps/");
        }
        /**
         * @Auther: GengXuelong
         * <p> 函数功能描述如下:
         * @Description:
         *     拦截器配置函数
         */
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(
                    "/css/**","/images/**","/js/**","/index/**","/data/**","/function/**","/login/**","/","/**.jar/**","/error/**"
            );
        }
    }

}
