package com.example.gestures;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
//    private View pv;

    private ImageView imageView;
    private Paint paintbrush;
    private Canvas canvas;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //get view to attach gesture listener
//        this.pv = (View) findViewById(R.id.pinkView);
//
//        //add ontouchlistner to the view
//        this.pv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getActionMasked();
//
//                Log.d(TAG, "ACTION IN PINK: (" + event.getX() + ", " + event.getY() + ")");
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        Log.d(TAG,"Action detected was DOWN");
//
//                        Log.d(TAG, "Drawing canvas ");
//
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        Log.d(TAG,"Action detected was MOVE");
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//                        Log.d(TAG,"Action detected was UP");
//                        return true;
//                }
//                return false;
//            }
//        });
//
//        this.imageView = (ImageView) findViewById(R.id.ivImage);
//        this.bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
//
//        //drawing it on a blank photograph
//        this.canvas = new Canvas(this.bitmap);
//
//        //@TODO: draw on photo (canvas)
//        this.paintbrush = new Paint();
//        this.paintbrush.setColor(Color.RED);
//        this.paintbrush.setStrokeWidth(5);
//
//        this.canvas.drawRect(100, 100, 400, 400, this.paintbrush);
//
//        this.imageView.setImageBitmap(this.bitmap);
//
//        this.imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getActionMasked();
//
//                Log.d(TAG, "ACTION IN PINK: (" + event.getX() + ", " + event.getY() + ")");
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        Log.d(TAG,"Action detected was DOWN");
//
//                        Log.d(TAG, "Drawing canvas ");
//
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        Log.d(TAG,"Action detected was MOVE");
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//                        Log.d(TAG,"Action detected was UP");
//                        return true;
//                }
//                return false;
//            }
//        });

        // programatic layout
        //1.  create layout container
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //2. add view to container
        Button button = new Button(this);
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setText("Click Me");
        linearLayout.addView(button);

        //3. add views, text / other UI
        setContentView(linearLayout);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getActionMasked();
//
//        Log.d(TAG, "ACTION IN MAIN: (" + event.getX() + ", " + event.getY() + ")");
//
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                Log.d(TAG,"Action detected was DOWN");
//
//                Log.d(TAG, "Drawing canvas ");
//
//                return true;
//            case (MotionEvent.ACTION_MOVE) :
//                Log.d(TAG,"Action detected was MOVE");
//                return true;
//            case (MotionEvent.ACTION_UP) :
//                Log.d(TAG,"Action detected was UP");
//
//                return true;
//            default :
//                return super.onTouchEvent(event);
//        }
//    }

}
