package com.exchange.buy.sell.market.bootstrap;

import com.exchange.buy.sell.market.domain.ImageEntity;
import com.exchange.buy.sell.market.domain.ProductEntity;
import com.exchange.buy.sell.market.domain.category.Currency;
import com.exchange.buy.sell.market.domain.category.Gender;
import com.exchange.buy.sell.market.domain.category.Size;
import com.exchange.buy.sell.market.repository.ProductRepository;
import com.exchange.buy.sell.market.utils.ImageLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.exchange.buy.sell.market.domain.category.Currency.PLN;
import static com.exchange.buy.sell.market.domain.category.Gender.MALE;

/**
 * Created by oleht on 11.05.2018
 */
@Component
public class ProductBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        productRepository.saveAll(getProductExamples());
    }

    private List<ProductEntity> getProductExamples() {

        List<ProductEntity> productExamples = new ArrayList<>();
        try {
            ProductEntity product_1 = getTestProduct(MALE, Size.L, "54", "Sprzedam kurtke w super postaci", new BigDecimal("139.999"), PLN);
            product_1.addImages(getTestImage("img/m_kurtka_l_1.jpg"));
            ProductEntity product_2 = getTestProduct(MALE, Size.XL, "83", "Sprzedam bluze",  new BigDecimal("100.00"), PLN);
            product_2.addImages(getTestImage("img/m_bluza_xl_1.jpg"));

            ProductEntity product_3 = getTestProduct(MALE, Size.L, null, "Sprzedam spódnice",  new BigDecimal("100.00"), PLN);
            product_3.addImages(getTestImage("img/f_spodnica_l_1.jpg"));

            ProductEntity product_4 = getTestProduct(MALE, Size.XS, null, "Sprzedam sukienke, extra wygłąd",  new BigDecimal("100.00"), PLN);
            product_4.addImages(getTestImage("img/f_sukienka_xs_1.jpg"));

            ProductEntity product_5 = getTestProduct(MALE, Size.L, "54", "Sprzedam kurtke w super postaci", new BigDecimal("139.999"), PLN);
            product_5.addImages(getTestImage("img/m_kurtka_l_1.jpg"));
            ProductEntity product_6 = getTestProduct(MALE, Size.XL, "83", "Sprzedam bluze",  new BigDecimal("100.00"), PLN);
            product_6.addImages(getTestImage("img/m_bluza_xl_1.jpg"));

            ProductEntity product_7 = getTestProduct(MALE, Size.L, null, "Sprzedam spódnice",  new BigDecimal("100.00"), PLN);
            product_7.addImages(getTestImage("img/f_spodnica_l_1.jpg"));

            ProductEntity product_8 = getTestProduct(MALE, Size.XS, null, "Sprzedam sukienke, extra wygłąd",  new BigDecimal("100.00"), PLN);
            product_8.addImages(getTestImage("img/f_sukienka_xs_1.jpg"));


            productExamples.addAll(Arrays.asList(product_1, product_2, product_3, product_4, product_5, product_6, product_7, product_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productExamples;
    }

    private ImageEntity getTestImage(String pathFromResource) throws IOException {
        ImageEntity img = new ImageEntity();
        img.setImage(ImageLoadUtil.getTestImg(pathFromResource));

        return img;
    }

    private ProductEntity getTestProduct(Gender gender, Size sizeAlphabetical, String sizeNumerical, String description, BigDecimal priceValue, Currency currency) {
        ProductEntity entity = new ProductEntity();
        entity.setCurrency(currency);
        entity.setGender(gender);
        entity.setDescription(description);
        entity.setPriceValue(priceValue);
        entity.setSizeAlphabetical(sizeAlphabetical);
        entity.setSizeNumerical(sizeNumerical);

        return entity;
    }

}
