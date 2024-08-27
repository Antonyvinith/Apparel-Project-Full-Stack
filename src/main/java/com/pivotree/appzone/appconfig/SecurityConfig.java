package com.pivotree.appzone.appconfig;

import com.pivotree.appzone.exception.CustomAccessDeniedHandler;
import com.pivotree.appzone.filter.JwtAuthenticationFilter;
import com.pivotree.appzone.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(
                                req->req.requestMatchers("/user/**","/customer/addCustomer").permitAll()

                                        .requestMatchers("/addCategory","/updateCategory/**").hasAuthority("ADMIN")
                                        .requestMatchers("/allCategory","/categorySearch").hasAnyAuthority("ADMIN","CUSTOMER")

                                        .requestMatchers("/customer/get-by-username",
                                                "/customer/update-by-username","/customer/upload-profile-image",
                                                "/customer/update-profile-picture",
                                                "/customer/profile-image").hasAnyAuthority("ADMIN","CUSTOMER")
                                        .requestMatchers("/customer/getAll").hasAuthority("ADMIN")

                                        .requestMatchers("/fulfillment/**").hasAuthority("ADMIN")

                                        .requestMatchers("/inventory/**").hasAuthority("ADMIN")

                                        .requestMatchers("/location/**").hasAuthority("ADMIN")

                                        .requestMatchers("/order/createOrder").hasAuthority("CUSTOMER")
                                        .requestMatchers("/order/getAll","order/CC","order/HD").hasAuthority("ADMIN")
                                        .requestMatchers("/order/getOrdersByCustomer").hasAnyAuthority("ADMIN","CUSTOMER")

                                        .requestMatchers("/product/create").hasAuthority("ADMIN")
                                        .requestMatchers("/product/getAll","/product/products","/product/productRef",
                                                "/product/productsByPriceAndCategory").hasAnyAuthority("CUSTOMER","ADMIN")

                                        .requestMatchers("/addresses/**").hasAuthority("CUSTOMER")

                                        .requestMatchers("/cards/**").hasAuthority("CUSTOMER")

                                        .anyRequest().authenticated()
                        )
                        .userDetailsService(userDetailsServiceImp)
                        .exceptionHandling(e->e.accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                        .sessionManagement(session->session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }



}
