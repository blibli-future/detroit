//package com.blibli.future.detroit.configuration;
//
//import com.blibli.future.detroit.model.UserRole;
//import com.blibli.future.detroit.repository.UserRepository;
//import com.blibli.future.detroit.repository.UserRoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//class GlobalAuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserRoleRepository userRoleRepository;
//}
