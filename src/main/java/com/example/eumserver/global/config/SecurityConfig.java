package com.example.eumserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests( auth -> {
          auth.requestMatchers("/").permitAll();
          auth.anyRequest().authenticated();
        })
        .formLogin(Customizer.withDefaults())
        .oauth2Login(oauth -> {
          oauth.defaultSuccessUrl("/user");
        })
        .build();
  }
}
