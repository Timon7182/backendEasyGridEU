package com.timon.rhouse;

import org.springframework.core.Ordered;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/WelcomePage.jsf");
//        registry.addViewController("/login").setViewName("/login.jsf");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


//    https://stackoverflow.com/questions/26057995/changing-default-welcome-page-for-spring-boot-application-deployed-as-a-war
}