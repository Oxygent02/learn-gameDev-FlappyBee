package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Tawon extends Game {

    private float x = 0;
    private float y = 0;
    private float ySpeed = 0;
    private static float PERCEPATAN =  0.3f;
    private static float KECEPATAN_TERBANG = 3f;

    private static final float RADIUS = 24f;
    private Circle body;
    private final Texture img;

    @Override
    public void create() {
        setScreen(new MainScreen());
    }

    public Tawon(Texture texture) {
        body = new Circle(x, y, RADIUS);
        img = texture;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        body.setX(x);
        body.setY(y);
    }

    public void drawBody(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(body.x,body.y,body.radius);
    }

    public void drawAsset(Batch batch){
        batch.draw(img, x-RADIUS*2-10, y-RADIUS*2);
    }

    public void update(){
        ySpeed = ySpeed - PERCEPATAN;
        setPosition(x,y + ySpeed);
    }

    public Circle getBody() {
        return body;
    }

    public void terbang(){
        ySpeed = KECEPATAN_TERBANG;
        setPosition(x,y+ySpeed);
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }
}
