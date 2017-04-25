package com.blibli.future.detroit.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/**")
                .fullyAuthenticated()
            .antMatchers("/api/v1/api-docs")
                .permitAll()
            .anyRequest()
                .permitAll()
            .and()
                .httpBasic()
            .and()
                .csrf().disable();
    }

}
