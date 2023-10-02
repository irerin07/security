package com.tistory.irerin07.security.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author 민경수
 * @description jwt authentication filter
 * @since 2023.10.02
 **********************************************************************************************************************/
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authorization = request.getHeader("Authorization");

    if (authorization == null || !authorization.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);

      return;
    }

    String jwt = authorization.substring(7);

  }

}
