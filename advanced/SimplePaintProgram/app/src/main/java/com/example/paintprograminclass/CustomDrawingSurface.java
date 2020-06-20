package com.example.paintprograminclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomDrawingSurface extends View {

    // 1. the ability to detect touches
    // 2. the ability to draw

    final String TAG = "CUSTOMCANVAS";


    // - paintbrush, canvas, bitmap + (colors come later)
    Paint paintbrush;
    Bitmap bitmap;
    Canvas canvas;


    // - helper variables for drawing lines on the screen
    float startX, startY;           // starting coordinates of the line
    float endX, endY;               // ending coordinates of the line


    public CustomDrawingSurface(Context context) {
        super(context, null);

        // configure out paintbrush
        this.paintbrush = new Paint();
        this.paintbrush.setColor(Color.GRAY);       // initial color of the pen
        this.paintbrush.setStrokeWidth(8);          // initial width of the pen

        // configure your bitmap and your canvas
        // - we are not configuring it in the constructor because
        // its quite annoying to get the size & width of the screen from inside the constructro
        // - if you know that you want the size of this drawing surface to be the same
        // no matter what device you're on, then configure it here
        // - but if you want it to be dynamic, you gotta do it somewhere else
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        Log.d(TAG, "Screen size changed");

        // Initialize our bitmap and canvas here.
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Pass the bitmap to the canvas
        this.canvas = new Canvas(this.bitmap);

        // OPTIONALLY: Maybe you can set the background color of the screen
        this.canvas.drawColor(Color.WHITE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "Calling onDraw function!");
        super.onDraw(canvas);

        // force the screen to draw your amazing thing
        canvas.drawBitmap(this.bitmap, 0, 0, this.paintbrush);

    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float mouseX = event.getX();
        float mouseY = event.getY();

        // Log.d(TAG, "User touched the screen: (" + mouseX + ", " + mouseY + ")");

        // detect what specific type of gesture was detected --> flight, swipe, pan --> GestureDetector
        // - FINGER IS DOWN
        // - FINGER IS UP
        // - FINGER HAS MOVED
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Finger is down: (" + mouseX + ", " + mouseY + ")");
                // @TODO: write some logic to handle when the mouse goes down
                // - capture the starting position of where you line should be
                this.startX = mouseX;
                this.startY = mouseY;
                // EXERCISE 1: Modify this code to draw when the mouse moves
                // EXERCISE 2: Update your app with a ERASE and YELLOW button
                // - When person presses the YELLOW button, all new lines will be drawn in YELLOW
                // - When the person presses the ERASE button, the entire image will be erased
                // (blank drawing surface)


                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Finger moved: (" + mouseX + ", " + mouseY + ")");
                // @TODO: write some logic to handle when the mouse moves


                this.endX = mouseX;
                this.endY = mouseY;

                // draw the line
                this.canvas.drawLine(this.startX, this.startY, this.endX, this.endY, this.paintbrush);

                // update where the starting positing is
                this.startX = this.endX;
                this.startY = this.endY;

                invalidate();

                // Path --> connect a series of (x,y) points into a single line

                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Finger lifted up: (" + mouseX + ", " + mouseY + ")");
                // @TODO: write some logic to handle when the mouse is released (finger up)
                this.endX = mouseX;
                this.endY = mouseY;

                // draw the line
                this.canvas.drawLine(this.startX, this.startY, this.endX, this.endY, this.paintbrush);
                // force the canvas to redraw
                invalidate();


                return true;
            default:
                return super.onTouchEvent(event);
        }
    }



    // helper functions
    public void erase() {
        Log.d(TAG, "Erasing the canvas");
        this.canvas.drawColor(Color.WHITE);
        invalidate();
    }

    public void changeColor(int color) {
        Log.d(TAG, "Changing the color");
        this.paintbrush.setColor(color);
    }

}
