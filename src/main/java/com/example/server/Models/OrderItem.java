package com.example.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // 🔥 ROW ID (AUTO)

    private Long productId;       // 🔥 PRODUCT ID

    private String title;
    private String email;

    private Double price;
    private Double discountPrice;
    private Integer quantity;

    private String image;
}
