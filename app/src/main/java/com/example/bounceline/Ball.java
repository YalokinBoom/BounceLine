package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class Ball {
    public double positionX;
    public double positionY;
    private double velocityX;
    private double velocityY;
    private double gravityX = 0.00001;
    private double gravityY = 1000;
    private double radius;
    private double mass;
    private Paint paint;
    private Walls walls;
    private String collided;
    private String gravitated;
    private double previousTime;
    private double nextTime = ((double) System.currentTimeMillis()) / 1000;

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
        canvas.drawText("Ball Position: " + ballPosition, 100, height - 230, paint);
        canvas.drawText("Ball Velocity: " + ballVelocity + ", Collided: " + collided, 100, height - 180, paint);
    }

    public void update() {
        previousTime = nextTime;
        nextTime = ((double) System.currentTimeMillis()) / 1000;

        collided = checkWallsCollision();

        if (collided == "NO") {
            gravity();
        } else {
            wallsCollision();
        }
    }

    private void gravity() {
        positionY += (1/2) * gravityY * Math.pow((nextTime - previousTime), 2) + velocityY * (nextTime - previousTime);
        velocityY += gravityY * (nextTime - previousTime);
    }

    private String checkWallsCollision() {
        double nextPositionY = positionY + ((velocityY + gravityY * (nextTime - previousTime)) * (nextTime - previousTime));

        if ((nextPositionY - radius) < walls.topMargin) { // Top collision
            return "TOP";
        } else if ((nextPositionY + radius) > walls.bottomMargin) { // Bottom collision
            return "BOTTOM";
        } else {
            return "NO";
        }
    }

    private void wallsCollision() {
        if (collided == "TOP") { // Top collision
            double collisionVelocityY = - Math.sqrt(   (2 * gravityY * (walls.topMargin - positionY + radius)) + Math.pow(velocityY, 2)  );
            double dTime = (nextTime - previousTime) - (   (collisionVelocityY - velocityY) / gravityY   );

            velocityY = gravityY * dTime - collisionVelocityY;
            positionY += (walls.topMargin - positionY + radius) - (   (dTime * (velocityY + collisionVelocityY)) / 2   );

        } else if (collided == "BOTTOM") { // Bottom collision
            double collisionVelocityY = Math.sqrt(   (2 * gravityY * (walls.bottomMargin - positionY - radius)) + Math.pow(velocityY, 2)  );
            double dTime = (nextTime - previousTime) - (   (collisionVelocityY - velocityY) / gravityY   );

            velocityY = gravityY * dTime - collisionVelocityY;
            positionY += (walls.bottomMargin - positionY - radius) - (   (dTime * (velocityY + collisionVelocityY)) / 2   );

        }
    }
}
