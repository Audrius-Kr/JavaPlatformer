package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.CatGame;
import jsonParse.ParseJson;
import jsonParse.SpriteFrame;
import screens.GameScreen;

public abstract class Entity extends Sprite {
    public World world;
    public static final int CIRCLE_RADIUS = 16;
    public static final int SPRITE_SIZE = 32;
    public Body b2body;
    public FixtureDef fdef;
    public ParseJson parseJson;
    public SpriteFrame spriteFrame;
    public TextureRegion[] textureRegions;
    public Texture texture;
    public float x;
    public float y;



    public Entity(World world, String name, float x, float y) {
        this.world = world;
        this.x = x;
        this.y = y;
        loadSpriteFrames(name, x, y);
        defineEntity();
    }
    public abstract void update (float dt);
    public void loadSpriteFrames(String name, float x, float y) {
        texture = new Texture(name +".png");
        parseJson = new ParseJson(name +".json");

        spriteFrame = parseJson.getSpriteFrame();
        int i = 0;
        textureRegions = new TextureRegion[spriteFrame.frames.size()];
        for (SpriteFrame.Frame frame : spriteFrame.frames) {
            textureRegions[i] = new TextureRegion(texture, frame.frameDetails.x, frame.frameDetails.y, frame.frameDetails.w, frame.frameDetails.h);
            i++;
        }


        setBounds(x, y, SPRITE_SIZE / CatGame.PPM, SPRITE_SIZE / CatGame.PPM);

    }
    public void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y );
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body = world.createBody(bdef);
        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(CIRCLE_RADIUS / CatGame.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);




    }
    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getX() {
        return x;
    }
}
