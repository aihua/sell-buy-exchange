package com.exchange.buy.sell.market.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by oleht on 13.07.2018
 */
@Entity
public class UserEntity extends AbstractEntity<Long> {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @OneToOne
    private ImageEntity profileImage;

    private String description;

    @Column(name = "client_product")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ProductEntity> products;

    @Column(name = "client_product_marked")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ProductEntity> markedProducts;

    private List<String> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImageEntity getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageEntity profileImage) {
        this.profileImage = profileImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public List<ProductEntity> getMarkedProducts() {
        return markedProducts;
    }

    public void setMarkedProducts(List<ProductEntity> markedProducts) {
        this.markedProducts = markedProducts;
    }


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
