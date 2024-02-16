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

public class Player extends Entity {


    private float elapsedTime = 0;
    private int playerStatus = IDLE;
    private int previousStatus = IDLE;
    public static final float ANIMATION_DELAY = .3f;
    public static final int PLAYER_SPEED_X = 3;
    private int aniIndex;
    private boolean dead;

    public Player(World world, GameScreen screen, float x, float y) {
        super(world, "2", x / CatGame.PPM, y / CatGame.PPM);

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

            setRegion(textureRegions[playerStatus + aniIndex]);
            if (elapsedTime >= ANIMATION_DELAY) {
                elapsedTime = 0;
                aniIndex++;
                if ((aniIndex + playerStatus >= Constants.PlayerConstants.getSpriteAmount(playerStatus)) || (aniIndex + playerStatus >= textureRegions.length))
                    aniIndex = 0;
            }

        }
    }


