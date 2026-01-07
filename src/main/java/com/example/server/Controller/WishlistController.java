package com.example.server.Controller;

import com.example.server.Models.Wishlist;
import com.example.server.Services.WishlistService;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ ADD TO WISHLIST
    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(
            @RequestBody Wishlist wishlist,
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token is required"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        Wishlist result = wishlistService.addToWishlist(wishlist, email);
        if(result==null){
            return ResponseEntity.ok(Map.of("message","Already in the cart"));
        }
        return ResponseEntity.ok(Map.of("message", "Added to wishlist","data",result));
    }

    // ✅ GET WISHLIST ITEMS
    @GetMapping("/get")
    public ResponseEntity<?> getWishlistItems(
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        List<Wishlist> items = wishlistService.getWishlistItems(email);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Wishlist fetched successfully",
                        "data", items
                )
        );
    }

    // ✅ DELETE WISHLIST ITEM
    @DeleteMapping("/delete/{wishlistId}")
    public ResponseEntity<?> deleteWishlistItem(
            @PathVariable Long wishlistId,
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        boolean deleted = wishlistService.deleteWishlistItem(wishlistId, email);

        if (!deleted) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Item not found or unauthorized"));
        }

        return ResponseEntity.ok(Map.of("message", "Item deleted from wishlist"));
    }
}
