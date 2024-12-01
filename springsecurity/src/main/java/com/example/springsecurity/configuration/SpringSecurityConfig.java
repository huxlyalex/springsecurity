package com.example.springsecurity.configuration;

import com.example.springsecurity.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableSpringConfigured
public class SpringSecurityConfig {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(cutomizer -> cutomizer.disable());//it is used to disable csrf token(required for put and update request)
        //the above we use lambda expression ,http.csrf function accept object of type interface (functional interface)
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/saveuser","/loginuser")
                .permitAll()// this means the above two request will be permitted and all the rest will be authenticated
                .anyRequest().authenticated());//it is used to authenticate any request
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// to make session stateless ,that is to remove session id
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);// this is to specify to use jwtFilter before the default UsernamePasswordAuthenticationFilter
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();// DaoAuthenticationProvider is a class that implements AuthenticationProvider
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));// to encrpt the incoming plain password tp Bcrypt password
        provider.setUserDetailsService(myUserDetailService);// here we configure to use our UserDetailService
        return provider;


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // this step is to return the object of AuthenticationManager
    }
}


