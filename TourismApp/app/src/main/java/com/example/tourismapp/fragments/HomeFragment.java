package com.example.tourismapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tourismapp.R;
import com.example.tourismapp.activities.MainActivity;
import com.example.tourismapp.components.Attraction;
import com.example.tourismapp.components.User;
import com.example.tourismapp.components.WishList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private List<WishList> wishLists;
    private static final String TAG = "HOME_FRAGMENT";
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final int DEFAULT_PADDING = 10;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        User user = MainActivity.db.userDAO()
                .getUserByUsername(MainActivity.spa.getString("username", "")).get(0);
        this.wishLists = MainActivity.db.wishlistDAO()
                .getWishListByUserId(user.getId());

        if (this.wishLists.size() != 0){
            //remove placeholder
            LinearLayout layout = view.findViewById(R.id.layoutWishList);
            layout.removeAllViewsInLayout();

            float factor = getContext().getResources().getDisplayMetrics().density;

            for(WishList wishList: this.wishLists){
                Log.d(TAG, String.format("ID: %d, userId: %d, attractionId: %d",
                        wishList.getId(), wishList.getUserId(), wishList.getAttractionId()));
                Attraction attraction = MainActivity.db.attractionDAO()
                        .getAttractionById(wishList.getAttractionId()).get(0);
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(this.getResources().getIdentifier(
                        attraction.getImagePath(),
                        "drawable",
                        getContext().getPackageName()));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        (int)(WIDTH * factor),
                        (int) (HEIGHT * factor)));
                imageView.setPadding(
                        (int) (DEFAULT_PADDING * factor),
                        0,
                        (int) (DEFAULT_PADDING * factor),
                        0);
                layout.addView(imageView);
            }
        }

        return view;
    }

}
