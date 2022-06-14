package fr.ensisa.minimaths.meteorite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.ensisa.minimaths.R;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    private ChibiCharacter chibi1;

    private int screenHeight;
    private int screenWidth;

    private Panel panel;


    private final List<FallingObject> fs = new ArrayList<FallingObject>();

    public GameSurface(Context context)  {
        super(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenHeight = displayMetrics.heightPixels;
        this.screenWidth = displayMetrics.widthPixels;
        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {

        this.chibi1.update();
        for(FallingObject f: this.fs)  {
            f.update();
            if(f.isCollision())
            {
                this.panel.collect(f.getValue());
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                this.chibi1.setXStop(x);
                int movingVectorX = x - this.chibi1.getX()>0?1:-1;
                this.chibi1.setMovingVector(movingVectorX);
                this.chibi1.setIsRunning(true);
            }
            return false;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        String score="Score: ".concat(String.valueOf(panel.getScore()));
        canvas.drawText(score,(int) (0.7*screenWidth),(int) (0.1*screenHeight),paint);
        paint.setTextSize(60);
        canvas.drawText(panel.getEquation(),(int) (0.7*screenWidth),(int) (0.15*screenHeight),paint);
        this.chibi1.draw(canvas);
        for(FallingObject f: this.fs)  {
            f.draw(canvas);
            canvas.drawText(String.valueOf(f.getValue()),f.getX()+65,f.getY()+100,paint);
        }
        int x=150;
        int y=150;
        for(int i=0;i<this.panel.getLife();i++) {
            canvas.drawBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.heart), x, y, null);
            x += 100;
        }
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int value;
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        Bitmap meteor = BitmapFactory.decodeResource(this.getResources(),R.drawable.meteor);
        Bitmap heart = BitmapFactory.decodeResource(this.getResources(),R.drawable.meteor);
        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,(int) (screenWidth*0.5),(int) (screenHeight*0.7));
        this.panel= new Panel();
        for(int i=0;i<10;i++)
        {
            this.fs.add(new FallingObject(this,meteor,this.chibi1,this.panel,-200,-200,0));
        }
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

}