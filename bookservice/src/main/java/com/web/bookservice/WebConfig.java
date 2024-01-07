package com.web.bookservice;

//import com.web.bookservice.interceptor.LoginCheckInterceptor;
import com.web.bookservice.interceptor.URICheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns();

        registry.addInterceptor(new URICheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login", "/search", "/search/books", "/search/book/*", "/register", "/discussion/list",
                        "/discussion/post/*",
                        "/css/**", "/img/**", "/error", "/*.ico"
                );


    }
}
