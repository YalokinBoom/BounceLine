package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;

public class Paddle {

    private double oldPointPositionX;
    private double oldPointPositionY;
    private double newPointPositionX;
    private double newPointPositionY;
    private Paint paint;

    public Paddle(Context context, double oldInitialPositionX, double oldInitialPositionY, double newInitialPositionX, double newInitialPositionY) {
        oldPointPositionX = oldInitialPositionX;
        oldPointPositionY = oldInitialPositionY;
        newPointPositionX = newInitialPositionX;
        newPointPositionY = newInitialPositionY;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.paddle);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        // Draw two circles at old and new positions
        canvas.drawCircle((float) oldPointPositionX, (float) oldPointPositionY, (float) 45, paint);
        canvas.drawCircle((float) newPointPositionX, (float) newPointPositionY, (float) 45, paint);
    }

    public void drawPointsPosition(Canvas canvas, Context context) {
        // Print old and new position coordinates
        String oldPointPosition = String.format("%07.2f", oldPointPositionX) + ", Y: " + String.format("%07.2f", oldPointPositionY);
        String newPointPosition = String.format("%07.2f", newPointPositionX) + ", Y: " + String.format("%07.2f", newPointPositionY);
        Paint positionPaint = new Paint();
        int positionColor = ContextCompat.getColor(context, R.color.cyan);
        positionPaint.setColor(positionColor);
        positionPaint.setTextSize(40);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        canvas.drawText("Old: X: " + oldPointPosition, 100, height-100, positionPaint);
        canvas.drawText("New: Y: " + newPointPosition, 100, height-50, positionPaint);
    }

    public void update() {

    }

    public void beginNewPosition(double positionX, double positionY) {
        this.oldPointPositionX = this.newPointPositionX;
        this.oldPointPositionY = this.newPointPositionY;
        this.newPointPositionX = positionX;
        this.newPointPositionY = positionY;
    }

    public void changeNewPosition(double positionX, double positionY) {
        this.newPointPositionX = positionX;
        this.newPointPositionY = positionY;
    }
}
