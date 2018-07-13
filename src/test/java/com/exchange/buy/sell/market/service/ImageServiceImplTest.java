package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.BasicTests;
import com.exchange.buy.sell.market.domain.ProductEntity;
import com.exchange.buy.sell.market.domain.ImageEntity;
import com.exchange.buy.sell.market.domain.category.Gender;
import com.exchange.buy.sell.market.repository.ProductImageRepository;
import com.exchange.buy.sell.market.repository.ProductRepository;
import com.exchange.buy.sell.market.utils.ImageLoadUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by oleht on 09.05.2018
 */
public class ImageServiceImplTest extends BasicTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Test
    public void saveImageTest() throws IOException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setGender(Gender.MALE);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImage(ImageLoadUtil.getTestImg("img/kurtka_l_1.jpg"));

        productEntity.addImages(imageEntity);

        productRepository.save(productEntity);
        assertEquals(productEntity.getGender(), productRepository.findAll().iterator().next().getGender());
        assertTrue("Can not found product in database, with id: " + productEntity.getId(), productRepository.findById(productEntity.getId()).isPresent());

        System.out.println(productEntity.getId());
        System.out.println(imageEntity.getId());

        assertEquals(imageEntity.getId(), productImageRepository.findById(imageEntity.getId()).get().getId());
    }

}