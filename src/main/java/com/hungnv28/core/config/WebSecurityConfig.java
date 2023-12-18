package com.hungnv28.core.config;

import com.hungnv28.core.enums.RoleUser;
import com.hungnv28.core.filters.JwtTokenFilter;
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

    private String getPattern(String prefixApi, String pattern) {
        return String.format("%s%s", prefixApi, pattern);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, getPattern(apiV1, "/actuator/**")).permitAll()

                        .requestMatchers(HttpMethod.GET, getPattern(apiV1, "/auth/**")).permitAll()
                        .requestMatchers(HttpMethod.POST, getPattern(apiV1, "/auth/**")).permitAll()

                        .requestMatchers(HttpMethod.GET, getPattern(apiV1, "/user/**")).permitAll()
                        .requestMatchers(HttpMethod.POST, getPattern(apiV1, "/user/**")).permitAll()

                        .requestMatchers(HttpMethod.GET, getPattern(apiV1, "/admin/**")).hasAnyRole(RoleUser.ADMIN.getValue())
                        .requestMatchers(HttpMethod.POST, getPattern(apiV1, "/admin/**")).hasAnyRole(RoleUser.ADMIN.getValue())

                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
