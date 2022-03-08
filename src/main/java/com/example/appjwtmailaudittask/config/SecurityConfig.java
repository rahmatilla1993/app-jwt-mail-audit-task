package com.example.appjwtmailaudittask.config;

import com.example.appjwtmailaudittask.enums.Permission;
import com.example.appjwtmailaudittask.enums.RoleName;
import com.example.appjwtmailaudittask.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/home").permitAll()
                .antMatchers("/api/manager/crud/**").hasAuthority(Permission.MANAGER_CRUD.getPermission())
                .antMatchers("/api/manager/task/**").hasAuthority(Permission.TASK_FOR_MANAGER.getPermission())
                .antMatchers("/api/manager/verifyEmail", "/api/manager/login").hasRole(RoleName.MANAGER.name())
                .antMatchers("/api/employee/crud/**").hasAuthority(Permission.EMPLOYEE_CRUD.getPermission())
                .antMatchers("/api/employee/task/*", "/api/employee/addSalary/*").hasRole(RoleName.MANAGER.name())
                .antMatchers("/api/employee/login", "/api/employee/verifyEmail").hasRole(RoleName.EMPLOYEE.name())
                .antMatchers("/api/management/**").hasAnyRole(RoleName.DIRECTOR.name(), RoleName.HR_MANAGER.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
