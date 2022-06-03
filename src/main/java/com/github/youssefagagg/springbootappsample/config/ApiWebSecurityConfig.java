package com.github.youssefagagg.springbootappsample.config;

import com.github.youssefagagg.springbootappsample.security.BasicAuthFilter;
import com.github.youssefagagg.springbootappsample.security.BasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class ApiWebSecurityConfig {
    private final BasicAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationManager authenticationManager;
    private final BasicAuthFilter basicAuthFilter;
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain httpBaiskWebSecurityConfig(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
//
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .requestMatchers().antMatchers("/api/**").and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .antMatchers("/api/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.authenticationManager(authenticationManager);
        http.addFilterBefore(basicAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
