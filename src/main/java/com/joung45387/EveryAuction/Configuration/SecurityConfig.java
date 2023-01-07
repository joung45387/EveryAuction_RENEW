package com.joung45387.EveryAuction.Configuration;

import com.joung45387.EveryAuction.Security.Oauth.PrincipleOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipleOauth2UserService principleOauth2UserService;

    @Bean
    public BCryptPasswordEncoder cryptPW(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http
                .authorizeHttpRequests()
                .antMatchers("/", "/signin", "/signup", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/signin")
                .defaultSuccessUrl("/oauthlogin")
                .userInfoEndpoint()
                .userService(principleOauth2UserService);
        return http.build();
    }
}
