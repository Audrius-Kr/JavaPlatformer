package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.CatGame;

public class MapEditor implements Screen {
    CatGame game;
    String tilesetPath = "tiled/SET1_Mainlev_build_crop.png";
    Texture tileset;
    Viewport tilesetViewport;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    float screenWidth = CatGame.SCREEN_WIDTH;
    float screenHeight = CatGame.SCREEN_HEIGHT;
    float tilesetAspectRatio;
    float PPM = CatGame.PPM;
    float tilesetWidth = screenWidth / 3;

    /*
    Use extend viewport

     */




    public MapEditor(CatGame game) {
        this.game = game;
        tileset = new Texture(tilesetPath);
        camera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        tilesetViewport = new ExtendViewport(screenWidth/ PPM, screenHeight / PPM ,camera);
        tilesetAspectRatio = (float) tileset.getWidth() / tileset.getHeight();


    }
    @Override
    public void show() {

    }



    float getHeightFromAR(float AA, float width) {
        return (width / AA /PPM);
    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(tileset, tilesetWidth * 2 / PPM,(screenHeight / PPM - getHeightFromAR(tilesetAspectRatio,tilesetWidth)) / 2 , tilesetWidth/PPM, getHeightFromAR(tilesetAspectRatio,tilesetWidth));
        game.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);  // Set color to white
        for (float i = 0; i <= (tilesetWidth + 16) /PPM; i += 16/PPM){
            shapeRenderer.line((tilesetWidth * 2  / PPM + i) ,(screenHeight / PPM - getHeightFromAR(tilesetAspectRatio,tilesetWidth)) / 2, (tilesetWidth * 2 / PPM + i), (screenHeight / PPM + getHeightFromAR(tilesetAspectRatio,tilesetWidth)) / 2 );
            for (float j = 0; j <= (getHeightFromAR(tilesetAspectRatio,tilesetWidth)) + 16 / PPM; j += 16/PPM){
                shapeRenderer.line((tilesetWidth  * 2 / PPM ) ,(screenHeight / PPM - getHeightFromAR(tilesetAspectRatio,tilesetWidth)) / 2 + j, (tilesetWidth * 3 / PPM ), (screenHeight / PPM - getHeightFromAR(tilesetAspectRatio,tilesetWidth)) / 2 + j );
            }
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int i, int i1) {
        tilesetViewport.update(i, i1);
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
        shapeRenderer.dispose();
        tileset.dispose();
    }
}
