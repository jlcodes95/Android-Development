package com.example.tourismapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tourismapp.R;
import com.example.tourismapp.components.Attraction;
import com.example.tourismapp.components.AttractionRating;
import com.example.tourismapp.components.User;
import com.example.tourismapp.components.WishList;

import java.util.List;

public class AttractionDetailActivity extends AppCompatActivity {

    private Attraction attraction;
    private User user;
    private WishList wishlist;
    private AttractionRating attractionRating;
    private SharedPreferences spa;
    private static final String SP_APP_NAME = "currentSessionData";
    private static final String TAG = "ATTRACTION_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.tbAttractionDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addRatingBarListener();

        Intent intent = getIntent();
        if (intent != null){
            attraction = (Attraction) intent.getSerializableExtra("attraction");
        }

        spa = getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE);
        String username = spa.getString("username", "");
        Log.d(TAG, username);
        user = MainActivity.db.userDAO().getUserByUsername(username).get(0);

        //set wishlist
        List<WishList> wishListList = MainActivity.db.wishlistDAO()
                .getWishListByUserAndAttractionId(user.getId(), attraction.getId());
        Log.d(TAG, ""+wishListList.size());
        if (wishListList.size() == 0){
            this.wishlist = null;
        }else{
            this.wishlist = wishListList.get(0);
        }

        //set attraction rating
        List<AttractionRating> attractionRatingList = MainActivity.db.attractionRatingDAO()
                .getAttractionRatingByUserIdAndAttractionId(user.getId(), attraction.getId());
        if(attractionRatingList.size() == 0){
            this.attractionRating = null;
        }else{
            this.attractionRating = attractionRatingList.get(0);
            ((RatingBar) findViewById(R.id.ratingBar)).setRating(this.attractionRating.getRating());
        }

        //set data of attraction
        ((TextView) findViewById(R.id.tvName)).setText(attraction.getName());
        ((TextView) findViewById(R.id.tvAddress)).setText(attraction.getAddress());
        ((TextView) findViewById(R.id.tvDescription)).setText(attraction.getDescription());
        ((ImageView) findViewById(R.id.ivAttraction)).setImageResource(
                this.getResources().getIdentifier(
                        attraction.getImagePath(),
                        "drawable",
                        this.getPackageName()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        swapWishListIcon(menu.findItem(R.id.miWishList));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.miWishList:
                handleWishListClick();
                swapWishListIcon(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addRatingBarListener(){
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                handleRatingChanged(ratingBar, rating, fromUser);
            }
        });
    }

    private void handleRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
            if (this.attractionRating == null) {
                AttractionRating attractionRating = new AttractionRating();
                attractionRating.setUserId(this.user.getId());
                attractionRating.setAttractionId(this.attraction.getId());
                attractionRating.setRating(rating);
                MainActivity.db.attractionRatingDAO().addAttractionRating(attractionRating);
            } else {
                this.attractionRating.setRating(rating);
                MainActivity.db.attractionRatingDAO().updateAttractionRating(this.attractionRating);
            }
            this.attractionRating = MainActivity.db.attractionRatingDAO()
                    .getAttractionRatingByUserIdAndAttractionId(
                            this.user.getId(),
                            this.attraction.getId())
                    .get(0);
    }

    private void swapWishListIcon(MenuItem item){
        if (this.wishlist != null){
            item.setIcon(R.drawable.icon_bookmark_fill);
        }else{
            item.setIcon(R.drawable.icon_bookmark_no_fill);
        }
    }

    private void handleWishListClick(){
        if (this.wishlist == null){
            WishList wishlist = new WishList();
            wishlist.setUserId(this.user.getId());
            wishlist.setAttractionId(this.attraction.getId());
            MainActivity.db.wishlistDAO().addWishList(wishlist);
            this.wishlist = MainActivity.db.wishlistDAO()
                    .getWishListByUserAndAttractionId(
                            user.getId(),
                            attraction.getId())
                    .get(0);
        }else{
            MainActivity.db.wishlistDAO().deleteWishList(this.wishlist);
            this.wishlist = null;
        }
    }
}
