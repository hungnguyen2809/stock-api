package com.hungnv28.core.filters;

import com.hungnv28.core.models.UserInfo;
import com.hungnv28.core.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String BEARER = "Bearer";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (hasJwtToken(request)) {
                UserInfo userInfo = validateToken(request);
                setUpSpringAuthentication(userInfo);
            } else {
                SecurityContextHolder.clearContext();
            }

            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private void setUpSpringAuthentication(UserInfo userInfo) {
        if (userInfo != null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }
    }

    private String getHeader(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    private boolean hasJwtToken(HttpServletRequest request) {
        String authHeader = getHeader(request);
        return StringUtils.isNotEmpty(authHeader);
    }

    private UserInfo validateToken(HttpServletRequest request) {
        String token = getHeader(request);
        token = token
                .replace(BEARER, "")
                .replace(BEARER.toLowerCase(), "")
                .replace(BEARER.toUpperCase(), "")
                .trim();

        return JwtTokenUtil.verifyToken(token);
    }
}
