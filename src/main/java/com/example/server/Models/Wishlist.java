package com.example.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "wishlist",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "productId"})
        }
)
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ Wishlist row ID

    @Column(nullable = false)
    private Long productId;  // ✅ Actual product ID

    @Column(nullable = false)
    private String email;

    private String title;
    private Double price;
    private Double discountPrice;
    private Double rating;

    @Column(length = 1000)
    private String description;

    private String image;
    private String category;
}
