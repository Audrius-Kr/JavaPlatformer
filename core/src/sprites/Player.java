package sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.CatGame;
import jsonParse.ParseJson;
import jsonParse.SpriteFrame;
import screens.GameScreen;
import utils.Constants;

import java.util.List;

import static utils.Constants.PlayerConstants.*;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    ParseJson parseJson;
    SpriteFrame spriteFrame;
    TextureRegion[] textureRegions;
    Texture texture;
    private float elapsedTime = 0;
    private int playerStatus = IDLE;
    private int previousStatus = IDLE;
    public static final float ANIMATION_DELAY = .3f;
    private int aniIndex;

    public Player(World world, GameScreen screen, String name) {
        texture = new Texture("2.png");
        parseJson = new ParseJson("2.json");
        spriteFrame = parseJson.getSpriteFrame();
        int i = 0;
        textureRegions = new TextureRegion[spriteFrame.frames.size()];
        for (SpriteFrame.Frame frame : spriteFrame.frames) {
            textureRegions[i] = new TextureRegion(texture, frame.frameDetails.x, frame.frameDetails.y, frame.frameDetails.w, frame.frameDetails.h);
            i++;
        }
        this.world = world;
        definePlayer();
        setBounds(0, 0, 64 / CatGame.PPM, 64 / CatGame.PPM);

    }

    public void update(float dt) {
        elapsedTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if (b2body.getLinearVelocity().y < 0 && previousStatus != JUMPING) {
            previousStatus = playerStatus;
            playerStatus = FALLING;
            if (previousStatus != playerStatus) {
                aniIndex = 0;
            }

        }
        if (b2body.getLinearVelocity().y > 0) {
            previousStatus = playerStatus;
            playerStatus = JUMPING;
            if (previousStatus != playerStatus) {
                aniIndex = 0;
            }

        }
        if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0) {
            previousStatus = playerStatus;
            playerStatus = IDLE;
            if (previousStatus != playerStatus) {
                aniIndex = 0;
            }

        }
        if (b2body.getLinearVelocity().x > 0 && b2body.getLinearVelocity().y == 0) {
            previousStatus = playerStatus;
            playerStatus = RUNNING_RIGHT;
            if (previousStatus != playerStatus) {
                aniIndex = 0;
            }

        }
        if (b2body.getLinearVelocity().x < 0 && b2body.getLinearVelocity().y == 0) {
            previousStatus = playerStatus;
            playerStatus = RUNNING_LEFT;
            if (previousStatus != playerStatus) {
                aniIndex = 0;
            }

        }
        setRegion(textureRegions[playerStatus+aniIndex]);
        if(elapsedTime >= ANIMATION_DELAY) {
            elapsedTime = 0;
            aniIndex++;
            if ((aniIndex+playerStatus >= Constants.PlayerConstants.getSpriteAmount(playerStatus)) || (aniIndex+playerStatus >=textureRegions.length))
                aniIndex =0;
        }
        System.out.println(aniIndex);


    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / CatGame.PPM, 100 / CatGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / CatGame.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);

    }





}
