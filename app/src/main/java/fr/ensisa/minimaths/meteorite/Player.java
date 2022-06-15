package fr.ensisa.minimaths.meteorite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {

    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    private boolean isRunning;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;

    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 25;

    private int movingVectorX = 1;
    private int xStop;

    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Player(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 4, 3, x, y);

        this.gameSurface= gameSurface;

        this.rightToLefts = new Bitmap[colCount]; // 3
        this.leftToRights = new Bitmap[colCount]; // 3

        this.isRunning=false;
        this.xStop=0;

        for(int col = 0; col< this.colCount; col++ ) {
            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
        }
    }

    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }


    public void update()  {
        if(this.isRunning) {
            this.colUsing++;
            if (colUsing >= this.colCount) {
                this.colUsing = 0;
            }

            if(!(x>=this.xStop-15 && x<=this.xStop+15)) {
                this.x +=VELOCITY * movingVectorX;
            }
            else isRunning=false;

            if (this.x < 0) {
                this.x = 0;
                this.movingVectorX = 0;
                isRunning=false;
            } else if (this.x > this.gameSurface.getWidth() - width) {
                this.x = this.gameSurface.getWidth() - width;
                this.movingVectorX = 0;
                isRunning=false;
            }

            if (movingVectorX > 0) this.rowUsing = ROW_LEFT_TO_RIGHT;
            else this.rowUsing = ROW_RIGHT_TO_LEFT;
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public boolean getIsRunning()
    {
        return this.isRunning;
    }
    public void setIsRunning(boolean b)
    {
        this.isRunning=b;
    }
    public void setXStop(int x)
    {
        this.xStop=x;
    }
    public void setMovingVector(int movingVectorX)  {
        this.movingVectorX= movingVectorX;
    }

}