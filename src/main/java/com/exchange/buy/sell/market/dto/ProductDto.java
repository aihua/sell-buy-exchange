package com.exchange.buy.sell.market.dto;

import com.exchange.buy.sell.market.domain.category.Currency;
import com.exchange.buy.sell.market.domain.category.Gender;
import com.exchange.buy.sell.market.domain.category.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleht on 11.05.2018
 */
public class ProductDto extends AbstractDto<Long> {

    private Gender gender;

    private Size sizeAlphabetical;

    private String sizeNumerical;

    private String description;

    private BigDecimal priceValue;

    private Currency currency;

    private List<String> base64EncodedImages = new ArrayList<>();

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Size getSizeAlphabetical() {
        return sizeAlphabetical;
    }

    public void setSizeAlphabetical(Size sizeAlphabetical) {
        this.sizeAlphabetical = sizeAlphabetical;
    }

    public String getSizeNumerical() {
        return sizeNumerical;
    }

    public void setSizeNumerical(String sizeNumerical) {
        this.sizeNumerical = sizeNumerical;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<String> getBase64EncodedImages() {
        return base64EncodedImages;
    }

    public void setBase64EncodedImages(List<String> base64EncodedImages) {
        this.base64EncodedImages = base64EncodedImages;
    }
}
