package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.dto.UserDto;
import com.exchange.buy.sell.market.exception.EmailExistsException;
import com.exchange.buy.sell.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by oleht on 13.07.2018
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public UserEntity registerNewUserAccount(UserDto accountDto) throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:" + accountDto.getEmail());
        }
        UserEntity user = new UserEntity();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRoles(Collections.singletonList("ROLE_USER"));
        return repository.save(user);
    }

    private boolean emailExist(String email) {
        UserEntity user = repository.findByEmail(email);
        return user != null;
    }
}
