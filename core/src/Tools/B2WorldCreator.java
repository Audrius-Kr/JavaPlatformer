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
            body.createFixture(fdef);
        }
        finalCats = new Array<>();
        for(RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = object.getRectangle();
            finalCats .add(new FinalCat(world,screen, rect.getX() /2/CatGame.PPM, rect.getY() /2/ CatGame.PPM));

        }
    }
    public Array<FinalCat> getFinalCats() {
        return finalCats;
    }
}
