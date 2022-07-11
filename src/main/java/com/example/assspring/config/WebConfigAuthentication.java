package com.example.assspring.config;

import com.example.assspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebConfigAuthentication extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeHttpRequests()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/user").hasRole("ROLE_ADMIN")
                .antMatchers("/admin").hasAuthority("admin")
                .antMatchers("/profile").hasAnyAuthority("admin", "member")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/admin")
                .failureUrl("/error")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error");

    }
}
