package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.CatGame;
import jsonParse.ParseJson;
import jsonParse.SpriteFrame;
import screens.GameScreen;

import java.awt.*;
import java.awt.geom.RectangularShape;

public abstract class Entity extends Sprite {
    public World world;
    public static final int CIRCLE_RADIUS = 30;
    int spriteSize = CIRCLE_RADIUS *2 ;
    public Body b2body;
    float sensorWidth = 0.1f;
    float sensorHeight = 0.1f;
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


        setBounds(x, y, spriteSize / CatGame.PPM, spriteSize / CatGame.PPM);

    }
    public void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y );
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        fdef = new FixtureDef();
        PolygonShape leftSideShape = new PolygonShape();
        leftSideShape.setAsBox(sensorWidth / 2, spriteSize / CatGame.PPM/2, new Vector2(-(spriteSize / CatGame.PPM) / 2, 1/CatGame.PPM), 0);

        FixtureDef leftSideFixtureDef = new FixtureDef();
        leftSideFixtureDef.shape = leftSideShape;
        leftSideFixtureDef.isSensor = true;

        b2body.createFixture(leftSideFixtureDef).setUserData(new Sensor(this,this.getClass().getSimpleName(), "Left"));

// Create the right side sensor
        PolygonShape rightSideShape = new PolygonShape();
        rightSideShape.setAsBox(sensorWidth / 2, spriteSize / CatGame.PPM/2, new Vector2((spriteSize / CatGame.PPM) / 2, 1/CatGame.PPM), 0);

        FixtureDef rightSideFixtureDef = new FixtureDef();
        rightSideFixtureDef.shape = rightSideShape;
        rightSideFixtureDef.isSensor = true;
System.out.println(this);
        b2body.createFixture(rightSideFixtureDef).setUserData(new Sensor(this, this.getClass().getSimpleName(), "Right"));


        PolygonShape mainShape =new PolygonShape();
        mainShape.setAsBox(spriteSize / CatGame.PPM/2,spriteSize / CatGame.PPM/2);
        FixtureDef mainFixture = new FixtureDef();
        mainFixture.shape = mainShape;
        b2body.createFixture(mainFixture).setUserData(this);

        PolygonShape bottomSensor =new PolygonShape();
        bottomSensor.setAsBox(spriteSize / CatGame.PPM/2,0,new Vector2(0,-(spriteSize / CatGame.PPM/2) -1/CatGame.PPM ),0);
        FixtureDef bottomFixture = new FixtureDef();
        bottomFixture.isSensor = true;
        bottomFixture.shape = bottomSensor;
        b2body.createFixture(bottomFixture).setUserData(new Sensor(this, this.getClass().getSimpleName(), "Bottom"));





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
