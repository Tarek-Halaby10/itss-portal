// SecurityConfig.java - Place in: itss-auth-module/src/main/java/com/itss/auth/config/
package com.itss.auth.config;

import com.itss.auth.security.JwtAuthenticationEntryPoint;
import com.itss.auth.security.JwtAuthenticationFilter;
import com.itss.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/auth/refresh").permitAll()
                .requestMatchers("/api/auth/forgot-password").permitAll()
                .requestMatchers("/api/auth/reset-password").permitAll()
                .requestMatchers("/api/invitations/*/accept").permitAll()
                
                // Swagger/OpenAPI endpoints
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                
                // Health check
                .requestMatchers("/actuator/health").permitAll()
                
                // User management endpoints
                .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("USER_VIEW")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("USER_VIEW")
                .requestMatchers(HttpMethod.POST, "/api/users").hasAuthority("USER_CREATE")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("USER_UPDATE")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("USER_DELETE")
                .requestMatchers(HttpMethod.POST, "/api/users/*/approve").hasAuthority("USER_APPROVE")
                .requestMatchers(HttpMethod.POST, "/api/users/*/block").hasAuthority("USER_BLOCK")
                
                // Role management endpoints
                .requestMatchers(HttpMethod.GET, "/api/roles").hasAuthority("ROLE_VIEW")
                .requestMatchers(HttpMethod.GET, "/api/roles/**").hasAuthority("ROLE_VIEW")
                .requestMatchers(HttpMethod.POST, "/api/roles").hasAuthority("ROLE_CREATE")
                .requestMatchers(HttpMethod.PUT, "/api/roles/**").hasAuthority("ROLE_UPDATE")
                .requestMatchers(HttpMethod.DELETE, "/api/roles/**").hasAuthority("ROLE_DELETE")
                .requestMatchers(HttpMethod.POST, "/api/users/*/roles").hasAuthority("ROLE_ASSIGN")
                
                // Permission management endpoints
                .requestMatchers(HttpMethod.GET, "/api/permissions").hasAuthority("PERMISSION_VIEW")
                .requestMatchers(HttpMethod.GET, "/api/permissions/**").hasAuthority("PERMISSION_VIEW")
                .requestMatchers(HttpMethod.PUT, "/api/roles/*/permissions").hasAuthority("PERMISSION_ASSIGN")
                
                // Invitation endpoints
                .requestMatchers(HttpMethod.POST, "/api/invitations").hasAuthority("INVITATION_SEND")
                .requestMatchers(HttpMethod.GET, "/api/invitations").hasAuthority("INVITATION_VIEW")
                .requestMatchers(HttpMethod.GET, "/api/invitations/**").hasAuthority("INVITATION_VIEW")
                .requestMatchers(HttpMethod.DELETE, "/api/invitations/**").hasAuthority("INVITATION_CANCEL")
                
                // Activity endpoints
                .requestMatchers(HttpMethod.GET, "/api/activities").hasAuthority("ACTIVITY_VIEW")
                .requestMatchers(HttpMethod.GET, "/api/activities/**").hasAuthority("ACTIVITY_VIEW")
                
                // Dashboard endpoints
                .requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAuthority("DASHBOARD_VIEW")
                
                // All other requests must be authenticated
                .anyRequest().authenticated()
            );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}