package fr.ensisa.minimaths.meteorite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.concurrent.ThreadLocalRandom;

public class FallingObject extends GameObject{
    private boolean isOperator;
    private int value;
    private GameSurface gameSurface;
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int SUM=1;
    private static final int PRODUCT_OPERATOR=2;
    public static final float VELOCITY = 15;
    private int rowUsing = 0;
    private boolean isReady;

    private ChibiCharacter player;

    private Panel panel;
    private Bitmap[] topToBottoms;

    private int colUsing;

    private int movingVectorY = 1;


    private static long lastSpawn=-1;

    public FallingObject(GameSurface gameSurface, Bitmap image,ChibiCharacter player,Panel panel, int x, int y,int value) {
        super(image, 1, 4, x, y);
        this.gameSurface= gameSurface;
        this.player= player;
        this.panel=panel;
        this.isReady=true;
        this.value=value;
        this.topToBottoms = new Bitmap[colCount]; // 3

        for(int col = 0; col< this.colCount; col++ ) {
            this.topToBottoms[col]  = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
        }
    }
    public void update()  {
            if(isReady) spawn();
            this.colUsing++;
            if (colUsing >= this.colCount) {
                this.colUsing = 0;
            }

            this.y += VELOCITY * movingVectorY;

    }
    public Bitmap[] getMoveBitmaps()  {
                return this.topToBottoms;
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    public void draw(Canvas canvas)
    {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
    }

    public boolean isCollision(){
        if (player.getY()<=this.y+100 && player.getY()>=this.y-100 && player.getX()<=this.x+64 && player.getX()>=this.x-64)
        {
            this.x=-200;
            this.y=-200;
            this.isReady=true;
            return true;
        }
        if(this.y>0.8 * this.gameSurface.getScreenHeight())
        {
            this.x=this.y=-200;
            this.isReady=true;
        }
        return false;
    }

    public void spawn()
    {
        int probability = ThreadLocalRandom.current().nextInt(0,100);
        int rand= ThreadLocalRandom.current().nextInt(100,(int)(this.gameSurface.getScreenWidth())-100);
        if(this.lastSpawn==-1)
        {
            this.lastSpawn = System.nanoTime();
            this.x = rand;
            this.y = 100;
            this.isReady = false;
            if (this.panel.getOperator() == this.SUM) {
                if(this.panel.getIsSetN1())
                {
                    if(probability > 75) this.value = panel.getRes() - panel.getN1();
                    else this.value = ThreadLocalRandom.current().nextInt(panel.getRes() - panel.getN1() - 10, panel.getRes() - panel.getN1() + 10);
                }
                else
                {
                    this.value=ThreadLocalRandom.current().nextInt(0, panel.getRes());
                }
            }
        }
        long now= System.nanoTime();
        long delta=(now - this.lastSpawn)/1000000000;
        if(delta>2)
        {
            this.lastSpawn=System.nanoTime();
            this.x=(int)rand;
            this.y=100;
            isReady=false;
            if(probability>75) this.value=panel.getRes()-panel.getN1();
            else this.value= ThreadLocalRandom.current().nextInt(panel.getRes()-panel.getN1()-10,panel.getRes()-panel.getN1()+10);
        }
    }

    public int getValue()
    {
        return this.value;
    }

}
