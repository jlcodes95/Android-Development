package com.example.gametemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    GameDrawingSurface gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // programgatically showing the GameDrawingSurface
        // - no need to use an xml file
        this.gameView = new GameDrawingSurface(this, size.x, size.y);
        setContentView(this.gameView);
    }


    // This function gets run when user switches from the game to some other app on the phone
    @Override
    protected void onPause() {
        super.onPause();

        // Pause the game
        gameView.pauseGame();
    }

    // This function gets run when user comes back to the game
    @Override
    protected void onResume() {
        super.onResume();

        // Start the game
        gameView.startGame();
    }
}
