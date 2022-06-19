package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

/**
 * 拦截器
 */
@Configuration
public class UserLoginInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        创建自定义的拦截器对象
        LoginInterceptor loginInterceptor = new LoginInterceptor();
//        完成拦截器的注册
//        拦截器的白名单
      /*  String[] exclude={"/bootstrap3/**","/css/**","/images/**","/js/**","/web/login.html",
                         "/web/register.html","/web/index.html","/web/product.html","/user/login",
                        "/user/reg","/district/**","/products/**"};*/
        ArrayList<String> list = new ArrayList<>();

        list.add("/bootstrap3/**");
        list.add("/css/**");
        list.add("/images/**");
        list.add("/js/**");

        list.add("/web/login.html");
        list.add("/web/register.html");
        list.add("/user/reg");
        list.add("/user/login");
        list.add("/web/index.html");

        registry.addInterceptor(loginInterceptor).
                addPathPatterns("/**").
                excludePathPatterns(list);
    }
}
