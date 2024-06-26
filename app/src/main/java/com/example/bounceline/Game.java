package com.example.bounceline;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Game manages all objects  in a game and is responsible for updating all states and render all
 */
class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Walls walls;
    private final Paddle paddle;
    private final Ball ball;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize walls
        walls = new Walls(getContext(), 250, 275, 50, 50);

        // Initialize ball
        ball = new Ball(getContext(), walls, 540, 500, 0, 0, 75, 10);

        // Initialize paddle
        paddle = new Paddle(getContext(),400, 600, 680, 600);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle touch event actions
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paddle.beginNewPosition((double) event.getX(), (double) event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                paddle.changeNewPosition((double) event.getX(), (double) event.getY());
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        walls.draw(canvas);

        drawTitle(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        ball.drawBallPosition(canvas, getContext());
        paddle.drawPointsPosition(canvas, getContext());

        paddle.draw(canvas);
        ball.draw(canvas);
    }

    public void drawTitle(Canvas canvas) {
        String title = "BounceLine by Nikolay Smirnov";
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.paddleCircle);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText(title, 100, 70, paint);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.ball);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 150, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.ball);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 210, paint);
    }

    public void update() {
        // Update game state
        paddle.update();
        ball.update();
    }
}
