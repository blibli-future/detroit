package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.controller.api.CutOffController;
import com.blibli.future.detroit.model.enums.UserType;
import org.apache.catalina.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Profile(value = {"development", "production"})
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(CutOffController.GET_ALL_CUTOFF_HISTORY)
                .hasRole(UserType.SUPER_ADMIN.toString())
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
