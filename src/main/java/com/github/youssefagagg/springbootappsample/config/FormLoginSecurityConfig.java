package com.github.youssefagagg.springbootappsample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class FormLoginSecurityConfig {

    @Bean

    public SecurityFilterChain formWebSecurityConfig(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
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
