package utils;

public class Constants {
    public static class PlayerConstants{
        public static final int DEAD = 8;
        public static final int RUNNING_RIGHT = 3;
        public static final int RUNNING_LEFT = 5;
        public static final int IDLE = 1;
        public static final int JUMPING = 8;
        public static final int  FALLING = 9;
        public static final int CROUCH = 7;

        public static int getSpriteAmount(int player_action){
            switch(player_action) {
                case RUNNING_RIGHT:
                    return 5;
                case IDLE:
                    return 3;
                case JUMPING:
                    return 9;
                case FALLING:
                    return 10;
                case RUNNING_LEFT:
                    return 7;
                case CROUCH:
                    return 8;


            }

        return 0;}
    }
}
