package screens;

import com.badlogic.gdx.*;
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
    Viewport tilesetViewport, mapViewport;
    OrthographicCamera tileCamera, mapCamera;
    ShapeRenderer shapeRenderer;

    float tileViewportWidth;
    float pixelsPerUnitWidth;
    float pixelsPerUnitHeight;
    float tileViewportHeight;
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

    //mapViewport
        mapCamera.setToOrtho(false, cameraWidth/PPM, cameraHeight/PPM);
        mapCamera.position.set(Gdx.graphics.getWidth() / PPM / 2f, Gdx.graphics.getHeight() / PPM / 2f , 0);
        mapCamera.update();
        mapViewport = new ExtendViewport(screenWidth/ PPM, screenHeight / PPM , mapCamera);
        mapViewport.setScreenBounds(0, 0, (int) ((float)Gdx.graphics.getWidth() * 2f / 3f), Gdx.graphics.getHeight());


    //tileViewport

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        tileCamera.setToOrtho(false, aspectRatio * screenHeight/PPM, screenHeight/PPM);
        tileCamera.update();
        tilesetViewport = new ExtendViewport(aspectRatio * screenHeight/PPM, screenHeight/PPM , tileCamera);
        tileCamera.position.set(tilesetViewport.getWorldWidth() / 2f, tilesetViewport.getWorldHeight() / 2f , 0);
        tileCamera.update();
        tilesetViewport.setScreenBounds((int) (Gdx.graphics.getWidth() * 2f / 3f), 0, (int) ((float)Gdx.graphics.getWidth() / 3f), Gdx.graphics.getHeight());



        InputProcessor inputProcessor = new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                float minZoom = 0.01f;
                float maxZoom = 10f;
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

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//tilesetViewport projection matrix
        tilesetViewport.apply();
        tileCamera.update();
        game.batch.setProjectionMatrix(tileCamera.combined);
        shapeRenderer.setProjectionMatrix(tileCamera.combined);


    // Get the viewport's world width and height
        tileViewportWidth = tilesetViewport.getWorldWidth();
        tileViewportHeight = tilesetViewport.getWorldHeight();
        pixelsPerUnitHeight = tilesetViewport.getWorldHeight() / tilesetViewport.getScreenHeight();
        pixelsPerUnitWidth = tilesetViewport.getWorldWidth() / tilesetViewport.getScreenWidth();

    // Calculate the image's width, height, and position
        float aspectRatio = (float) tileset.getWidth() / (float) tileset.getHeight();
        float imageHeight = tileViewportWidth / aspectRatio; // Preserve the image's aspect ratio
        float imageX = 0; // Draw the image at the left edge of the viewport
        float imageY = (tileViewportHeight - imageHeight) / 2; // Center the image on the y-axis
        System.out.println("tileViewportHeight " + tileViewportHeight + " over AR: " + (tileViewportHeight / aspectRatio));
        float tileSetGridHeight = mapCamera.viewportHeight;


        game.batch.begin();
            game.batch.draw(tileset, imageX, imageY, tileViewportWidth , imageHeight);
        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);  // Set color to white


        drawGrid(imageX, imageY, imageX + tileViewportWidth, imageY + imageHeight);


//mapViewport projection matrix
        mapViewport.apply();
        mapCamera.update();
        shapeRenderer.setProjectionMatrix(mapCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);  // Set color to white

        float width = mapCamera.viewportWidth;
        float height = mapCamera.viewportHeight;
        pixelsPerUnitHeight = mapViewport.getWorldHeight() / mapViewport.getScreenHeight();
        pixelsPerUnitWidth = mapViewport.getWorldWidth() / mapViewport.getScreenWidth();
        System.out.println(mapViewport.getWorldWidth() + " " + mapViewport.getScreenWidth());

        drawGrid(0,0, width, height);


        handleInput();



    }

    private void drawGrid(float x1, float y1, float x2, float y2) {
        int count = 0;
        for (float i = x1; i <= x2; i += 16 * pixelsPerUnitWidth){

            shapeRenderer.line(i, y1, i, y2);
            count++;
            for (float j = y1; j <= y2; j += 16 * pixelsPerUnitHeight){
                shapeRenderer.line(x1, y2 - j, x2, y2 - j);
            }
        }
        System.out.println(count);
        shapeRenderer.end();
    }

    @Override
    public void resize(int i, int i1) {
        mapCamera.position.set(cameraWidth / 2f / PPM, cameraHeight / 2f / PPM, 0);
        mapCamera.update();
        mapViewport.update(i, i1);
        mapViewport.setScreenBounds(0, 0, (int) ((float)Gdx.graphics.getWidth() * 2f / 3f), Gdx.graphics.getHeight());

        tilesetViewport.update(i, i1);
        tilesetViewport.setScreenBounds((int) (Gdx.graphics.getWidth() * 2f / 3f), 0, (int) ((float)Gdx.graphics.getWidth() / 3f), Gdx.graphics.getHeight());
        tileCamera.position.set(tilesetViewport.getWorldWidth() / 2f, tilesetViewport.getWorldHeight() / 2f , 0);
        tileCamera.update();
    }


    private void handleInput() {
        float cameraMoveSpeed = 5.0f; // Adjust as needed
         // Adjust as needed

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mapCamera.position.x -= cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mapCamera.position.x += cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mapCamera.position.y += cameraMoveSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mapCamera.position.y -= cameraMoveSpeed * Gdx.graphics.getDeltaTime();
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
