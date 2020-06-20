package com.example.finaltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CustomDrawingSurface extends SurfaceView implements Runnable{

    private final String TAG = "DEBUG_CANVAS";
    private final int MIN_RGB = 77;
    private final int MAX_RGB = 255;
    private final float MIN_SPEED = 15;
    private final float MAX_SPEED = 30;
    private final float SIZE_CONFETTI = 50;
    private final float MAG_SOUTH_MIN = 90.0f;
    private final float MAG_SOUTH_MAX = 270.0f;

    private int screenHeight;
    private int screenWidth;
    private float centerX;
    private float centerY;
    private float throwDestinationY = 0.0f;
    private boolean doesSweep = false;
    private boolean doesThrow = false;
    private boolean screenLocked = false;
    private ArrayList<Confetti> confettis;

    // game state
    boolean gameIsRunning = true;
    int count = 0;

    // threading
    Thread gameThread;

    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    public CustomDrawingSurface(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();
        this.paintbrush.setColor(Color.WHITE);
//
//        this.screenHeight = h;
//        this.screenWidth = w;

        Log.d(TAG, "center point is: (" + this.centerX + ", " + this.centerY + ")");

        confettis = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "center point is: (" + w + ", " + h + ")");
        this.screenHeight = h;
        this.screenWidth = w;
        this.centerX = w / 2.0f;
        this.centerY = h / 2.0f;
    }

    @Override
    public void run() {
        // infinite loop
        while (gameIsRunning == true) {
            this.updatePositions(); // calculate new coordinates for sprtes
            this.redrawSprites();  // draw sprintes in new positions
            count = count + 1;
            this.setFPS();  // put in a delay
        }
    }

    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updatePositions() {
        Log.d(TAG, "updating position...");
        if (doesSweep) {
            for(Confetti c: confettis) {
                c.moveTo(centerX, centerY);
            }
        }else if (doesThrow) {
            for(Confetti c: confettis) {
                c.moveTo(c.getX(), this.throwDestinationY);
            }
        }
    }

    public void redrawSprites() {
        Log.d(TAG, "" + confettis.size());
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();
            this.canvas.drawColor(Color.WHITE);

            for (Confetti c : confettis) {
                paintbrush.setColor(c.getColor());
                this.canvas.drawRect(c.getX() - SIZE_CONFETTI/2,
                        c.getY() - SIZE_CONFETTI/2,
                        c.getX() + SIZE_CONFETTI,
                        c.getY() + SIZE_CONFETTI, paintbrush);
            }

            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(200); // 50 ms = 0.05 seconds
        }
        catch (Exception e) {

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //screen is locked...
        if (screenLocked) {
            return true;
        }

        int action = event.getActionMasked();

        Log.d(TAG, "ACTION IN MAIN: (" + event.getX() + ", " + event.getY() + ")");

        float x = event.getX();
        float y = event.getY();
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(TAG,"Action detected was DOWN");
                Log.d(TAG, "Drawing canvas ");
                confettis.add(new Confetti(x, y, getRandomSpeed(), Color.rgb(getRandomInt(), getRandomInt(), getRandomInt())));
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(TAG,"Action detected was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(TAG,"Action detected was UP");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public void onLockScreen() {
        this.screenLocked = !this.screenLocked;
        Log.d(TAG, "screen is locked: " + this.screenLocked);
    }

    public void onThrow(float degrees) {
        if (degrees >= MAG_SOUTH_MIN && degrees <= MAG_SOUTH_MAX) {
            this.throwDestinationY = this.screenHeight;
        }else {
            this.throwDestinationY = 0;
        }
        this.doesThrow = true;
    }

    public void onSweep() {
        this.doesSweep = true;
    }

    public void erase() {
        this.confettis.clear();
    }

    private float getRandomSpeed() {
        Random random = new Random();
        return MIN_SPEED + random.nextFloat() * (MAX_SPEED - MIN_SPEED);
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(MAX_RGB - MIN_RGB + 1) + MIN_RGB;
    }


}
