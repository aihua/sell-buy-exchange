package com.exchange.buy.sell.market.domain;

import com.exchange.buy.sell.market.domain.category.Currency;
import com.exchange.buy.sell.market.domain.category.Gender;
import com.exchange.buy.sell.market.domain.category.Size;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleht on 11.05.2018
 */
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "product_type",
//        discriminatorType = DiscriminatorType.STRING)
public class ProductEntity extends AbstractEntity<Long> {

    @Column(name = "owner")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "sizeAlphabetical")
    @Enumerated(EnumType.STRING)
    private Size sizeAlphabetical;

    @Column(name = "sizeNumerical")
    private String sizeNumerical;

    @Lob
    private String description;

    private BigDecimal priceValue;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "product_image")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ImageEntity> productImages = new ArrayList<>();

    private Boolean active;

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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

    public List<ImageEntity> getProductImages() {
        return productImages;
    }

    public void addImages(ImageEntity image) {
        image.setProduct(this);
        this.productImages.add(image);
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
