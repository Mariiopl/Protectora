package com.protectora.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

        @Autowired
        private JwtFilter jwtFilter;

        // 🔹 Habilitar CORS globalmente para Angular
        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(CorsRegistry registry) {
                                registry.addMapping("/**") // todos los endpoints
                                                .allowedOrigins("http://localhost:4200") // origen de Angular
                                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                                .allowedHeaders("*")
                                                .allowCredentials(true);
                        }
                };
        }

        @Bean
        public AuthenticationManager authManager(HttpSecurity http,
                        PasswordEncoder passwordEncoder,
                        UserDetailsService userDetailsService) throws Exception {

                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder)
                                .and()
                                .build();
        }

        // @Bean
        // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
        // Exception {

        // http.cors() // ✅ habilitar CORS ANTES de csrf
        // .and()
        // .csrf().disable()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and()
        // .authorizeHttpRequests()
        // .requestMatchers("/auth/login", "/auth/register",
        // "/api/mascotas/**").permitAll()
        // .anyRequest().authenticated()
        // .and()
        // .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // return http.build();
        // }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors() // ✅ Habilita CORS usando tu WebMvcConfigurer
                                .and()
                                .csrf().disable()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .authorizeHttpRequests()
                                .anyRequest().permitAll(); // para pruebas, todo el mundo pasa
                return http.build();
        }
}
