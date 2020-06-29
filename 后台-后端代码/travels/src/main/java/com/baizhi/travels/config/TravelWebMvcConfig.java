package com.baizhi.travels.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TravelWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/province/provincelist.html").setViewName("/province/provincelist");
        registry.addViewController("/reg.html").setViewName("reg");
        registry.addViewController("/province/addprovince.html").setViewName("/province/addprovince");
        registry.addViewController("/province/updateprovince.html").setViewName("/province/updateprovince");
        registry.addViewController("/viewspot/viewspotlist.html").setViewName("/viewspot/viewspotlist");
        registry.addViewController("/viewspot/addviewspot.html").setViewName("/viewspot/addviewspot");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");

    }
}
