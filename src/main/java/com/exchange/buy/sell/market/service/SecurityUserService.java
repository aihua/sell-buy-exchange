package com.exchange.buy.sell.market.service;

/**
 * Created by oleht on 22.07.2018
 */
public interface SecurityUserService {
    String validatePasswordResetToken(long id, String token);
}
