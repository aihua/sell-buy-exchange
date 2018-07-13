package com.exchange.buy.sell.market.repository;

import com.exchange.buy.sell.market.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by oleh.tsyupaon 6/13/17.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {



}
