package com.exchange.buy.sell.market.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by oleht on 11.05.2018
 */
@MappedSuperclass
public class AbstractEntity <T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private T id;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date creationDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModificationDate;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}
