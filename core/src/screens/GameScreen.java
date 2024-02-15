package screens;

import Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CatGame;
import sprites.Player;

import static utils.Constants.PlayerConstants.*;

public class GameScreen implements Screen {
    Texture texture;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private CatGame game;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    TextureAtlas atlas;

    public GameScreen(CatGame game){
        this.game = game;
        //texture = new Texture("GerasBackground.png");
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(CatGame.SCREEN_WIDTH / CatGame.PPM, CatGame.SCREEN_HEIGHT / CatGame.PPM, gameCamera);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tiled/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1 / CatGame.PPM);
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2, 0);
        world = new World(new Vector2(0,-10), true );
        b2dr = new Box2DDebugRenderer();
        player = new Player(world, this, "1");
        new B2WorldCreator(world, map);
        atlas = new TextureAtlas("Player.atlas");
    }
    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(1f, 0),player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-1f, 0),player.b2body.getWorldCenter(), true);

    }
    public void update(float dt) {
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);

        gameCamera.position.x = player.b2body.getPosition().x;
        gameCamera.update();
        renderer.setView(gameCamera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        game.batch.setProjectionMatrix(gameCamera.combined);
        b2dr.render(world, gameCamera.combined);
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();


    }
}
