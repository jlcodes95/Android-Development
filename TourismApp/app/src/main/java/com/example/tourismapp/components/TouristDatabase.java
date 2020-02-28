package com.example.tourismapp.components;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database (entities = {User.class, Attraction.class, WishList.class, AttractionRating.class}, version = 1)
public abstract class TouristDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract AttractionDAO attractionDAO();
    public abstract WishListDAO wishlistDAO();
    public abstract AttractionRatingDAO attractionRatingDAO();
}
