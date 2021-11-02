package com.example.demo.web.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	private FamilyAuthenticationSuccessHandler familyAuthenticationSuccessHandler;
	
	@Autowired
	private FamilyAuthenticationFailHandler familyAuthenticationFailHandler;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/static/**").permitAll().anyRequest().fullyAuthenticated().and().authorizeRequests()
        .and()
        .csrf().disable()
        .formLogin().loginPage("/sys/sign-in").permitAll().loginProcessingUrl("/login").permitAll().defaultSuccessUrl("/").permitAll()
        .failureUrl("/sys/sig-in").permitAll()
        .successHandler(familyAuthenticationSuccessHandler)
        .failureHandler(familyAuthenticationFailHandler);

    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/sys/wake_up", "/sys/sign-in", "/line/**","/js/**", "/fonts/**", "/css/**", "/image/**", "/images/**", "/**/favicon.ico", "/styles/**", "/vendors/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}


}
