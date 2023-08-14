package com.reggie.core.config;

import com.reggie.core.filter.CheckTokenFilter;
import com.reggie.core.modular.auth.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailService userDetailService;

    private CheckTokenFilter checkTokenFilter;

    public static final List<String> EXCLUDED_URL_LIST;

    static {
        EXCLUDED_URL_LIST = new ArrayList<>();
        EXCLUDED_URL_LIST.add("/doc.html");
        EXCLUDED_URL_LIST.add("/authModule/login/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.logout().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        for (String excludedUrl : EXCLUDED_URL_LIST) {
            http.authorizeRequests()
                .antMatchers(excludedUrl)
                .permitAll();
        }

        http.authorizeRequests()
            .anyRequest()
            .authenticated();

        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers()
            .frameOptions()
            .disable()
            .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/css/**", "/js/**")
           .and()
           .ignoring()
           .antMatchers()
           .and()
           .ignoring()
           .antMatchers(
                   "/doc.html",
                   "/webjars/**",
                   "/swagger-resources/**",
                   "/v2/api-docs/**");
    }
}
