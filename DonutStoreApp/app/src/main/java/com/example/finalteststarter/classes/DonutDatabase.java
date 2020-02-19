package com.example.finalteststarter.classes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Donut.class}, version = 1, exportSchema = false)
public abstract class DonutDatabase extends RoomDatabase {
    public abstract DonutDAO donutDAO();
}
