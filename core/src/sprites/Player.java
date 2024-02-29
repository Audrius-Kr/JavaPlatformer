package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.CatGame;
import screens.GameScreen;
import utils.Constants;

import static com.mygdx.game.CatGame.CATEGORY_NOTHING;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {


    private float elapsedTime = 0;
    private int playerStatus = IDLE;
    private int previousStatus = IDLE;
    public static final float ANIMATION_DELAY = .3f;
    public static final float PLAYER_SPEED_X = 1.0f;
    private int aniIndex;
    public boolean dead = false;
    public boolean finished = false;
    private boolean notMoving = false;
    Music jump, playDead, finish;

    public Player(World world, float x, float y) {
        super(world, "2", x / CatGame.PPM, y / CatGame.PPM);
        jump = Gdx.audio.newMusic(Gdx.files.internal("jump.wav"));
        playDead = Gdx.audio.newMusic(Gdx.files.internal("dead.mp3"));


    }

    public void update(float dt) {
        elapsedTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((b2body.getPosition().y < 0) && !dead){
            playerDead();
        }
        if (!notMoving) {
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
            if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0 && b2body.getLinearVelocity().x <= .5f) {
                previousStatus = playerStatus;
                playerStatus = IDLE;
                if (previousStatus != playerStatus) {
                    aniIndex = 0;
                }

            }
            if (b2body.getLinearVelocity().x > 0 && b2body.getLinearVelocity().y <= 0.5f) {
                previousStatus = playerStatus;
                playerStatus = RUNNING_RIGHT;
                if (previousStatus != playerStatus) {
                    aniIndex = 0;
                }

            }
            if (b2body.getLinearVelocity().x < 0 && b2body.getLinearVelocity().y <= 0.5f) {
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
    public void addExtraGravity() {
        Vector2 velocity = b2body.getLinearVelocity();
        velocity.y = GameScreen.GRAVITY_STRENGTH;
        b2body.setLinearVelocity(velocity);
    }
    public void handleInput(float dt) {
        if (!notMoving){
            if ((Gdx.input.isKeyJustPressed(Input.Keys.UP)) && b2body.getLinearVelocity().y == 0) {
                jump.play();
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2body.getLinearVelocity().x <= Player.PLAYER_SPEED_X)
                b2body.applyLinearImpulse(new Vector2(1f, 0), b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2body.getLinearVelocity().x >= -(Player.PLAYER_SPEED_X))
                b2body.applyLinearImpulse(new Vector2(-1f, 0), b2body.getWorldCenter(), true);
        }

    }
        public void playerDead() {
            playDead.play();
            this.dead = true;
            this.notMoving = true;
            Filter filter = new Filter();
            filter.maskBits = CATEGORY_NOTHING;
            for (Fixture fixture : b2body.getFixtureList()){
                fixture.setFilterData(filter);
            }
            flip(false, true);
            b2body.applyLinearImpulse(new Vector2(0, 8f), b2body.getWorldCenter(), true);
        }
        public void playerFinished() {
        this.finished = true;
        this.notMoving = true;
        }
        public void setXVelocity (float velo){
            Vector2 velocity = b2body.getLinearVelocity();
            velocity.x = 0;
            b2body.setLinearVelocity(velocity);
        }
    }


