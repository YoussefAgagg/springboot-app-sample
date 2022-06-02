package com.github.youssefagagg.springbootappsample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    @Order(1)
    public SecurityFilterChain httpBaiskWebSecurityConfig(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();

    }

    @Bean
    @Order(2)
    public SecurityFilterChain formWebSecurityConfig(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/account/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/entity/**").authenticated()
                .mvcMatchers("/entity/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home").permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
        return http.build();

    }
}
