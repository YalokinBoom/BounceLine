package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;

class Ball {
    public double positionX;
    public double positionY;
    private double velocityX;
    private double velocityY;
    private double gravityX;
    private double gravityY = 0.3;
    private double radius;
    private double mass;
    private Paint paint;
    public Ball(Context context, double positionX, double positionY, double velocityX, double velocityY, double radius, double mass) {
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
        String ballPosition = String.format("%07.2f", positionX) + ", Y: " + String.format("%07.2f", positionY);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(40);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        canvas.drawText("Ball Position: X: " + ballPosition, 100, height - 200, paint);
    }

    public void update() {
        positionX += velocityX;
        positionY += velocityY;
        gravity();
    }
    public void gravity() {
        velocityX += gravityX;
        velocityY += gravityY;
    }

    public void setPosition(double positionX, double positionY) {
        velocityX = 0;
        velocityY = 0;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
