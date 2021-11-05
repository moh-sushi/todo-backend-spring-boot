package com.todobackend.mohsushi.springboot;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TodoBackendConfiguration implements WebMvcConfigurer {

  @Bean
  public Module hibernateModule() {
    return new Hibernate5Module();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
            ;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    PropertySourcesPlaceholderConfigurer propsConfig
            = new PropertySourcesPlaceholderConfigurer();
    propsConfig.setLocation(new ClassPathResource("git.properties"));
    propsConfig.setIgnoreResourceNotFound(false);
    propsConfig.setIgnoreUnresolvablePlaceholders(false);
    return propsConfig;
  }
}
