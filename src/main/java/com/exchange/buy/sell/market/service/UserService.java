package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.dto.UserDto;
import com.exchange.buy.sell.market.exception.EmailExistsException;

/**
 * Created by oleht on 13.07.2018
 */
public interface UserService {
    UserEntity registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;
}
