package Tools;

import com.badlogic.gdx.physics.box2d.*;
import sprites.Player;


public class WorldListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData()  instanceof Player || fixB.getUserData()  instanceof Player) {
            Fixture player = fixA.getUserData() instanceof Player ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;
System.out.println("Player");
            if(object.getUserData() != null && object.getUserData().equals("Spikes")){
                System.out.println("Spike");
                ((Player) player.getUserData()).playerDead();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
