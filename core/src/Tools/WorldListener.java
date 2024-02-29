package Tools;

import com.badlogic.gdx.physics.box2d.*;
import sprites.Entity;
import sprites.FinalCat;
import sprites.Player;
import sprites.Sensor;


public class WorldListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData()  instanceof Player || fixB.getUserData()  instanceof Player) {
            Fixture player = fixA.getUserData() instanceof Player ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            if(object.getUserData() != null && object.getUserData().equals("Spikes")){

                ((Player) player.getUserData()).playerDead();
            }
        }
        if (fixA.getUserData()  instanceof Player || fixB.getUserData()  instanceof Player) {
            Fixture player = fixA.getUserData() instanceof Player ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            if(object.getUserData() != null && object.getUserData() instanceof FinalCat){

                ((Player) player.getUserData()).playerFinished();
            }
        }
/*
            if ((fixA.getUserData() instanceof Sensor && ((((Sensor) fixA.getUserData()).sensorId.equals("Right")) && ((Sensor)fixA.getUserData()).name.equals("Player"))) || (fixA.getUserData() instanceof Sensor && (((((Sensor) fixA.getUserData())).sensorId.equals("Left")) && ((Sensor)fixA.getUserData()).name.equals("Player")))
                                                                                            ||
                    (fixB.getUserData() instanceof Sensor && (((((Sensor) fixB.getUserData())).sensorId.equals("Left")) && ((Sensor)fixB.getUserData()).name.equals("Player"))) || (fixB.getUserData() instanceof Sensor && (((((Sensor) fixB.getUserData())).sensorId.equals("Right")) && ((Sensor)fixB.getUserData()).name.equals("Player")))) {
                Fixture player = fixA.getUserData() instanceof Sensor ? fixA : fixB;
                Fixture object = player == fixA ? fixB : fixA;
                if(object.getUserData() != null && object.getUserData().equals("Ground")){

                    ((Player) player.getUserData()).setXVelocity(0);
                }
            }

        if ((fixA.getUserData() instanceof Sensor && (((((Sensor) fixA.getUserData())).sensorId.equals("Bottom")) && ((Sensor)fixA.getUserData()).name.equals("Player"))) || (fixB.getUserData() instanceof Sensor && ((((Sensor) fixA.getUserData())).sensorId.equals("Bottom")) && ((Sensor)fixB.getUserData()).name.equals("Player"))) {
            Fixture player = fixA.getUserData() instanceof Sensor ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            if(object.getUserData() != null && !(object.getUserData().equals("Ground"))){

                ((Player) player.getUserData()).addExtraGravity();
            }
        }



 */

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
