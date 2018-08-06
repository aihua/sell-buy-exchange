package com.exchange.buy.sell.registration.repository;

import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by oleht on 13.07.2018
 */
public interface VerificationTokenRepository  extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(UserEntity user);
}
