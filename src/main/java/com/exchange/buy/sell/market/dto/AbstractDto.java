package com.exchange.buy.sell.market.dto;

/**
 * Created by oleht on 11.05.2018
 */
public abstract class AbstractDto<T> {

    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
