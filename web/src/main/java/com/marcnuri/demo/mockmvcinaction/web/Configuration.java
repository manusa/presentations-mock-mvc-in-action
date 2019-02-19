package com.marcnuri.demo.mockmvcinaction.web;

import com.marcnuri.demo.mockmvcinaction.web.beer.BeerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-01.
 */
@org.springframework.context.annotation.Configuration
@EnableMapRepositories(basePackageClasses = {BeerRepository.class})
public class Configuration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true); // <- Spring 4/5 defaults this to true - Spring Boot 2 overrides this behavior -->
        // --> https://github.com/spring-projects/spring-boot/issues/11105
        // --> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/PathMatchConfigurer.html#setUseSuffixPatternMatch-java.lang.Boolean-
        // --> https://github.com/spring-projects/spring-boot/commit/67e5897c4042030494ce69d133cfe68c7f4a7d6f#diff-4b931de6d3ec1f4484f63fecb0cd4947
    }

    @Bean
    public InternalResourceViewResolver htmlViewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }
}
