package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import screens.GameScreen;


public class FinalCat extends Entity {
    private Animation <TextureRegion> idleAnimation;
    private float elapsedTime = 0;


    public FinalCat(World world, GameScreen screen, float  x, float y) {
        super(world, "smallNiceCat", x, y);
        this.idleAnimation = new Animation<TextureRegion>(0.8f, textureRegions);

    }

    public TextureRegion getIdleFrame() {
        return idleAnimation.getKeyFrame(elapsedTime, true);

    }

    @Override
    public void update(float dt) {
        elapsedTime += dt;
        x = b2body.getPosition().x - getWidth() / 2;
        y = b2body.getPosition().y - getHeight() / 2;


    }
}
