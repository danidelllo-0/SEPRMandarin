package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Screens.MenuScreen;


//*******************************************************************
//DuckTator
//http://stackoverflow.com/questions/25635636/eclipse-exported-runnable-jar-not-showing-images
//URL: https://moorhengames.wordpress.com/play-game/
//*******************************************************************


//Extending Game - so we can access the setScreen class.
public class DuckTator extends Game {
	//SpriteBatch is a CONTAINER that contains all our images
	//When rendering the spritebatch spills them and renders all. Just need one for the entire game.
	//This is because they're memory intensive.
	public SpriteBatch batch;

	//These are our virtual width and virtual height.
	public static final int V_WIDTH = 1024;
	public static final int V_HEIGHT = 512;
	
	//PPM = Pixels per meter. Box2d is simulating physics using meters, kilograms and seconds. If we created an
	//object of size 5, this would be 5m in the physics world. We scale everything so 1 meter is 100 pixels.
	public static final float PPM = 100;
	
	public int score = 0;
	public int health = 10;
	
	
	//*****FILTER BITS*****
	//Using powers of 2, as libgdx requires. These filter bits are used to detect collisions. The Box2D representation of
	//Morgan (the duck) has a category bit of 2. This just means we can always tell when morgan is colliding with something,
	//also allows us to define WHAT morgan and other fixtures can collide with.
	public static final short GROUND_BIT = 1;
	public static final short DUCK_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short HEALTH_BIT = 8;
	public static final short FEATHER_BIT = 16;
	public static final short SHIELD_BIT = 32;
	public static final short DESTROYED_BIT = 64;
	public static final short ENEMY_BIT = 128;
	public static final short ENEMY_HEAD = 256;
	public static final short GROUND_OBJECT = 512;
	public static final short SPIKE_BIT = 1024;
	public static final short BOMB_BIT = 2048;
	public static final short CAGE_BIT = 4096;
	public static final short STOPPER_BIT = 8192;
	public static final short WATER_BIT = 16384;
	
	//These booleans determine what levels are going to be unlocked on the world map.
	public static boolean JAMES_UNLOCKED = true;
	public static boolean CONSTANTINE_UNLOCKED = true;
	public static boolean ALCUIN_UNLOCKED = true;
	public static boolean GOODRICKE_UNLOCKED = true;
	public static boolean LANGWITH_UNLOCKED = true;
	public static boolean VANBURGH_UNLOCKED = true;
	public static boolean DERWENT_UNLOCKED = true;
	public static boolean HALIFAX_UNLOCKED = true;

	
	@Override
	public void create () {
		//We create our sprite batch which is going to be passed to all the other classes
		batch = new SpriteBatch();
		
		//Setting the screen to the MenuScreen
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		//This delegates the render method to whatever screen is active at the time.
		//We will initally delegate to the menu screen.
		super.render();
	}
	
	public void dispose(){
		getScreen().dispose();
		
	}
}
