package com.example.finaltest;

import android.graphics.Color;

public class Confetti {

    private float x;
    private float y;
    private int color;
    private float speed;

    public Confetti(float x, float y, float speed, int color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * REFERENCED https://math.stackexchange.com/questions/175896/finding-a-point-along-a-line-a-certain-distance-away-from-another-point
     * calculating distance in pixels along the diagonal towards the specific point from the current position
     * distance depends on the speed; and is calculated each time the frame is drawn
     * @param targetX
     * @param targetY
     */
    public void moveTo(float targetX, float targetY) {
        //prevents the squares from being redrawn
        if ((this.x < targetX && this.x + this.speed <= targetX)||
            (this.x > targetX && this.x - this.speed >= targetX)||
                (this.y < targetY && this.y + this.speed <= targetY)||
                (this.y > targetY && this.y - this.speed >= targetY)){
            float distance = (float) Math.sqrt(Math.pow(targetX - this.x, 2) + Math.pow(targetY - this.y, 2));
            float ratio = speed / distance;
            this.x = (float) (1-ratio)*this.x + ratio*targetX;
            this.y = (float) (1-ratio)*this.y + ratio*targetY;
        }else {
            this.x = targetX;
            this.y = targetY;
        }


    }
}
