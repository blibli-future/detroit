package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.repository.UserRepository;
import com.blibli.future.detroit.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
        http.authorizeRequests()
//            .antMatchers(CutOffController.GET_ALL_CUTOFF_HISTORY)
//            .antMatchers("/api/v1/cutoff**")
//                .hasAnyRole("ROLE_" + UserType.SUPER_ADMIN.toString())
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
