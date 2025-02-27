package com.tcc2.nutri_app_backend.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:9000")); // Permite o frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/patients/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/nutritionists/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/menus/{menuId}/meals").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/menus/meals/{foodID}/substitutions").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/guidelines/patients/{patientId}").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/documents/patients/{patientId}").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/documents/{id}").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/waterGoals/{id}").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/forms/patients/{patientId}").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.PUT, "/forms/questions/answers").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/dietEntries/patients/{patientId}").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.GET, "/dietEntries/{id}").hasRole("PATIENT")
                        .anyRequest().hasRole("NUTRITIONIST")
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
