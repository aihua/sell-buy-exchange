package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by oleht on 08.05.2018
 */
public interface ProductService {

    void saveProduct();

    List<ProductDto> getAllProducts();

    Page<ProductDto> findPaginated(int page, int size);
}
