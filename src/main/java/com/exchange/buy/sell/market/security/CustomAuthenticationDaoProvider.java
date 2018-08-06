package com.exchange.buy.sell.market.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;

/**
 * Created by oleht on 25.07.2018
 */
@Component
public class CustomAuthenticationDaoProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(encoder());
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        if (super.getUserDetailsService() != null) {
            System.out.println("UserDetailsService has been configured properly");
        }
    }

    @Bean
    public PasswordEncoder encoder() {
        SecureRandom r = new SecureRandom();
        return new BCryptPasswordEncoder(31, r);
    }
}
