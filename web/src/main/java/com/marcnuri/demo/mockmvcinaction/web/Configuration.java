package com.marcnuri.demo.mockmvcinaction.web;

import com.marcnuri.demo.mockmvcinaction.web.beer.BeerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-01.
 */
@org.springframework.context.annotation.Configuration
@EnableWebMvc
@EnableMapRepositories(basePackageClasses = {BeerRepository.class})
public class Configuration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    @Bean
    public InternalResourceViewResolver htmlViewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }
}
