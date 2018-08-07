package com.richinfo.annotation.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig implements WebMvcConfigurer {

    private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：除了login，其他都拦截判断
        InterceptorRegistration addInterceptor =  registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        addInterceptor.excludePathPatterns("/admin/login");
        addInterceptor.excludePathPatterns("/admin/loginto");
        addInterceptor.excludePathPatterns("/admin/firstuser");
        addInterceptor.excludePathPatterns("/admin/saveuser");
        addInterceptor.excludePathPatterns("/static/**");

    }

}