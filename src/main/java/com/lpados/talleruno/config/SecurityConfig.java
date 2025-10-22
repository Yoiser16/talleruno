package com.lpados.talleruno.config;
import org.springframework.http.HttpMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            
            .csrf(csrf -> {})
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/usuarios", "/usuarios/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/consulta-usuarios", "/consulta-usuarios/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/libros/create", "/libros/edit/**", "/libros/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/libros").authenticated()
                .requestMatchers("/prestamos/create").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                .requestMatchers("/prestamos/edit/**", "/prestamos/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/prestamos").authenticated()
                
                .requestMatchers(HttpMethod.GET, "/api/usuarios", "/api/usuarios/").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                
                .requestMatchers(HttpMethod.GET, "/api/libros", "/api/libros/all", "/api/libros/disponibles").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/libros/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/libros/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/libros/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/libros/**").hasAuthority("ROLE_ADMIN")
                
                .requestMatchers(HttpMethod.POST, "/api/prestamos/crear", "/api/prestamos").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/api/prestamos/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/prestamos/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/prestamos", "/api/prestamos/**").authenticated()
                
                .requestMatchers("/", "/index").authenticated()
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")
            );
        
        return http.build();
    }
}
