package com.example.demo.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer
{


	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
               
                .authorizeHttpRequests(auth -> {
                	auth.requestMatchers("/**").permitAll();
                    auth.anyRequest().authenticated();
                    
                })
               
                .build();
    }

  /**
   * This bean is responsible for resolving texts from message_XX.properties files
   */
  @Bean
  public ResourceBundleMessageSource messageSource() {
    var resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasenames("i18n/messages");
    resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    resourceBundleMessageSource.setDefaultLocale(Locale.ENGLISH);
    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
    return resourceBundleMessageSource;
  }

  /**
   * This bean is responsible for setting a default locale for webpages
   */
 
  
  @Bean
  public LocaleResolver localeResolver() {
      SessionLocaleResolver slr = new SessionLocaleResolver();
      slr.setDefaultLocale(Locale.US);
      return slr;
  }

  /**
   * This bean is responsible for resolving locale from 'lang' parameter in URL
   * Example:
   *    /welcome?lang=pl_PL (Polish language, messages_pl_PL.properties)
   *    /welcome?lang=es_ES (Spanish language, messages_es_ES.properties)
   */
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }

  /**
   * This method is responsible for registering localeChangeInterceptor, so it can be used in the application
   * Overrides WebMvcConfigurer.addInterceptors method
   * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    registry.addInterceptor(localeChangeInterceptor());
  }


}
