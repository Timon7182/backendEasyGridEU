package com.timon.rhouse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new ImplUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/consumer/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/landlord/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/flat/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/landlord/**").permitAll()
                .antMatchers("/flat/**").permitAll()
                .antMatchers("/consumer/**").permitAll()
                .antMatchers("/geo/**").permitAll()
                .antMatchers("/geo/**").permitAll()
                .antMatchers("/photos/**").permitAll()
                .antMatchers("/addPicture.jsf").permitAll()
                .antMatchers("/WelcomePage.jsf").permitAll()
                .antMatchers("/login.jsf").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/registrationChoose.jsf").permitAll()
                .antMatchers("/registrationConsumer.jsf").permitAll()
                .antMatchers("/registrationLandlord.jsf").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.jsf").loginProcessingUrl("/login")
                .defaultSuccessUrl("/WelcomePage.jsf")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/WelcomePage.jsf")
//                .formLogin().permitAll()
//                .loginProcessingUrl("/doLogin")
                .and()
                .logout().permitAll();

        http.csrf().disable();

    }
}