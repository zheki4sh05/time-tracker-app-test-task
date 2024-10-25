package com.timetracker.timetrackerapptesttask;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.role.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

import java.util.*;

@SpringBootApplication
@EnableWebSecurity
public class TimeTrackerAppTestTaskApplication {

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(TimeTrackerAppTestTaskApplication.class, args);
    }


    @Bean
    public UserDetailsService userDetailsService(){

        return (email) -> {

            com.timetracker.timetrackerapptesttask.entity.User user =  repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            if(Objects.equals(user.getRolePriority(), RoleDto.ADMIN.getPriority())){
                return new UserRole(RoleDto.ADMIN,user);
            }else{
                return new UserRole(RoleDto.USER,user);
            }

        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());

        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
