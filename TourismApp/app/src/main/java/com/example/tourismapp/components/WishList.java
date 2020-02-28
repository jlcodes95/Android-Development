package com.example.tourismapp.components;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "wishlists", foreignKeys = {
    @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Attraction.class,
        parentColumns = "id",
        childColumns = "attractionId",
        onDelete = ForeignKey.CASCADE)
})
public class WishList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NotNull
    private int userId;
    @NotNull
    private int attractionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }
}
