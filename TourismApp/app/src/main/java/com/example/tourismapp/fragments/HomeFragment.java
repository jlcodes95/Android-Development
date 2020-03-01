package com.example.tourismapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tourismapp.R;
import com.example.tourismapp.activities.AttractionDetailActivity;
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
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private static final int DEFAULT_PADDING = 25;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);

        //on clicks for place holder
        addOnClickPlaceHolder();

        User user = MainActivity.db.userDAO()
                .getUserByUsername(MainActivity.spa.getString("username", "")).get(0);
        this.wishLists = MainActivity.db.wishlistDAO()
                .getWishListByUserId(user.getId());

        if (this.wishLists.size() != 0){
            //remove placeholder
            LinearLayout layout = view.findViewById(R.id.layoutWishList);
//            layout.removeAllViewsInLayout();
            int placeholderChildCount = layout.getChildCount();
            int currentCount = 0;

            float factor = getContext().getResources().getDisplayMetrics().density;

            for(final WishList wishList: this.wishLists){
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

                //add on click for each image view
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addOnClickImageView(wishList);
                    }
                });

                // no more placeholders in place
                if (currentCount == placeholderChildCount){
                    layout.addView(imageView);
                }else{
                    layout.removeViewAt(currentCount);
                    layout.addView(imageView, currentCount);
                    currentCount++;
                }

            }
        }

        return view;
    }

    /**
     * on click handler for images
     * @param wishList
     */
    private void addOnClickImageView(WishList wishList){
        Attraction attraction = MainActivity.db.attractionDAO().getAttractionById(wishList.getAttractionId()).get(0);
        MainActivity.spa.edit().putInt("previousFragment", R.id.b_nav_home).commit();
        Intent intent = new Intent(getContext(), AttractionDetailActivity.class);
        intent.putExtra("attraction", attraction);
        startActivity(intent);
    }

    /**
     * on click handler for place holder
     */
    private void addOnClickPlaceHolder(){
        ImageView placeholder1 = view.findViewById(R.id.imgPlaceHolder1);
        ImageView placeholder2 = view.findViewById(R.id.imgPlaceHolder2);

        placeholder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goToAttractions();
            }
        });

        placeholder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goToAttractions();
            }
        });
    }

}
