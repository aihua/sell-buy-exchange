package com.exchange.buy.sell.market.service;

import com.exchange.buy.sell.market.dto.ProductDto;
import com.exchange.buy.sell.market.exception.MyResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by oleht on 14.05.2018
 */
@RestController
public class ProductRestService {

    @Autowired
    private ProductService productService;

    @RequestMapping(
            value = "/student/get",
            params = { "page", "size" },
            method = RequestMethod.GET
    )
    public Page<ProductDto> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size) {

        Page<ProductDto> resultPage = productService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }

        return resultPage;
    }

}
