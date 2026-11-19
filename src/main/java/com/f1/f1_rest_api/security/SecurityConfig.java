package com.f1.f1_rest_api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    static final String API_POLICY = "default-src 'none'; frame-ancestors 'none'";
    static final String SWAGGER_UI_POLICY = "default-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; frame-ancestors 'none'";

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerUiFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/swagger-ui/**", "/swagger-ui.html")
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .headers(headers -> headers.contentSecurityPolicy(csp -> csp.policyDirectives(SWAGGER_UI_POLICY)))
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/f1", "/f1/**").permitAll()
                        .requestMatchers(HttpMethod.HEAD, "/f1", "/f1/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/f1/**").hasRole("FIA_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/f1/**").hasRole("FIA_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/f1/**").hasAnyRole("FIA_ADMIN", "TEAM_PRINCIPAL")
                        .requestMatchers(HttpMethod.PATCH, "/f1/**").hasAnyRole("FIA_ADMIN", "TEAM_PRINCIPAL")
                        .anyRequest().permitAll())
                .httpBasic(basic -> basic.authenticationEntryPoint(SecurityConfig::unauthorized))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(SecurityConfig::unauthorized)
                        .accessDeniedHandler(SecurityConfig::forbidden))
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.contentSecurityPolicy(csp -> csp.policyDirectives(API_POLICY)))
                .build();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("fia_director").password("{noop}fia2026").roles("FIA_ADMIN").build(),
                User.withUsername("andrea_stella").password("{noop}mclaren2026").roles("TEAM_PRINCIPAL").build(),
                User.withUsername("christian_horner").password("{noop}redbull2026").roles("TEAM_PRINCIPAL").build(),
                User.withUsername("fred_vasseur").password("{noop}ferrari2026").roles("TEAM_PRINCIPAL").build(),
                User.withUsername("f1_fan").password("{noop}fan2026").roles("VIEWER").build()
        );
    }

    private static void unauthorized(HttpServletRequest request, HttpServletResponse response,
                                     AuthenticationException exception) throws IOException {
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"F1 REST API\"");
        writeProblem(request, response, HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    private static void forbidden(HttpServletRequest request, HttpServletResponse response,
                                  AccessDeniedException exception) throws IOException {
        writeProblem(request, response, HttpStatus.FORBIDDEN,
                "You do not have permission to perform this operation.");
    }

    private static void writeProblem(HttpServletRequest request, HttpServletResponse response,
                                     HttpStatus status, String detail) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("""
                {
                  "title": "%s",
                  "status": %d,
                  "detail": "%s",
                  "instance": "%s"
                }
                """.formatted(
                escapeJson(status.getReasonPhrase()),
                status.value(),
                escapeJson(detail),
                escapeJson(request.getRequestURI())));
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}