package com.github.youssefagagg.springbootappsample.security;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.youssefagagg.springbootappsample.web.controller.exception.UserNotActivatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@Component
@RequiredArgsConstructor
public class BasicAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        log.debug(request.getRequestURI());
        try {
            //Check for the requests that starts with /api
            if (request.getRequestURI().startsWith("/api/")) {
                //Fetch Credential from authorization header
                String authorization = request.getHeader("Authorization");
                String base64Credentials = authorization.substring("Basic".length()).trim();
                byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(credDecoded, StandardCharsets.UTF_8);
                final String username = credentials.split(":", 2)[0];
                final String password = credentials.split(":", 2)[1];
                var userDetails = userDetailsService.loadUserByUsername(username);

                if(userDetails!=null&&passwordEncoder.matches(password,userDetails.getPassword()))
                    SecurityUtils.authenticateUser(request, userDetails);

            }

        }catch (UsernameNotFoundException | UserNotActivatedException ex){
            log.warn("error: ",ex);
        }

        chain.doFilter(request, response);

    }
}
