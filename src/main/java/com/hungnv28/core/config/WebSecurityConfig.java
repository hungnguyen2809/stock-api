package com.hungnv28.core.config;

import com.hungnv28.core.exception.CodeException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.filters.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
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

    private AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setError(true);
            errorResponse.setData("OK");
            errorResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            errorResponse.setCode(CodeException.TOKEN_EXPIRES.getValue());
            errorResponse.setMessage("Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new JSONObject(errorResponse).toString());
        };
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

                        .anyRequest().authenticated()
                ).exceptionHandling(exception -> exception.authenticationEntryPoint(getAuthenticationEntryPoint()));

        return http.build();
    }
}
