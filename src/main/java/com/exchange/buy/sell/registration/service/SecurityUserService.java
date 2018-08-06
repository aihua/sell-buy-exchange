package com.exchange.buy.sell.registration.service;

/**
 * Created by oleht on 22.07.2018
 */
public interface SecurityUserService {
    String validatePasswordResetToken(long id, String token);
}
