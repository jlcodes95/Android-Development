package com.example.tourismapp.components;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AttractionRatingDAO {

    @Insert
    public void addAttractionRating(AttractionRating attractionRating);

    @Delete
    public void deleteAttractionRating(AttractionRating attractionRating);

    @Update
    public void updateAttractionRating(AttractionRating attractionRating);

    @Query("SELECT * FROM attraction_ratings WHERE userId = :userId AND attractionId = :attractionId")
    public List<AttractionRating> getAttractionRatingByUserIdAndAttractionId(int userId, int attractionId);

    @Query("SELECT * FROM attraction_ratings")
    public List<AttractionRating> getAttractionRatingList();

    @Query("SELECT AVG(rating) FROM attraction_ratings WHERE attractionId = :attractionId")
    public double getAttractionRatingAverageByAttractionId(int attractionId);
}
