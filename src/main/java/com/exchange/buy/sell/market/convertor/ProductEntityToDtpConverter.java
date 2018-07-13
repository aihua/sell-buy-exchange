package com.exchange.buy.sell.market.convertor;

import com.exchange.buy.sell.market.domain.ProductEntity;
import com.exchange.buy.sell.market.dto.ProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.exchange.buy.sell.market.utils.ImageLoadUtil.base64EncodedImage;

/**
 * Created by oleht on 11.05.2018
 */
@Component
public class ProductEntityToDtpConverter implements Converter<ProductEntity, ProductDto> {

    @Nullable
    @Override
    public ProductDto convert(ProductEntity prod) {
        ProductDto dto = new ProductDto();
        dto.setId(prod.getId());
        dto.setCurrency(prod.getCurrency());
        dto.setDescription(prod.getDescription());
        dto.setGender(prod.getGender());
        dto.setPriceValue(prod.getPriceValue());
        dto.setSizeAlphabetical(prod.getSizeAlphabetical());
        dto.setSizeNumerical(dto.getSizeNumerical());
//        dto.setImages(prod.getProductImages().stream().map(ImageEntity::getImage).collect(Collectors.toList()));
        dto.setBase64EncodedImages(prod.getProductImages().stream().map(img -> base64EncodedImage(img.getImage())).collect(Collectors.toList()));

        return dto;
    }
}
