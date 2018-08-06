package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.domain.PasswordResetToken;
import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by oleht on 22.07.2018
 */
@Service
@Transactional
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        UserEntity user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
