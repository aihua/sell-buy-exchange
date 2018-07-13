package com.exchange.buy.sell.market.domain.category;

import java.util.Arrays;

/**
 * Created by oleht on 11.05.2018
 */
public enum Size {
    XS, S, M, L, XL, XXL, XXXL;

    public static Size getByName(String name){
        return Arrays.stream(Size.values()).filter(n -> n.name().toLowerCase().equals(name.toLowerCase()))
                .findFirst().orElse(null);
    }

}
