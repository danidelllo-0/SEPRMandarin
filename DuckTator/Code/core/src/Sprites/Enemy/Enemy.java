package Sprites.Enemy;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DuckTator;
import Screens.GameOver;

public abstract class Enemy extends Sprite{
	
	protected World world;
	protected Screen screen;
	//Public so those that extend from enemy can access
	public Body b2Body;
	public Vector2 velocity;
	DuckTator game;

	//floats x,y are for position of the enemy.
	public Enemy(DuckTator game, World world, float x, float y){
		this.world = world;
		this.game = game;
		defineEnemy(x,y);
		velocity = new Vector2((float) 0.6,0);
		b2Body.setActive(false);
	}
	
	//Forcing every class that extends from enemy to implement these methods.
	protected abstract void defineEnemy(float x, float y);
	public abstract void hitOnHead();
	public abstract void hitOnBody();
	public abstract void reverseVelocity(boolean x, boolean y);
	public abstract void update(float dt);
	public void killed_morgan(){
		game.setScreen(new GameOver(game));
	}

}
