package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;

public class Walls {
    public int topMargin;
    public int bottomMargin;
    public int leftMargin;
    public int rightMargin;
    private Paint paint;

    public Walls(Context context, int topMargin, int bottomMargin, int leftMargin, int rightMargin) {
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }

    public void draw(Canvas canvas) {
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        canvas.drawRoundRect((float) leftMargin, (float) topMargin, (float) (width-rightMargin), (float) (height-bottomMargin), 50, 50, paint);
    }
}
