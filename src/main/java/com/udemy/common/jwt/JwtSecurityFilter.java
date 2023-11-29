package com.udemy.common.jwt;

import com.udemy.exceptions.PhoneNumberIsNotVerified;
import com.udemy.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (StringUtils.isBlank(header) || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(7);
            Claims claims = jwtUtils.claims(token);
            String phoneNumber = claims.getSubject();

            System.out.println(request.getRequestURI());
            boolean goingForVerification = request.getRequestURI().contains("sms");

            UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
            User user = (User) userDetails;

            if (user.isPhoneNumberVerified() || goingForVerification) {
                var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.getWriter().write("User registration accepted. " +
                        "Please verify your phone! " +
                        "Path: /api/v1/auth/verify/phone");
                return;
            }


        } catch (PhoneNumberIsNotVerified e) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("User registration accepted. Please verify your phone! Path: /api/v1/auth/verify-phone");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            filterChain.doFilter(request, response);
        }

    }
}
