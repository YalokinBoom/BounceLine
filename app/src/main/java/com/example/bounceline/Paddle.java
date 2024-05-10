package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;

public class Paddle {

    private double oldPositionX;
    private double oldPositionY;
    private double newPositionX;
    private double newPositionY;
    private Paint circlePaint;
    private Paint linePaint;

    public Paddle(Context context, double oldPositionX, double oldPositionY, double newPositionX, double newPositionY) {
        this.oldPositionX = oldPositionX;
        this.oldPositionY = oldPositionY;
        this.newPositionX = newPositionX;
        this.newPositionY = newPositionY;

        circlePaint = new Paint();
        int color = ContextCompat.getColor(context, R.color.paddleCircle);
        circlePaint.setColor(color);

        linePaint = new Paint();
        int lineColor = ContextCompat.getColor(context, R.color.paddleLine);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(40);
    }

    public void draw(Canvas canvas) {
        // Draw line between old and new positions
        canvas.drawLine((float) oldPositionX, (float) oldPositionY, (float) newPositionX, (float) newPositionY, linePaint);
        // Draw two circles at old and new positions
        canvas.drawCircle((float) oldPositionX, (float) oldPositionY, (float) 30, circlePaint);
        canvas.drawCircle((float) newPositionX, (float) newPositionY, (float) 30, circlePaint);
        // Draw circles between the old and new positions
        int pointsDistance = (int) Math.round(  Math.sqrt( Math.pow((newPositionX-oldPositionX), 2) + Math.pow((newPositionY-oldPositionY), 2) )  ) / 60;
        for (int i = 1; i < pointsDistance; i++) {
            canvas.drawCircle((float) (oldPositionX + (newPositionX - oldPositionX)*i/pointsDistance), (float) (oldPositionY + (newPositionY - oldPositionY)*i/pointsDistance), (float) 20, circlePaint);
        }
    }

    public void drawPointsPosition(Canvas canvas, Context context) {
        // Print old and new position coordinates
        String oldPointPosition = String.format("%07.2f", oldPositionX) + ", Y: " + String.format("%07.2f", oldPositionY);
        String newPointPosition = String.format("%07.2f", newPositionX) + ", Y: " + String.format("%07.2f", newPositionY);
        Paint positionPaint = new Paint();
        int positionColor = ContextCompat.getColor(context, R.color.paddleCircle);
        positionPaint.setColor(positionColor);
        positionPaint.setTextSize(40);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        canvas.drawText("Old Point Position: X: " + oldPointPosition, 100, height-75, positionPaint);
        canvas.drawText("New Point Position: Y: " + newPointPosition, 100, height-25, positionPaint);
    }

    public void update() {

    }

    public void beginNewPosition(double positionX, double positionY) {
        this.oldPositionX = this.newPositionX;
        this.oldPositionY = this.newPositionY;
        this.newPositionX = positionX;
        this.newPositionY = positionY;
    }

    public void changeNewPosition(double positionX, double positionY) {
        this.newPositionX = positionX;
        this.newPositionY = positionY;
    }
}
