package com.moriya.project_moriya_java.security;
//moriya.project_moriya_java
import com.moriya.project_moriya_java.security.jwt.AuthEntryPointJwt;
import com.moriya.project_moriya_java.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

//הגדרות אבטחה
@Configuration
@EnableMethodSecurity

public class WebSecurityConfig {
  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(8);
  }


  //********תפקיד הפונקציה:
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(request -> {
              CorsConfiguration corsConfiguration = new CorsConfiguration();
              corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
                corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
              corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
              corsConfiguration.setAllowCredentials(true);
              return corsConfiguration;
            })) .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
             // Disable CORS
            .authorizeHttpRequests((authorize) -> {
              authorize.requestMatchers("/h2-console/**").permitAll().
                      requestMatchers("/api/users/sign**").permitAll()
//                      requestMatchers("/sendMail").permitAll().
                      .requestMatchers("/api/users/updateUser/**").permitAll()
                      .requestMatchers("/api/users/getUser/**").permitAll()
                     .requestMatchers("/sendMail").permitAll()
                      .requestMatchers("/api/ads/getAllAds").permitAll().
                      requestMatchers("/api/users/login").permitAll();

              authorize.anyRequest().authenticated();
            })
            .httpBasic(Customizer.withDefaults());


 // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
    http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));

    //***********משמעות הגדרה זו:

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
