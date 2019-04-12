package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bunga {

    private static final float LEBAR_BATANG = 13f;
    private static final float PANJANG_BATANG = 447f;
    private static final float RADIUS_BUNGA = 33f;
    private static final float GAP = 225f;
    private static final float KECEPATAN_BUNGA = 100f;

    public static final float WIDTH = RADIUS_BUNGA * 2;

    private float x = 0;
    private float y = 0;

    private final Circle bungaAtas;
    private final Circle bungaBawah;
    private final Rectangle batangAtas;
    private final Rectangle batangBawah;

    private final Texture imgAtas, imgBawah;

    private boolean isThrough = false;

    public Bunga(Texture texture1, Texture texture2){
        this.y = MathUtils.random(-400f);
        this.batangBawah = new Rectangle(x,y,LEBAR_BATANG,PANJANG_BATANG);
        this.bungaBawah = new Circle(x+batangBawah.width,y+batangBawah.height,RADIUS_BUNGA);

        this.bungaAtas = new Circle(x+batangBawah.width,bungaBawah.y + GAP,RADIUS_BUNGA);
        this.batangAtas = new Rectangle(bungaAtas.x,bungaAtas.y+(RADIUS_BUNGA/2),batangBawah.width,batangBawah.height);

        imgAtas = texture1;
        imgBawah = texture2;
    }

    public void setPosition(float x){
        this.x = x;
        updatePosisiBunga();
    }

    public float getX() {
        return x;
    }

    private void updatePosisiBunga(){
        bungaBawah.setX(x + batangBawah.width/2);
        batangBawah.setX(x);

        bungaAtas.setX(x + batangAtas.width/2);
        batangAtas.setX(x);
    }

    public void update(float delta){
        setPosition(x - (KECEPATAN_BUNGA * delta));
    }

    public void drawBunga(ShapeRenderer shape){
        shape.circle(bungaBawah.x,bungaBawah.y,RADIUS_BUNGA);
        shape.rect(batangBawah.x,batangBawah.y,batangBawah.width,batangBawah.height);
        shape.circle(bungaAtas.x,bungaAtas.y,RADIUS_BUNGA);
        shape.rect(batangAtas.x,batangAtas.y,batangAtas.width,batangAtas.height);
    }

    public void drawAsset(Batch batch){
        batch.draw(imgAtas, bungaAtas.x-2*RADIUS_BUNGA+batangAtas.width, bungaAtas.y-RADIUS_BUNGA);
        batch.draw(imgBawah, bungaBawah.x-2*RADIUS_BUNGA+batangBawah.width, batangBawah.y+RADIUS_BUNGA);
    }

    public boolean isTawonKenak(Tawon tawon){
        Circle tawonShape = tawon.getBody();
        return
        Intersector.overlaps(tawonShape,bungaAtas) ||
        Intersector.overlaps(tawonShape,bungaBawah) ||
        Intersector.overlaps(tawonShape,batangAtas) ||
        Intersector.overlaps(tawonShape,batangBawah);
    }

    public void tandaiSudahLewat(){
        isThrough= true;
    }

    public boolean isSudahLewat(){
        return isThrough;
    }

}
