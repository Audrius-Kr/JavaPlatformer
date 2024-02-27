package screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.CatGame;

public class MapEditor implements Screen {
    CatGame game;
    String tilesetPath = "tiled/SET1_Mainlev_build_crop.png";
    Texture tileset;
    Viewport tilesetViewport, mapViewport;
    OrthographicCamera tileCamera, mapCamera;
    ShapeRenderer shapeRenderer;
    Label.LabelStyle labelStyle;
    Label positionLabel;

    float screenWidth = 640;
    float screenHeight = 400;
    float tilesetAspectRatio;
    float cameraWidth = 1280;
    float cameraHeight = 800;
    float zoomSpeed = 0.1f;
    float PPM = CatGame.PPM;
    float tilesetWidth;






    public MapEditor(CatGame game) {
        this.game = game;
        tileset = new Texture(tilesetPath);
        tilesetAspectRatio = (float) tileset.getWidth() / tileset.getHeight();
        mapCamera = new OrthographicCamera();
        tileCamera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        tilesetWidth = screenWidth / 3 * 2 / PPM;
        mapCamera.setToOrtho(false, cameraWidth/PPM, cameraHeight/PPM);
        mapCamera.position.set(mapCamera.viewportWidth / 2, mapCamera.viewportHeight / 2 , 0);
        mapCamera.update();
        mapViewport = new ExtendViewport(screenWidth/ PPM, screenHeight / PPM , mapCamera);
        mapViewport.setScreenBounds(0, 0, (int) ((float)Gdx.graphics.getWidth() * 2f / 3f), Gdx.graphics.getHeight());

        InputProcessor inputProcessor = new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                float minZoom = 0.01f; // Adjust as needed
                float maxZoom = 10f; // Adjust as needed
                mapCamera.zoom = Math.max(minZoom, Math.min(maxZoom, mapCamera.zoom + amountY * zoomSpeed));
                mapCamera.update();
                return true;
            }
        };
        Gdx.input.setInputProcessor(inputProcessor);








    }
    @Override
    public void show() {

    }



    float getHeightFromAR(float AR, float width) {
        return (width / AR /PPM);
    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapViewport.apply();
        mapCamera.update();
        shapeRenderer.setProjectionMatrix(mapCamera.combined);
        game.batch.setProjectionMatrix(mapCamera.combined);

        game.batch.begin();
            game.batch.draw(tileset,Gdx.graphics.getWidth() * 2f / 3f, (Gdx.graphics.getHeight() / PPM - tilesetWidth) / 2f, tilesetWidth, getHeightFromAR(tilesetAspectRatio, tilesetWidth));
        game.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);  // Set color to white

        float width = mapCamera.viewportWidth;
        float height = mapCamera.viewportHeight;

        for (float i = 0; i <= width; i += 16/PPM){
            shapeRenderer.line(i, height, i, 0);
            for (float j = 0; j <= height; j += 16/PPM){
                shapeRenderer.line(0, height - j, width, height - j);
            }
        }
        shapeRenderer.end();




        handleInput();



    }

    @Override
    public void resize(int i, int i1) {
        mapViewport.update(i, i1);
        mapViewport.setScreenBounds(0, 0, (int) ((float)Gdx.graphics.getWidth() * 2f / 3f), Gdx.graphics.getHeight());

    }


    private void handleInput() {
        float cameraMoveSpeed = 5.0f; // Adjust as needed
         // Adjust as needed

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mapCamera.position.x += cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mapCamera.position.x -= cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mapCamera.position.y -= cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mapCamera.position.y += cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }



        mapCamera.update();
    }
//    Void getMousePos(Label label, Game game) {
//        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//        mapCamera.unproject(mousePos); // Convert mouse position to world coordinates
//        label.setText("X: " + mousePos.x + ", Y: " + mousePos.y);
//        label.setPosition(mousePos.x, mousePos.y);
//        label.draw(game.batch,1);
//
//
//    }

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
