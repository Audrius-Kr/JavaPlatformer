package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.CatGame;

public class MenuScreen implements Screen {


    float screenMiddleX = Gdx.graphics.getWidth() ;
    float tenthOfScreenY = Gdx.graphics.getHeight() / 10f;
    float tenthOfScreenX = Gdx.graphics.getWidth() / 10f;
    float buttonWidth = tenthOfScreenX * 3f ;
    float buttonHeight = tenthOfScreenY * 2f;
    float spaceBetweenButtons = tenthOfScreenY * 2 ;

    CatGame game;
    int w = 608;
    int h = 208;
    Texture menuItems;
    Texture menuItemsHoover;
    TextureRegion playButton;
    float playButtonY = buttonHeight *2 + spaceBetweenButtons*2;
    float menuButtonY = buttonHeight + spaceBetweenButtons;
    float editButtonY = 0 ;
    TextureRegion playButtonHoover, menuButton, menuButtonHoover;
    TextureRegion editButton;
    TextureRegion editButtonHoover;
    float mouseX, mouseY;


    public void update() {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
    }
    public MenuScreen(CatGame game) {
        //800x270
        this.game = game;
        menuItems = new Texture("Menu Buttons sprite (Colored).png");
        menuItemsHoover = new Texture("Menu Buttons sprite (BnW).png");
        editButton = new TextureRegion(menuItems,2*w,3*h,w,h);
        editButtonHoover = new TextureRegion(menuItemsHoover,2*w,3*h,w,h);
        playButton = new TextureRegion(menuItems, 0,0,w,h);
        playButtonHoover = new TextureRegion(menuItemsHoover,0,0,w,h);
        menuButton = new TextureRegion(menuItems, 2*w, h,w,h);
        menuButtonHoover = new TextureRegion(menuItemsHoover, 2*w, h,w,h);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        update();
        float  buttonX = (screenMiddleX - buttonWidth)/2f;
        screenMiddleX = Gdx.graphics.getWidth() ;


        Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        game.batch.begin();
        drawButtonWithHoverEffect(buttonX,playButtonY, playButton, playButtonHoover);
        drawButtonWithHoverEffect(buttonX,editButtonY, editButton, editButtonHoover);
        drawButtonWithHoverEffect(buttonX,menuButtonY, menuButton, menuButtonHoover);
        game.batch.end();
        onClickChangeScreen(buttonX, playButtonY);


    }

    private void drawButtonWithHoverEffect(float x, float y, TextureRegion button, TextureRegion buttonHover) {

        if (mouseX < x|| mouseX > x  + buttonWidth || mouseY < y || mouseY > y + buttonHeight)
            game.batch.draw(button, x , y, buttonWidth, buttonHeight);
        else
            game.batch.draw(buttonHover, x, y, buttonWidth, buttonHeight);
    }
    void onClickChangeScreen(float x, float y) {
        if ((mouseX > x && mouseX < x  + buttonWidth && mouseY > y && mouseY < y + buttonHeight) && Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(this.game));
        }
    }

    @Override
    public void resize(int i, int i1) {
        game.batch.getProjectionMatrix().setToOrtho2D(0, 0, i, i1);
        tenthOfScreenY = Gdx.graphics.getHeight() / 10f;
        tenthOfScreenX = Gdx.graphics.getWidth() / 10f;
        buttonWidth = tenthOfScreenX * 3f ;
        buttonHeight = tenthOfScreenY * 2f;
        spaceBetweenButtons = tenthOfScreenY * 2 ;
        playButtonY = buttonHeight *2 + spaceBetweenButtons*2;
        menuButtonY = buttonHeight + spaceBetweenButtons;
        editButtonY = 0 ;

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        menuItems.dispose();
        menuItemsHoover.dispose();
    }
}
