package com.coding24h.campus_job.config;

import com.coding24h.campus_job.service.impl.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    private final SecurityService securityService;

    @Autowired
    public WebSecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    // 使用明文密码编码器（仅测试用）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/js/**",
                "/css/**",
                "/images/**",
                "/webjars/**",
                "/uploads/**"
        );
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityService);
        authProvider.setPasswordEncoder(passwordEncoder());  // 使用明文比对

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/", "/index", "/detail",
                                "/webjars/**",
                                "/register", "/toLogin",
                                "/public/**",
                                "/uploads/**",
                                "/login"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/upload-avatar").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/toLogin")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/toLogin?error")
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.accessDeniedPage("/noPermit"))
                .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}