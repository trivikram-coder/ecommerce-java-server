package com.example.server.Services;

import com.example.server.Models.Wishlist;
import com.example.server.Repositories.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepo wishlistRepo;

    // ✅ ADD TO WISHLIST
    public String addToWishlist(Wishlist wishlist, String email) {

        Optional<Wishlist> existingItem =
                wishlistRepo.findByEmailAndProductId(email, wishlist.getProductId());

        if (existingItem.isPresent()) {
            return "Already in wishlist";
        }

        wishlist.setEmail(email);
        wishlistRepo.save(wishlist);
        return "Item added to wishlist";
    }

    // ✅ GET WISHLIST ITEMS
    public List<Wishlist> getWishlistItems(String email) {
        return wishlistRepo.findByEmail(email);
    }

    // ✅ DELETE WISHLIST ITEM
    public boolean deleteWishlistItem(Long wishlistId, String email) {

        Optional<Wishlist> itemOpt = wishlistRepo.findById(wishlistId);

        if (itemOpt.isPresent()) {
            Wishlist item = itemOpt.get();

            if (!item.getEmail().equals(email)) {
                return false;
            }

            wishlistRepo.delete(item);
            return true;
        }
        return false;
    }
}
