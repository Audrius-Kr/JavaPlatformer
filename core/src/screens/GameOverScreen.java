package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CatGame;

public class GameOverScreen implements Screen {
    CatGame game;
    Texture deadImg;
    public  static final int DEAD_IMG_W = 512;
    public static final int DEAD_IMG_H = 256;
    Viewport gamePort;
    OrthographicCamera gameCamera;

    public GameOverScreen(CatGame game) {
        //gameCamera = new OrthographicCamera();
        this.game = game;
        deadImg = new Texture("died.png");
       // gamePort = new FitViewport(CatGame.SCREEN_WIDTH / CatGame.PPM, CatGame.SCREEN_HEIGHT / CatGame.PPM, gameCamera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        game.batch.begin();
        /*
        Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

         */

        game.batch.draw(deadImg, (CatGame.SCREEN_WIDTH - DEAD_IMG_W ) / 2f / CatGame.PPM,(CatGame.SCREEN_HEIGHT - DEAD_IMG_H ) / 2f / CatGame.PPM, DEAD_IMG_W/CatGame.PPM,DEAD_IMG_H/CatGame.PPM);
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        deadImg.dispose();

    }
}
