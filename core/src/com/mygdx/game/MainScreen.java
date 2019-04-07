package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen extends ScreenAdapter {
    private static final float WIDTH = 480;
    private static final float HEIGHT = 640;
    private static final float GAP = 200f;

    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shape;

    //texture for images
    private Texture background;
    private Texture gambar_bungaAtas;
    private Texture gambar_bungaBawah;
    private Texture gambar_tawon;

    //objects
    private Tawon tawon;
//    private Bunga bunga;

    private Array<Bunga> bungas = new Array<Bunga>();

    private void createNewBunga(){
        Bunga newBunga = new Bunga(gambar_bungaAtas,gambar_bungaBawah);
        newBunga.setPosition(WIDTH + Bunga.WIDTH);
        bungas.add(newBunga);
    }

    private void checkNeedNewBunga(){
        if(bungas.size == 0){
            createNewBunga();
        }
        else {
            Bunga bunga = bungas.peek();
            if (bunga.getX() < WIDTH - GAP){
                createNewBunga();
            }
        }
    }

    private void removeBunga(){
        if (bungas.size > 0){
            Bunga firstBunga = bungas.first();
            if (firstBunga.getX() < -Bunga.WIDTH){
                bungas.removeValue(firstBunga,true);
            }
        }
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WIDTH/2,HEIGHT/2,0);
        camera.update();

        viewport = new FitViewport(WIDTH,HEIGHT,camera);

        background = new Texture(Gdx.files.internal("bg.png"));
        gambar_bungaAtas = new Texture(Gdx.files.internal("flowerTop.png"));
        gambar_bungaBawah = new Texture(Gdx.files.internal("flowerBottom.png"));
        gambar_tawon = new Texture(Gdx.files.internal("bee1.png"));

        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        //init object
        tawon = new Tawon(gambar_tawon);
        tawon.setPosition(WIDTH/2,HEIGHT);

//        bunga = new Bunga(gambar_bungaAtas,gambar_bungaBawah);
//        bunga.setPosition(WIDTH);
    }

    @Override
    public void render(float delta) {
        //clear the screen first
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //setup batch
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background,0,0); //update the screen with textures
        batch.end();

        //draw objects
        shape.setProjectionMatrix(camera.projection);
        shape.setTransformMatrix(camera.view);
        shape.begin(ShapeRenderer.ShapeType.Line);

        tawon.drawBody(shape);
//        bunga.drawBunga(shape);
        for(Bunga bunga : bungas){
            bunga.drawBunga(shape);
        }

        shape.end();

        update(delta);
    }

    private void update(float delta){
        tawon.update();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            tawon.terbang();
        }

        blokPosisiTawon();

//        bunga.update(delta);
        updateBungas(delta);

        if (checkTabrakan()){
            restart();
        }
    }

    private void updateBungas(float delta){
        for(Bunga bunga: bungas){
            bunga.update(delta);
        }
        checkNeedNewBunga();
        removeBunga();
    }

    private void blokPosisiTawon(){
        tawon.setPosition(tawon.getX(), MathUtils.clamp(tawon.getY(),0,HEIGHT));
    }

    private boolean checkTabrakan(){
        for (Bunga bunga: bungas){
            if (bunga.isTawonKenak(tawon)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    private void restart(){
        //set to default init
        tawon.setPosition(WIDTH/2,HEIGHT);
        bungas.clear();
    }
}
