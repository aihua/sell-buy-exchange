package com.exchange.buy.sell.market.config;

import com.exchange.buy.sell.registration.security.CustomAuthenticationDaoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by oleht on 13.07.2018
 */
@Configuration
@ComponentScan(basePackages = { "com.exchange.buy.sell", "com.exchange.buy.sell.registration.service" })
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationDaoProvider authenticationDaoProvider;

    public SecSecurityConfig() {
        super();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationDaoProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/updatePassword*",
                        "/user/savePassword*",
                        "/updatePassword*")
                .hasAuthority("CHANGE_PASSWORD_PRIVILEGE");
    }
}
