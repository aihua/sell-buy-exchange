package com.exchange.buy.sell.market.controller;

import com.exchange.buy.sell.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by oleht on 11.05.2018
 */
@Controller
public class IndexPageController {

    @Autowired
    private ProductService productService;

//    @RequestMapping({"", "/", "/index"})
//    public String getIndexPage(Model model) {
//
//        model.addAttribute("products", productService.getAllProducts());
//
//        return "index";
//    }
}
