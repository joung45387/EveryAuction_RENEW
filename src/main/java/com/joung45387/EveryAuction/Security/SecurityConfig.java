package com.joung45387.EveryAuction.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder cryptPW(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeHttpRequests()
                .antMatchers("/per").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                //.loginPage("/loginForm")
                .loginProcessingUrl("login")
                .defaultSuccessUrl("/");

        return http.build();
    }
}
