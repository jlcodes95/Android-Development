package com.example.tourismapp.components;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AttractionDAO {

    @Insert
    public void addAttraction(Attraction attraction);

    @Delete
    public void deleteAttraction(Attraction attraction);

    @Query("UPDATE attractions SET name=:name, address=:address, description=:description WHERE id=:id")
    public void updateAttractionById(int id, String name, String address, String description);

    @Query("SELECT * FROM attractions WHERE id=:id")
    public List<Attraction> getAttractionById(int id);

    @Query("SELECT * FROM attractions")
    public List<Attraction> getAttractionList();
}
