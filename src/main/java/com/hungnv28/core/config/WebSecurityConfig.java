package com.hungnv28.core.config;

import com.hungnv28.core.components.ApiAuthenticationEntryPoint;
import com.hungnv28.core.components.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${sys.api.v1}")
    private String apiV1;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    ApiAuthenticationEntryPoint authenticationEntryPoint;


    private String getPattern(String prefixApi, String pattern) {
        return String.format("%s%s", prefixApi, pattern);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, getPattern(apiV1, "/actuator/**")).permitAll()

                        .requestMatchers(HttpMethod.POST, getPattern(apiV1, "/auth/sign-in")).permitAll()
                        .requestMatchers(HttpMethod.POST, getPattern(apiV1, "/auth/sign-up")).permitAll()

                        .anyRequest().authenticated()
                ).exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }
}
