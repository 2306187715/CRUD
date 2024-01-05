package com.example.config;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.example.pojo.StpStuUtil;
import com.example.pojo.StpTeacherUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Array;
import java.util.List;


@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
    
    
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) throws SaTokenException{
        registry.addInterceptor(new SaInterceptor(handle -> {
                    
                    // 登陆管理员
                    SaRouter.match("/students/getInfo",
                            "/teachers/getInfo"
                    ).check(r -> {
                        if (!StpUtil.isLogin()) {
                            throw new SaTokenException("权限不足0");
                        }
                    });


                    // 登录管理员/教师
                    SaRouter.match("/depts/getInfo",
                            "/electives/getInfo",
                            "/semesters/getInfo"
                    ).check(r -> {
                        if (!StpUtil.isLogin() && !StpTeacherUtil.isLogin()) {
                            throw new SaTokenException("权限不足1");
                        }
                    });


                    // 登录学生/教师
                    SaRouter.match("/schedules/getInfo").check(r -> {
                        if (!StpStuUtil.isLogin() && !StpTeacherUtil.isLogin()) {
                            throw new SaTokenException("权限不足2");
                        }
                    });


                    // 登录管理员/教师/学生
                    SaRouter.match("/dashboard/getInfo",
                            "/course/getInfo",
                            "/college/getInfo"
                            ).check(r -> {
                        if (!StpUtil.isLogin() && !StpStuUtil.isLogin() && !StpTeacherUtil.isLogin()) {
                            throw new SaTokenException("权限不足3");
                        }
                    });
                    
                })).addPathPatterns("/**")
                .excludePathPatterns("/login","/404","/403");
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加 Excel 消息转换器
        converters.add(new MappingJackson2HttpMessageConverter());
    }
    
    
}

