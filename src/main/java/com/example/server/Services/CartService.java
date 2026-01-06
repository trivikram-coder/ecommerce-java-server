package com.example.server.Services;

import com.example.server.Models.Cart;
import com.example.server.Repositories.CartRepo;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    public String addToCart(Cart cart, String email) {
        try {
            Cart existingCart = cartRepo
                    .findByEmailAndProductId(email, cart.getProductId())
                    .orElse(null);

            if (existingCart != null) {
                existingCart.setQuantity(existingCart.getQuantity() + 1);
                cartRepo.save(existingCart);
                return "Quantity updated successfully";
            }

            cart.setEmail(email);          // ✅ IMPORTANT
            cart.setQuantity(1L);
            cartRepo.save(cart);

            return "Item added to cart";

        } catch (Exception e) {
            throw new RuntimeException("Failed to add item to cart", e); // ✅
        }
    }


    public List<Cart> getItems(String email){
        return cartRepo.findByEmail(email);
    }
    public boolean updateQuantity(Long cartId, Long quantity, String email) {
        Optional<Cart> cartOpt = cartRepo.findById(cartId);

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (!cart.getEmail().equals(email)) return false;

            cart.setQuantity(quantity);
            cartRepo.save(cart);
            return true;
        }
        return false;
    }

    public boolean deleteItem(Long cartId, String email) {
        Optional<Cart> cartOpt = cartRepo.findById(cartId);

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (!cart.getEmail().equals(email)) return false;

            cartRepo.delete(cart);
            return true;
        }
        return false;
    }



}
