package com.example.finalteststarter.classes;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DonutDAO {

    @Insert
    public void addDonut(Donut donut);

    @Query("SELECT * FROM donuts")
    public List<Donut> getDonutList();
}
