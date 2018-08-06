package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.domain.VerificationToken;
import com.exchange.buy.sell.market.dto.UserDto;
import com.exchange.buy.sell.registration.exception.EmailExistsException;

/**
 * Created by oleht on 13.07.2018
 */
public interface UserService {
    UserEntity registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;

    UserEntity getUser(String verificationToken);

    void saveRegisteredUser(UserEntity user);

    void createVerificationToken(UserEntity user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    String validateVerificationToken(String token);

    void createPasswordResetTokenForUser(UserEntity user, String token);

    UserEntity findUserByEmail(String email);

    void changeUserPassword(UserEntity user, String password);

    boolean checkIfValidOldPassword(UserEntity user, String password);
}
