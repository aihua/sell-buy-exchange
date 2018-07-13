package com.exchange.buy.sell.market.repository;

import com.exchange.buy.sell.market.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by oleh.tsyupaon 6/13/17.
 */
public interface ProductImageRepository extends JpaRepository<ImageEntity, Long> {

}
