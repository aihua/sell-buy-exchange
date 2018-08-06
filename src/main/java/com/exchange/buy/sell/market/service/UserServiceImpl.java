package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.domain.PasswordResetToken;
import com.exchange.buy.sell.market.domain.Role;
import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.domain.VerificationToken;
import com.exchange.buy.sell.market.dto.UserDto;
import com.exchange.buy.sell.registration.exception.EmailExistsException;
import com.exchange.buy.sell.registration.persistence.repository.PasswordResetTokenRepository;
import com.exchange.buy.sell.market.repository.UserRepository;
import com.exchange.buy.sell.registration.persistence.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by oleht on 13.07.2018
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    private final String passwordPaper = "AQwe#109*";

    @Autowired
    private UserRepository repository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public UserEntity registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + accountDto.getEmail());
        }
        UserEntity user = new UserEntity();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());

        user.setPassword(passwordEncoder.encode(accountDto.getPassword() + passwordPaper));

        user.setEmail(accountDto.getEmail());
        user.setRoles(Collections.singleton(new Role("d")));
        return repository.save(user);
    }

    private boolean emailExist(String email) {
        UserEntity user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public UserEntity getUser(String verificationToken) {
        UserEntity user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void saveRegisteredUser(UserEntity user) {
        repository.save(user);
    }

    @Override
    public void createVerificationToken(UserEntity user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final UserEntity user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        repository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public void createPasswordResetTokenForUser(final UserEntity user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public UserEntity findUserByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void changeUserPassword(final UserEntity user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final UserEntity user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

}

