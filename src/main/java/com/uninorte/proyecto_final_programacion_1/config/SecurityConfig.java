package com.uninorte.proyecto_final_programacion_1.config;

import com.uninorte.proyecto_final_programacion_1.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso a recursos estáticos (CSS, JS, lib/bootstrap, lib/fontawesome)
                .requestMatchers("/css/**", "/js/**", "/lib/**", "/images/**", "/webjars/**").permitAll()
                // Permitir acceso a la página de inicio y a las rutas de autenticación
                .requestMatchers("/", "/index", "/login", "/register", "/auth/register").permitAll()
                // Todas las demás peticiones requieren autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Especifica la página de login personalizada
                .defaultSuccessUrl("/productos", true) // <--- ¡CAMBIO CLAVE AQUÍ! Redirige a /productos después del login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL para cerrar sesión
                .logoutSuccessUrl("/login?logout") // Redirige a /login después de cerrar sesión
                .permitAll()
            );
        return http.build();
    }
}