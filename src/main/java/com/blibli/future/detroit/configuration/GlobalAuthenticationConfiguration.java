package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.repository.UserRepository;
import com.blibli.future.detroit.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Configuration
class GlobalAuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                com.blibli.future.detroit.model.User user = userRepository.findByEmail(email);
                List<UserRole> userRoleList = userRoleRepository.findByEmail(email);

                ArrayList<GrantedAuthority> authorities = new ArrayList();
                for (UserRole userRole : userRoleList) {
                    authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
                    System.out.println(userRole.getRole().toString());
                }

                if(user != null) {
                    return new User(user.getEmail(), user.getPassword(),
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
