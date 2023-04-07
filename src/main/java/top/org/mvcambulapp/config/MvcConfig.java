package top.org.mvcambulapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry){

      //  registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
       // registry.addViewController("/login").setViewName("index");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/images/**").addResourceLocations("/static/images/");
    }
}
