package com.exchange.buy.sell.market.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by oleht on 11.05.2018
 */
public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file);

}
