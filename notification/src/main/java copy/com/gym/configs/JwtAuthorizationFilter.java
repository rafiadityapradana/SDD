package com.gym.configs;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

@Autowired
    JwtTokenProvider jwtUtil;
    @Autowired
    ObjectMapper mapper;

    public JwtAuthorizationFilter(JwtTokenProvider jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }
    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain filterChain) throws javax.servlet.ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null ) {
                filterChain.doFilter(request, response);
                return;
            }
            String claims = jwtUtil.resolveClaims(request);
            if(claims != null & jwtUtil.validateToken(accessToken)){
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(claims,"",new ArrayList<>());
               SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch (Exception e){
               errorDetails.put("status", false);
               errorDetails.put("message", "Authentication Error");
               errorDetails.put("data", null);
               response.setStatus(HttpStatus.FORBIDDEN.value());
               response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);

        }
        filterChain.doFilter(request, response);
    }
}