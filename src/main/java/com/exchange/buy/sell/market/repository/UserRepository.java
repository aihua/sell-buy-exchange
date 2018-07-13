package com.exchange.buy.sell.market.repository;

import com.exchange.buy.sell.market.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by oleht on 13.07.2018
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
