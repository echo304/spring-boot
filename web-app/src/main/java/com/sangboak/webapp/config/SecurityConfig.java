package com.sangboak.webapp.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, order = 1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final private UserDetailsService accountService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/boards/{\\d+}/posts/{\\d+}/edit").authenticated()
                .antMatchers("/boards/{\\d+}/posts/create").authenticated()
                .antMatchers(HttpMethod.DELETE, "/boards/{\\d+}/posts/{\\d+}").authenticated()
                .antMatchers(HttpMethod.PUT, "/boards/{\\d+}/posts/{\\d+}").authenticated()
                .antMatchers(HttpMethod.POST, "/boards/{\\d+}/posts").authenticated()
                .antMatchers(HttpMethod.POST, "/posts/{\\d+}/replies").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/account/login/result")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
                .logoutSuccessUrl("/account/logout/result")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }
}

