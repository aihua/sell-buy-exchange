package com.exchange.buy.sell.market.controller;

import com.exchange.buy.sell.market.domain.ImageEntity;
import com.exchange.buy.sell.market.domain.ProductEntity;
import com.exchange.buy.sell.market.repository.ProductRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by oleht on 14.05.2018
 */
@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("product/{id}/main-image")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        Optional<ProductEntity> productImageEntity = productRepository.findById(Long.valueOf(id));
        if (productImageEntity.isPresent()) {
            ImageEntity mainImage = productImageEntity.get().getProductImages().get(0);
            Byte[] image = mainImage.getImage();
            byte[] byteArray = new byte[image.length];
            int i = 0;

            for (Byte wrappedByte : image) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
