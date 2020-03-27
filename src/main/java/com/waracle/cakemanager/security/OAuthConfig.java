package com.waracle.cakemanager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
* Author : Atul Kumar
* This class allow the request for Oauth2 authorization
* */
@Configuration
public class OAuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable().antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/")
                .permitAll().and()
                .authorizeRequests().antMatchers("/h2/**").permitAll()
                .anyRequest()
                .authenticated().and().oauth2Login().and().oauth2Client().and()
                .csrf().ignoringAntMatchers("/h2/**")
                .and().headers().frameOptions().sameOrigin();
    }

}
