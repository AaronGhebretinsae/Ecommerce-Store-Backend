package com.educative.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
