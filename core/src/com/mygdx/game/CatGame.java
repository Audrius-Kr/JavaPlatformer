package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.GameScreen;
import screens.MapEditor;
import screens.MenuScreen;

public class CatGame extends Game {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 800;
	public static final float PPM = 100;
	public static final short CATEGORY_NOTHING = 0;
	public static final short CATEGORY_PLAYER = 1;

	public SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MapEditor(this));
	}
	public static float getScreenWidth() {
		return Gdx.graphics.getWidth();
	}
	public static float getScreenHeight() {
		return Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
