package com.example.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "product_id"})
        }
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;   // ✅ UNIQUE per row

    @Column(name = "product_id", nullable = false)
    private Long productId;   // ✅ product reference

    @Column(nullable = false)
    private String email;     // ✅ user identifier

    private String title;
    private Double price;
    private Double discountPrice;
    private Double rating;
    private String description;
    private String image;
    private String category;

    private Long quantity;
}
