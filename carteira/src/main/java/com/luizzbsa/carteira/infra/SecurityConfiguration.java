package com.luizzbsa.carteira.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        private UserAuthenticationFilter userAuthenticationFilter;

        public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
                "/users/login",
                "/users",
                "/h2-console/",
                "/h2-console/**"
        };

        public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
                "/conta",
                "/transacoes"
        };



        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


            return httpSecurity.csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(request -> {
                        request.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll();

                        request.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated();
                        request.anyRequest().denyAll();
                    }).addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

}