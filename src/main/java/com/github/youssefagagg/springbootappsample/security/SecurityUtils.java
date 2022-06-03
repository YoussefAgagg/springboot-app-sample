package com.github.youssefagagg.springbootappsample.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;


@Slf4j
public final class SecurityUtils {

  private SecurityUtils() {}

  public static void setAuthentication(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
  public static void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
   log.debug("authentication");
    if (Objects.nonNull(request) && Objects.nonNull(userDetails)) {
      var authorities = userDetails.getAuthorities();
      var authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      setAuthentication(authentication);
    }
  }




}
