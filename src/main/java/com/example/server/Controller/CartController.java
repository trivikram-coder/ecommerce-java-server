package com.example.server.Controller;

import com.example.server.Models.Cart;
import com.example.server.Services.CartService;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ ADD TO CART
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestBody Cart item,
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token is required"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        String result = cartService.addToCart(item, email);

        return ResponseEntity.ok(Map.of("message", result));
    }

    // ✅ GET CART ITEMS
    @GetMapping("/get")
    public ResponseEntity<?> getCartItems(
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        List<Cart> items = cartService.getItems(email);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Cart fetched successfully",
                        "data", items
                )
        );
    }

    // ✅ UPDATE CART ITEM QUANTITY
    @PutMapping("/update/{cartId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long cartId,
            @RequestParam Long quantity,
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        boolean updated = cartService.updateQuantity(cartId, quantity, email);

        if (!updated) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Item not found or unauthorized"));
        }

        return ResponseEntity.ok(Map.of("message", "Cart updated successfully"));
    }

    // ✅ DELETE CART ITEM
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable Long cartId,
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        boolean deleted = cartService.deleteItem(cartId, email);

        if (!deleted) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Item not found or unauthorized"));
        }

        return ResponseEntity.ok(Map.of("message", "Item deleted from cart"));
    }
}
