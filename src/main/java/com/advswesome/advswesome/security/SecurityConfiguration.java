package com.advswesome.advswesome.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import com.advswesome.advswesome.repository.document.Role;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    // private final LogoutHandler logoutHandler; 

    @Autowired
    public SecurityConfiguration(   
        JwtAuthenticationFilter jwtAuthFilter, 
        AuthenticationProvider authenticationProvider
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        // this.logoutHandler = logoutHandler;
    }

    // endpoints that are accessible to all 
    private static final String[] WHITE_LIST_URL = {
        "/clients/register",
        "/users/auth/register",
        "/users/auth/login"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req.requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                    .requestMatchers(POST, "/profiles").permitAll() // hasAnyAuthority("INDIVIDUAL") 
                    .requestMatchers(GET, "/profiles/**").permitAll() // .hasAnyRole(INDIVIDUAL.name()) 
                    .requestMatchers(PUT, "/profiles/**").permitAll() //.hasAnyRole(INDIVIDUAL.name()) 
                    .requestMatchers(DELETE, "/profiles/**").permitAll() //.hasAnyRole(INDIVIDUAL.name()) 
                    .requestMatchers(POST, "/prescriptions").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(GET, "/prescriptions/**").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(PUT, "/prescriptions/**").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(DELETE, "/prescriptions/**").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(POST, "/consents").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(GET, "/consents/**").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(PUT, "/consents/**").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers(DELETE, "/consents/**").permitAll() //.hasAnyRole(INDIVIDUAL.name())
                    .requestMatchers("/analytics/**").permitAll() //.hasAnyRole(ORGANIZATION.name())
                    .anyRequest()
                    .authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            // .logout(logout ->
            //     logout.logoutUrl("/api/v1/auth/logout")
            //         .addLogoutHandler(logoutHandler)
            //         .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
            // )
        ;

        return http.build();
    }

}