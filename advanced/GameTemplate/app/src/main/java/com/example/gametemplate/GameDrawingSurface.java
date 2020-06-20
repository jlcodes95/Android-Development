package com.example.gametemplate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameDrawingSurface extends SurfaceView implements Runnable {
    // -----------------------------------
    // ## ANDROID DEBUG VARIABLES
    // -----------------------------------

    // Android debug variables
    final static String TAG="PONG-GAME";

    // -----------------------------------
    // ## SCREEN & DRAWING SETUP VARIABLES
    // -----------------------------------

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // ## GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------
    int circleX;        // (x, y coordinate of the center of Circle)
    int circleY;

    int circle2X;
    int circle2Y;

    int circle3X;
    int circle3Y;


    // ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    Enemy e1, e2, e3;

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();



    Player player;



    // What is the problem with using the above approach to create 50 circles
    // assume 1 circle = 1 enbemy

    // ----------------------------
    // ## GAME STATS - number of lives, score, etc
    // ----------------------------


    public GameDrawingSurface(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();
        this.paintbrush.setColor(Color.WHITE);

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        // @TODO: Add your sprites to this section
        // This is optional. Use it to:
        //  - setup or configure your sprites
        //  - set the initial position of your sprites

        // set the initial position of the circle to (20,200)


        e1 = new Enemy(20, 100, 25, Color.BLACK, 5);
        e2 = new Enemy(20, 100, 100, Color.GREEN, 10);
        e3 = new Enemy(20, 100, 50, Color.argb(255,227, 9, 176), 10);

        // add them to the array list
        // this.enemies.add(e1);
        this.enemies.add(e2);
        // this.enemies.add(e3);

        this.player = new Player(screenWidth/2, 400, 50);




//
//        circleX = 20;
//        circleY = 100;
//
//        circle2X = 20;
//        circle2Y = 100;
//
//        circle3X = 20;
//        circle3Y = 100;

        // @TODO: Any other game setup stuff goes here


    }

    // ------------------------------
    // HELPER FUNCTIONS
    // ------------------------------

    // This funciton prints the screen height & width to the screen.
    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }


    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------

    int count = 0;


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


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    // 1. Tell Android the (x,y) positions of your sprites
    public void updatePositions() {
        // @TODO: Update the position of the sprites

//        // move the circle1  10px to the diagonal
//        circleY = circleY + 10;
//        circleX = circleX + 10;
//
//        // move circle2 to the right
//        circle2X = circle2X + 10;
//
//        // move circle3 down
//        circle3Y = circle3Y + 20;


        // move the circle1  10px to the diagonal
//        e1.x = e1.x + e1.speed;
//        e1.y = e1.y + e1.speed;

        // move circle2 to the right
        e2.x = e2.x + e2.speed;

        // move circle3 down
//        e3.y = e3.y + e3.speed;

        // @TODO: Collision detection code

        // make e2 bounce back when it hit the wall
        if (e2.x >= this.screenWidth) {
//            e2.x = e2.x - e2.speed - 10;
            e2.speed = -10;
        }
        else if (e2.x <= 0) {
            e2.speed = +10;
        }



        // @TODO: How to detect if enemy & player are touching?
//        if (e2.x == player.x && e2.y == player.y) {
//            // player & enemy are touching, so do something
//        }




        Log.d(TAG, "Pos: " + e2.x + "," + e2.y);



    }

    // 2. Tell Android to DRAW the sprites at their positions
    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // Put all your drawing code in this section

            // configure the drawing tools
            this.canvas.drawColor(Color.WHITE);
            paintbrush.setColor(Color.BLACK);



            //@TODO: Draw the sprites (rectangle, circle, etc)

            for (int i = 0; i < enemies.size(); i++) {

                Enemy e = enemies.get(i);
                paintbrush.setColor(e.color);
                this.canvas.drawCircle(e.x, e.y, e.size, paintbrush);

            }


            // draw the player
            this.canvas.drawRect(player.x1, player.y1, player.x1 + player.width, player.y1 + player.width, paintbrush);



//            //black circle
//            paintbrush.setColor(e1.color);
//            this.canvas.drawCircle(e1.x, e1.y, e1.size, paintbrush);


//            // yellow circle
//            paintbrush.setColor(e2.color);
//            this.canvas.drawCircle(e2.x, e2.y, e2.size, paintbrush);
//
//            // pink circle
//            paintbrush.setColor(e3.color);
//            this.canvas.drawCircle(e3.x, e3.y, e3.size, paintbrush);


            //@TODO: Draw game statistics (lives, score, etc)
            paintbrush.setColor(Color.BLACK);
            paintbrush.setTextSize(60);
            canvas.drawText("Loop: " + count, 20, 100, paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    // Sets the frame rate of the game
    public void setFPS() {
        try {
            gameThread.sleep(50); // 50 ms = 0.05 seconds
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    // EXERCISE: MOve player to wherever the mouse is clicked
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            // user pushed down on screen

            player.x1 = (int) event.getX();
            player.y1 = (int) event.getY();

        }
        else if (userAction == MotionEvent.ACTION_UP) {
            // user lifted their finger
        }
        return true;
    }
}
