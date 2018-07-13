package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.domain.ImageEntity;
import com.exchange.buy.sell.market.domain.ProductEntity;
import com.exchange.buy.sell.market.repository.ProductImageRepository;
import com.exchange.buy.sell.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by oleht on 11.05.2018
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public void saveImageFile(Long productId, MultipartFile file) {

        try {
            ProductEntity product = productRepository.findById(productId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            ImageEntity image = new ImageEntity();
            image.setImage(byteObjects);
            productImageRepository.save(image);

            product.addImages(image);
            productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
