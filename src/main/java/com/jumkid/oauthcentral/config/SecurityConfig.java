package com.jumkid.oauthcentral.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/static/**")
                .permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                    .accessDeniedPage("/accessDenied")
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
            .and()
                .formLogin()
                .defaultSuccessUrl("/admin.html")
                .permitAll()
            .and()
                .csrf().disable();  // enable this if the authorization service exposure to public
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String userQuery = "SELECT username, password, active AS enabled FROM users as u WHERE u.username = ?";
        String authorityQuery = "SELECT username, role AS authority FROM authorities as a WHERE a.username = ?";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(authorityQuery)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/status", "/webjars/**", "/swagger-ui.html", "/swagger-resources/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
