package com.descentrilizedsynergy.supdem.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.csrf()
        // .disable()
        // .authorizeHttpRequests()
        // .requestMatchers("/api/producer/**", "/auth/**")
        // .permitAll()
        // .anyRequest()
        // .authenticated()
        // .and()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and()
        // .authenticationProvider(authenticationProvider)
        // .addFilterBefore(jwtAuthenticationFilter,
        // UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable().authorizeRequests().requestMatchers("/api/consumer/**",
                "/api/public/**")
                .permitAll();

        // http.authorizeHttpRequests(
        // request -> request.requestMatchers(new
        // AntPathRequestMatcher("/api/producer/profile"))
        // .permitAll())
        // .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();

    // configuration.setAllowedOrigins(List.of("http://localhost:8005"));
    // configuration.setAllowedMethods(List.of("GET", "POST"));
    // configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();

    // source.registerCorsConfiguration("/**", configuration);

    // return source;
    // }
}
