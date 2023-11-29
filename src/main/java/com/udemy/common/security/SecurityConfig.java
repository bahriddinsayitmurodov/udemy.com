package com.udemy.common.security;

import com.udemy.common.jwt.JwtAuthenticationEntrypoint;
import com.udemy.common.jwt.JwtSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {

    private final JwtAuthenticationEntrypoint entrypoint;
    private final String[] WHITE_LIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/auth/register",
            "/api/***"
    };
    @Bean
    public SecurityFilterChain security(HttpSecurity security,JwtSecurityFilter jwtSecurityFilter) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.cors(AbstractHttpConfigurer::disable);

        security.authorizeHttpRequests(request -> request
                .requestMatchers( WHITE_LIST)
                .permitAll()
                .anyRequest()
                .fullyAuthenticated());

        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        security.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        security.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(entrypoint));
        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}
