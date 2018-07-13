package com.exchange.buy.sell.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

/**
 * Created by oleht on 11.05.2018
 */
@Configuration
public class WebConfiguration {//extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        SecureRandom r = new SecureRandom();
        return new BCryptPasswordEncoder(31, r);
    }

}
