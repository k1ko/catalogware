package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 26.08.2016.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    public enum Role {
        ADMIN,
        CUSTOMER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Basket basket;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likes")
//    private List<Product> likes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    private List<Product> products;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
    private List<ProductLike> likes;

    public User() {
    }

    public User(Long id, String name, String email, String password, Date createdAt, Role role,
                Basket basket, List<Order> orders, List<Product> likes, List<Product> products) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.basket = basket;
        this.orders = orders;
//        this.likes = likes;
        this.products = products;
    }

    public User(Long id, String name, String email, String password, Date createdAt, Role role) {
        this(id, name, email, password, createdAt, role, null, null, null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

//    public List<Product> getLikes() {
//        return likes;
//    }
//
//    public void setLikes(List<Product> likes) {
//        this.likes = likes;
//    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
