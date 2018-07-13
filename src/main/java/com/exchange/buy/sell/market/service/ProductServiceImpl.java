package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.convertor.ProductEntityToDtpConverter;
import com.exchange.buy.sell.market.domain.ProductEntity;
import com.exchange.buy.sell.market.dto.ProductDto;
import com.exchange.buy.sell.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleht on 08.05.2018
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductEntityToDtpConverter productEntityToDtpConverter;

    @Override
    public void saveProduct() {

    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> list = new ArrayList<>();
        productRepository.findAll().iterator().forEachRemaining(prod -> list.add(productEntityToDtpConverter.convert(prod)));
        return list;
    }

    @Override
    public Page<ProductDto> findPaginated(int page, int size) {
        Page<ProductEntity> entities = productRepository.findAll(PageRequest.of(page, size));
        return entities.map(productEntityToDtpConverter::convert);
    }

}
