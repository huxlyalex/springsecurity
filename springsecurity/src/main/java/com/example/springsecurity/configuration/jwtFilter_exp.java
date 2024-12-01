package com.example.springsecurity.configuration;

import com.example.springsecurity.service.JWTService;
import com.example.springsecurity.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class jwtFilter_exp extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWh1bCIsImlhdCI6MTczMjk2OTQ0MCwiZXhwIjoxNzMyOTY5NTQ4fQ.mh18sa_WC5g9XVfuvKaC8XfNMq_ftrImd3kaVCiKSOU // this is an example of the format of the token received  from the client
        String authHeader = request.getHeader("Authorization"); // to get only the authorization header
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);//to remove the bearer from the token format
            username = jwtService.extractUserName(token); // to get username from the token
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(username); // the username received from the token is used here and this will give the entire userDetails object
            if (jwtService.validateToken(username, userDetails)) // username and password is correct ,so the next filter UsernamePasswordAuthentication filter should be activated by using the UsernamePasswordAuthenticationToken
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // setting the authentication object , that we checked first in if statement

            }
        }
        filterChain.doFilter(request, response);//continue with the next filter


    }


}

