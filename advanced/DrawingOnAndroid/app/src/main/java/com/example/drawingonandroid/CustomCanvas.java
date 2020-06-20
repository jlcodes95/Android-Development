package com.example.drawingonandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomCanvas extends View {

    private final String TAG = "DEBUG_CUSTOMCANVAS";

    private Paint paintbrush;
    private Bitmap bitmap;
    private Canvas canvas;

    private float startX, startY;
    private float endX, endY;

    public CustomCanvas(Context context) {
        super(context, null);

        //configure paintbrush
        this.paintbrush = new Paint();
        this.paintbrush.setColor(Color.GREEN);
        this.paintbrush.setStrokeWidth(5);

        //configure bitmap and canvas is annoying in constructor
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "Calling onDraw function");
        super.onDraw(canvas);

        //force screen to draw
        canvas.drawBitmap(this.bitmap, 0, 0, this.paintbrush);
    }

    //enableing drawing
    @Override
    protected  void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        Log.d(TAG, "Screen size changed");

        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);

        //OPTIONALLY: maybe set background color of canvas
        this.canvas.drawColor(Color.MAGENTA);
    }

    //adding touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float mouseX = event.getX();
        float mouseY = event.getY();

        Log.d(TAG, "User touched the screen: (" + mouseX + ", " + mouseY + ")");

        //detect what specific type of gesture was detected
            // fling
            // swipe
            // pan

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "User touched DOWN on the screen: (" + mouseX + ", " + mouseY + ")");
                this.startX = mouseX;
                this.startY = mouseY;
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "User moved on the screen: (" + mouseX + ", " + mouseY + ")");
                this.endX = mouseX;
                this.endY = mouseY;

                //draw the line
                this.canvas.drawLine(this.startX, this.startY, this.endX, this.endY, this.paintbrush);
                //force canvas redraw
                invalidate();
                this.startX = mouseX;
                this.startY = mouseY;
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "User touched UP from the screen: (" + mouseX + ", " + mouseY + ")");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

}
