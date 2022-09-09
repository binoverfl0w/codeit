package org.example.codeit.app.security;

import org.example.codeit.app.rest.RestAuthenticationEntryPoint;
import org.example.codeit.app.security.jwt.JWTTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {
    private final UserDetailsService userService;
    private final JWTTokenFilter jwtTokenFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(UserDetailsService userService, JWTTokenFilter jwtTokenFilter, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.userService = userService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.sendError(
//                                    HttpServletResponse.SC_UNAUTHORIZED,
//                                    ex.getMessage()
//                            );
//                        }
//                )
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and();

        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()
//                .mvcMatchers(HttpMethod.GET,"/").permitAll()
//                .mvcMatchers(HttpMethod.POST,"/oauth/token").permitAll()
//                .mvcMatchers(HttpMethod.POST,"/users").permitAll()
//                .mvcMatchers("/**").authenticated()
        );

        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}