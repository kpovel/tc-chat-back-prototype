package com.example.demo.sequrity;

import com.example.demo.sequrity.jwt.AuthEntryPointJwt;
import com.example.demo.sequrity.jwt.AuthTokenFilter;
import com.example.demo.servise.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
@ComponentScan(basePackages = "com.example.demo")
public class SecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;

    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public SecurityConfig(AuthEntryPointJwt unauthorizedHandler, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsServiceImpl = userDetailsServiceImpl;

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }


    public static final String[] ENDPOINTS_WHITELIST = {
            "/api/signup",
            "/api/login",
            "/"

    };
//    public static final String LOGIN_URL = "/api/login";
    //    public static final String LOGOUT_URL = "/logout";
//    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";
//    public static final String DEFAULT_SUCCESS_URL = "/security-page";
//    public static final String USERNAME = "username";
//    public static final String PASSWORD = "password";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/signup").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )

        .authenticationProvider(authenticationProvider())

       .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
//                        .anyRequest().authenticated()
//                )
//
        return http.build();
    }
}
