package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bunga {

    private static final float LEBAR_BATANG = 13f;
    private static final float PANJANG_BATANG = 447f;
    private static final float RADIUS_BUNGA = 33f;
    private static final float GAP = 225f;
    private static final float KECEPATAN_BUNGA = 100f;

    private float x = 0;
    private float y = 0;

    private final Circle bungaAtas;
    private final Circle bungaBawah;
    private final Rectangle batangAtas;
    private final Rectangle batangBawah;

    public Bunga(Texture texture1, Texture texture2){
        this.y = MathUtils.random(-400f);
        this.batangBawah = new Rectangle(x,y,LEBAR_BATANG,PANJANG_BATANG);
        this.bungaBawah = new Circle(x+batangBawah.width,y+batangBawah.height,RADIUS_BUNGA);

        this.bungaAtas = new Circle(x+batangBawah.width,bungaBawah.y + GAP,RADIUS_BUNGA);
        this.batangAtas = new Rectangle(bungaAtas.x,bungaAtas.y+(RADIUS_BUNGA/2),batangBawah.width,batangBawah.height);
    }

    public void setPosition(float x){
        this.x = x;
        updatePosisiBunga();
    }

    private void updatePosisiBunga(){
        bungaBawah.setX(x + batangBawah.width/2);
        batangBawah.setX(x);

        bungaAtas.setX(x + batangAtas.width/2);
        batangAtas.setX(x);

        //Todo: make looping for spawn next obstacle
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

}
