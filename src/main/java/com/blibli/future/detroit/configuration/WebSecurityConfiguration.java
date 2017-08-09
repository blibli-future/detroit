package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.repository.UserRepository;
import com.blibli.future.detroit.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static com.blibli.future.detroit.controller.api.AgentChannelController.*;
import static com.blibli.future.detroit.controller.api.AgentPositionController.*;
import static com.blibli.future.detroit.controller.api.CategoryController.*;
import static com.blibli.future.detroit.controller.api.CutOffController.*;
import static com.blibli.future.detroit.controller.api.ExportController.GET_DATA_EXPORT;
import static com.blibli.future.detroit.controller.api.ParameterController.*;
import static com.blibli.future.detroit.controller.api.ReviewController.*;
import static com.blibli.future.detroit.controller.api.StatisticController.*;
import static com.blibli.future.detroit.controller.api.UserController.*;

@EnableWebSecurity
@Configuration
@Profile(value = {"development", "production"})
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService()).passwordEncoder(this.passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String REVIEWER = UserType.REVIEWER.toString();
        final String SUPER_ADMIN = UserType.SUPER_ADMIN.toString();
        final String AGENT = UserType.AGENT.toString();

        http.authorizeRequests()
            // Login has open permit, duh
            .antMatchers(HttpMethod.GET, "/api/v1/users/login")
                .fullyAuthenticated()

            // Reviewer is forbidden
            .antMatchers(HttpMethod.GET,
                // Review controller
                END_REVIEW_PERIOD, GET_DATA_EXPORT
            )
            .hasAuthority(SUPER_ADMIN)
            .antMatchers(HttpMethod.DELETE, GET_ALL_AGENT_CHANNEL + "/*", GET_ALL_AGENT_POSITION + "/*",
                CREATE_CATEGORY + "/*", GET_ALL_PARAMETER + "/*", DELETE_USER)
            .hasAuthority(SUPER_ADMIN)
            .antMatchers(HttpMethod.POST, CREATE_PARAMETER, CREATE_CATEGORY, CREATE_USER, CREATE_AGENT_CHANNEL,
                CREATE_AGENT_POSITION)
            .hasAuthority(SUPER_ADMIN)
            .antMatchers(HttpMethod.PATCH,
                "/api/v1/parameters/**", "/api/v1/parameters/**", "/api/v1/agent-positions/**",
                "/api/v1/agent-channels/**", "/api/v1/users/reviewer/**", "/api/v1/users/*")
            .hasAuthority(SUPER_ADMIN)
            .antMatchers(HttpMethod.PUT, "/api/v1/parameters/**")
            .hasAuthority(SUPER_ADMIN)

            // Agent is forbidden
            .antMatchers(HttpMethod.GET,
                // User controller
                GET_PARAMETER_ROLE_LIST, GET_ALL_USER,
                // Statistic controller
                GET_ALL_STATISTIC_DATA, GET_ALL_STATISTIC_INFO,
                // Review controller
                "/api/v1/reviews/**",
                // ParameterController
                GET_ALL_PARAMETER,
                // Cutoff Controller
                GET_ALL_CUTOFF_HISTORY, GET_CURRENT_CUTOFF_HISTORY
                )
                .hasAnyAuthority(REVIEWER, SUPER_ADMIN)
            .antMatchers(HttpMethod.POST, CREATE_AGENT, CREATE_REVIEW)
            .hasAnyAuthority(REVIEWER, SUPER_ADMIN)
            .antMatchers(HttpMethod.PATCH, "/api/v1/users/agent","/api/v1/reviews/**")
            .hasAnyAuthority(REVIEWER, SUPER_ADMIN)
            .antMatchers(HttpMethod.PUT, "/api/v1/reviews/**")
            .hasAnyAuthority(REVIEWER, SUPER_ADMIN)
            .antMatchers(HttpMethod.DELETE, "/api/v1/reviews/**")
            .hasAnyAuthority(REVIEWER, SUPER_ADMIN)

            // Agent only
            .antMatchers("/api/v1/statistic/agent-report/*")
            .hasAuthority(REVIEWER)

            // Anything else is free for all roles
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

    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                com.blibli.future.detroit.model.User user = userRepository.findByEmail(email);
                List<UserRole> userRoleList = userRoleRepository.findByEmail(email);

                ArrayList<GrantedAuthority> authorities = new ArrayList();
                for (UserRole userRole : userRoleList) {
                    authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
                }

                if(user != null) {
                    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        true, true, true, true,
                        authorities);
                } else {
                    throw new UsernameNotFoundException("Could not find the user '"
                        + email + "'");
                }
            }
        };
    }
}
