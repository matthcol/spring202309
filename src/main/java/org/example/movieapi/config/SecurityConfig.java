package org.example.movieapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

// https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html

@Configuration
//@EnableWebSecurity
//@EnableWebMvc
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,  HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        return httpSecurity
                // CORS
                .cors(httpSecurityCorsConfigurer -> {})
                // CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // authorisations
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(
                                    mvcMatcherBuilder.pattern("/swagger-ui/**"),
                                    mvcMatcherBuilder.pattern("/v3/api-docs/**")
                            ).permitAll()
                            .requestMatchers(
                                    mvcMatcherBuilder.pattern(HttpMethod.GET,"/api/**")
                            ).hasRole("USER_ROLE")
                            .requestMatchers(
                                    mvcMatcherBuilder.pattern(HttpMethod.POST,"/api/**"),
                                    mvcMatcherBuilder.pattern(HttpMethod.PUT,"/api/**"),
                                    mvcMatcherBuilder.pattern(HttpMethod.PATCH,"/api/**"),
                                    mvcMatcherBuilder.pattern(HttpMethod.DELETE,"/api/**")
                            ).hasRole("ADMIN_ROLE")
                            .anyRequest().denyAll()
                )
                // authentication
                .httpBasic(httpSecurityHttpBasicConfigurer -> {})
                // sessions
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(STATELESS))
                .build();
    }

}
