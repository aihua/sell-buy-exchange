package com.exchange.buy.sell.market.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by oleht on 11.05.2018
 */
@MappedSuperclass
public class AbstractEntity <T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private T id;

    @CreationTimestamp
    private Timestamp creationDate;

    @UpdateTimestamp
    private Timestamp lastModificationDate;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Timestamp lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}
