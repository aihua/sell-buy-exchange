package com.exchange.buy.sell.market.domain;

import javax.persistence.*;

/**
 * Created by oleht on 11.05.2018
 */
@Entity
public class ImageEntity extends AbstractEntity<Long> {

    @JoinColumn(name = "product")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductEntity product;

    @Lob
    private Byte[] image;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }
}
