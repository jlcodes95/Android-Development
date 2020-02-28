package com.example.tourismapp.components;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishListDAO {
    @Insert
    public void addWishList(WishList wishlist);

    @Delete
    public void deleteWishList(WishList wishlist);

    @Query("SELECT * FROM wishlists WHERE id=:id")
    public List<WishList> getWishListById(int id);

    @Query("SELECT * FROM wishlists " +
            "INNER JOIN users ON users.id = wishlists.userId " +
            "WHERE wishlists.userId = :userId")
    public List<WishList> getWishListByUserId(int userId);

    @Query("SELECT * FROM wishlists WHERE userId = :userId AND attractionId = :attractionId")
    public List<WishList> getWishListByUserAndAttractionId(int userId, int attractionId);

    @Query("SELECT * FROM wishlists")
    public List<WishList> getWishListList();

}
