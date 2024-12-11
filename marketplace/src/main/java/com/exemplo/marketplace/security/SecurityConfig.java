package com.exemplo.marketplace.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Desabilita CSRF para simplificação (use com cuidado em produção)
            .authorizeRequests()
                .requestMatchers("/users/register").permitAll()  // Permite o registro e login sem autenticação
                .requestMatchers("/products/register").hasRole("SELLER")  // Apenas SELLER pode cadastrar produtos
                .requestMatchers("/products/**").hasRole("BUYER")  // BUYER pode visualizar produtos
                .anyRequest().authenticated()  // Qualquer outra requisição precisa estar autenticada
            .and()
            .formLogin().disable()  // Desabilita o login via formulário (será feito via API)
            .httpBasic()  // Habilita autenticação básica via HTTP
            .and()
            .logout()
                .permitAll();  // Permite logout para todos os usuários

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(16384, 8, 1, 32, 64);  // Configuração do SCryptPasswordEncoder
    }
}
