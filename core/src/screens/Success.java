package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.CatGame;

public class Success implements Screen {
    CatGame game;
    Texture deadImg, goMenuImg, pixmapImg;
    public  static final int DEAD_IMG_W = 512;
    public static final int DEAD_IMG_H = 256;
    String successImg = "success.png";
    String GO_MENU_IMG = "goMenu.png";
    Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



    public Success(CatGame game) {
        this.game = game;
        deadImg = new Texture(successImg);
        goMenuImg = new Texture(GO_MENU_IMG);
        pixmapImg = new Texture(pixmap);
    }

    @Override
    public void show() {

    }
    private void update() {
        changeScreen();
    }

    @Override
    public void render(float v) {
        game.batch.begin();

        // Save the old projection matrix
        Matrix4 oldProjection = game.batch.getProjectionMatrix().cpy();

        // Set the projection matrix to an orthographic projection with the size of the screen
        game.batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        float screenMiddleX = CatGame.getScreenWidth();
        float screenMiddleY = CatGame.getScreenHeight();
        float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = Gdx.graphics.getHeight();
        float width = height * aspectRatio;
        float x = (Gdx.graphics.getWidth() - width) / 2;

        // Draw your sprite
        game.batch.draw(pixmapImg, x, 0, width, height, 0, 0, pixmapImg.getWidth(), pixmapImg.getHeight(), false, true);
        game.batch.draw(deadImg, (screenMiddleX - DEAD_IMG_W ) / 2f ,(screenMiddleY - DEAD_IMG_H ) / 2f, DEAD_IMG_W, DEAD_IMG_H);
        game.batch.draw(goMenuImg, (screenMiddleX -  DEAD_IMG_W ) / 2f ,0, DEAD_IMG_W , DEAD_IMG_H);

        game.batch.end();

        // Restore the old projection matrix
        game.batch.setProjectionMatrix(oldProjection);
        update();
    }
    private void changeScreen() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(this.game));
        }
        if(Gdx.input.isTouched()) {
            game.setScreen(new MenuScreen(this.game));
        }
    }

    @Override
    public void resize(int i, int i1) {
        ;
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
        goMenuImg.dispose();

    }
}
