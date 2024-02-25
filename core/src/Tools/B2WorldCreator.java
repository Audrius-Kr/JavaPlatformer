package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CatGame;
import screens.GameScreen;
import sprites.FinalCat;




public class B2WorldCreator {
    private Array<FinalCat> finalCats;
    private static final float FRICTION = 0.2f;



    public B2WorldCreator(GameScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = object. getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / CatGame.PPM, (rect.getY() + rect.getHeight() / 2) / CatGame.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / CatGame.PPM, rect.getHeight() / 2 / CatGame.PPM);
            fdef.shape = shape;
            fdef.friction = FRICTION;
            body.createFixture(fdef).setUserData("Ground");
        }
        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = object. getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / CatGame.PPM, (rect.getY() + rect.getHeight() / 2) / CatGame.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / CatGame.PPM, rect.getHeight() / 2 / CatGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("Spikes");

        }


        finalCats = new Array<>();
        for(RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = object.getRectangle();
            finalCats .add(new FinalCat(world,screen, rect.getX() /CatGame.PPM, rect.getY() / CatGame.PPM));

        }
    }
    public Array<FinalCat> getFinalCats() {
        return finalCats;
    }
}
