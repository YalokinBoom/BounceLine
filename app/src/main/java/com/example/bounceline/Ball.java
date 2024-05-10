package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;

public class Ball {
    public double positionX;
    public double positionY;
    private double velocityX;
    private double velocityY;
    private double gravityX;
    private double gravityY = 0.3;
    private double radius;
    private double mass;
    private Paint paint;
    private Walls walls;
    private String collided;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;

    public Ball(Context context, Walls walls, double positionX, double positionY, double velocityX, double velocityY, double radius, double mass) {
        this.walls = walls;

        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        this.radius = radius;
        this.mass = mass;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.ball);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }

    public void drawBallPosition(Canvas canvas, Context context) {
        String ballPosition = "X: " + String.format("%07.2f", positionX) + ", Y: " + String.format("%07.2f", positionY);
        String ballVelocity = String.format("%05.2f", Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2)));
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.ball);
        paint.setColor(color);
        paint.setTextSize(40);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        canvas.drawText("Ball Position: " + ballPosition, 100, height - 200, paint);
        canvas.drawText("Ball Velocity: " + ballVelocity + ", Collided: " + collided, 100, height - 150, paint);
    }

    public void update() {
        velocity();
        gravity();
        wallsCollision();
    }

    private void velocity() {
        positionX += velocityX;
        positionY += velocityY;
    }

    private void gravity() {
        velocityX += gravityX;
        velocityY += gravityY;
    }

    private void wallsCollision() {
        if ((positionY-radius) < walls.topMargin) { // Top collision
            velocityY *= -1;
            velocity();
            collided = "TOP";
        } else if ((positionY+radius) > (height-walls.bottomMargin)) { // Bottom collision
            velocityY *= -1;
            velocity();
            collided = "BOTTOM";
        } else if ((positionX-radius) < walls.leftMargin) { // Left collision
            velocityX *= -1;
            velocity();
            collided = "LEFT";
        } else if ((positionX+radius) > (width-walls.rightMargin)) { // Right collision
            velocityX *= -1;
            velocity();
            collided = "RIGHT";
        } else {
            collided = "NO";
        }
    }
}
