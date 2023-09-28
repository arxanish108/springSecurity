package com.newSecurity.conceptsSpring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // user information
    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("anish")
//                .password(encoder.encode("pwd1"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("roshan")
//                .password(encoder.encode("pwd2"))
//                .roles("USER", "ADMIN", "HR")
//                .build();
//


          return new UserInfoUserDetailsService();
//        return new InMemoryUserDetailsManager(admin, user);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/home/welcome","home/user/add").permitAll()
                                .requestMatchers("/home/**").authenticated()
                )
                .formLogin(withDefaults()); // This configures form-based login

        // Disable CSRF protection for simplicity. You may want to enable it in a production environment.
       // http.csrf().disable();
        http.csrf((csrf) -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

}
