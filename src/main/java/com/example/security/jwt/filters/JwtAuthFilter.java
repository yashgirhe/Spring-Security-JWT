package com.example.security.jwt.filters;

import com.example.security.jwt.entity.User;
import com.example.security.jwt.service.JwtService;
import com.example.security.jwt.service.UserService;
import com.example.security.jwt.utils.BlackListToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final BlackListToken blackListToken;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            //fetch accessToken from request header
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = requestTokenHeader.substring(7);
            if (blackListToken.isBlacklisted(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            Long userId = jwtService.getUserIdFromToken(accessToken);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                //pass additional information like - IP address and sessionId
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //set user authentication accessToken in security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            //pass on to next filter
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            //errors in security filter chain aren't automatically handled by spring security so
            //handlerExceptionResolver is used
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
