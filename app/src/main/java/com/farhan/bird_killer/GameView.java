package com.farhan.bird_killer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying;
    private int screenX,screenY;
    private float screenRotationX,screenRotationY;
    private Paint paint;
    private Background background1,background2;

    public GameView(Context context,int screenX,int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRotationX = 1920f / screenX;
        screenRotationY = 1080f / screenY;


        background1 = new Background(screenX,screenY,getResources());
        background2 = new Background(screenX,screenY,getResources());
        background2.x =screenX;

        paint = new Paint();

    }



    @Override
    public void run() {

        while (isPlaying){

            update();
            draw();
            sleep();

        }

    }

    private void sleep() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void draw() {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);


            getHolder().unlockCanvasAndPost(canvas);


        }

    }

    private void update() {

        background1.x -=10 * screenRotationX;
        background2.x -= 10 * screenRotationX;


        if (background1.x+background1.background.getWidth()<0){

            background1.x = screenX;
        }


        if (background2.x+background1.background.getWidth()<0){

            background2.x = screenX;
        }

    }

    public void resume()
    {

        isPlaying =true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause()
    {


        try {
            isPlaying = false;
            thread.join();

        }catch (InterruptedException e){

            e.printStackTrace();
        }


    }






}
