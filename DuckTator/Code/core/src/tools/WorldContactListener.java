package tools;

import java.io.Console;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.DuckTator;
import Scenes.Hud;
import Screens.JamesCompleted;
import Sprites.Morgan;
import Sprites.Collectables.Feather;
import Sprites.Collectables.Health;
import Sprites.Enemy.BasketBomb;
import Sprites.Enemy.Enemy;
import Sprites.Morgan;

public class WorldContactListener implements ContactListener{
	//A contact listener is what is CALLED when two fixtures COLLIDE with each other.
	DuckTator game;
	Morgan player;
	
	public WorldContactListener(DuckTator game,Morgan player){
		this.game = game;
		this.player=player;
		
	}
	
	@Override
	public void beginContact(Contact contact) {
		/* This function gets called when two fixtures begin to make a connection.
		*A contact has a fixture A and a fixture B. We need to figure out which is which. */
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		
		/*We logically OR the two fixtures together using their category bits. Therefore "orFix" will be
		* unique to two fixtures colliding. */
		int orFix = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
	
		//switch statement using our orFix. Thus in the case the orFix is the same as two category bits we know they've collided
		/*
		if (fixA.getFilterData().categoryBits==DuckTator.DUCK_BIT)
		{
			System.out.println(fixB.getFilterData().categoryBits);
		}
		if (fixB.getFilterData().categoryBits==DuckTator.DUCK_BIT)
		{
			System.out.println(fixA.getFilterData().categoryBits);
		}
		*/
		switch (orFix){
			
		//TESTING IF THE DUCK COLLIDED WITH THE HEAD OF AN ENEMY
			case DuckTator.ENEMY_HEAD | DuckTator.DUCK_BIT:
				//Finding out which one is the enemey
				if (fixA.getFilterData().categoryBits == DuckTator.ENEMY_HEAD)
					/*If they've collided we can get the user data (remember we set the user data
					* equal to the object. Have to caste it as type Enemy, then we can access
					* the methods in the class! */
					((Enemy) fixA.getUserData()).hitOnHead();
				else
					((Enemy) fixB.getUserData()).hitOnHead();
				break;
			
			//TESTING IF AN ENEMY HIT A GROUND OBJECT.
			case DuckTator.ENEMY_BIT | DuckTator.GROUND_OBJECT:
				if (fixA.getFilterData().categoryBits == DuckTator.ENEMY_BIT){
					((Enemy) fixA.getUserData()).reverseVelocity(true, false);
				}
				else{
					((Enemy) fixB.getUserData()).reverseVelocity(true, false);
				}
				break;
				
			//TESTING IF THE ENEMY COLLIDES WITH A BRICK BIT	
			case (DuckTator.ENEMY_BIT | DuckTator.BRICK_BIT) :
				if (fixA.getFilterData().categoryBits == DuckTator.ENEMY_BIT){
					((Enemy) fixA.getUserData()).reverseVelocity(true, false);
				}
				else{
					((Enemy) fixB.getUserData()).reverseVelocity(true, false);
				}
				break;
				
			//TESTING IF THE ENEMY HAS COLLIDED WITH THE STOPPER BIT	
			case DuckTator.ENEMY_BIT | DuckTator.STOPPER_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.ENEMY_BIT){
					((Enemy) fixA.getUserData()).reverseVelocity(true, false);
				}
				else{
					((Enemy) fixB.getUserData()).reverseVelocity(true, false);
				}
				break;
				
							
				
			//TESTING IF AN ENEMY HIT THE DUCK
			case DuckTator.ENEMY_BIT | DuckTator.DUCK_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.ENEMY_BIT){
					((Enemy) fixA.getUserData()).reverseVelocity(true, false);
					((Enemy) fixA.getUserData()).hitOnBody();
				}else{
					((Enemy) fixB.getUserData()).reverseVelocity(true, false);
					((Enemy) fixB.getUserData()).hitOnBody();
				}
				break;
			
				
				//TESTING IF THE DUCK HIT A HEART
			case DuckTator.DUCK_BIT | DuckTator.HEALTH_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.HEALTH_BIT){
					((Health) fixA.getUserData()).onBodyHit();
				}				
				else{
					((Health) fixB.getUserData()).onBodyHit();
				}
				break;
				
				
			//TESTING IF THE DUCK HIT A FEATHER
			case DuckTator.DUCK_BIT | DuckTator.FEATHER_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.FEATHER_BIT){
					((Feather) fixA.getUserData()).onBodyHit();
				}				
				else{
					((Feather) fixB.getUserData()).onBodyHit();
				}
				break;
				
			//TESTING IF THE DUCK HIT THE SPIKES	
			case DuckTator.DUCK_BIT | DuckTator.SPIKE_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.DUCK_BIT){
					((Morgan) fixA.getUserData()).dead_morgan(player.lvl);
				}
				else {					
					((Morgan) fixB.getUserData()).dead_morgan(player.lvl);
				}
				break;
				
			//TESTING IF THE DUCK HIT A BOMB	
			case DuckTator.DUCK_BIT | DuckTator.BOMB_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.BOMB_BIT){
					((BasketBomb) fixA.getUserData()).hitOnBody();
				}
				else {
					
					((BasketBomb) fixB.getUserData()).hitOnBody();
				}
				break;
				
			//TESTING IF THE DUCK COLLIDED WITH THE CAGE	
			case DuckTator.DUCK_BIT | DuckTator.CAGE_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.DUCK_BIT){
					DuckTator.REGENERATION = true;
					DuckTator.CONSTANTINE_UNLOCKED = true;
					Hud.addScore(5000);
					game.setScreen(new JamesCompleted(game));
					
					}
				else {
					DuckTator.REGENERATION = true;
					DuckTator.CONSTANTINE_UNLOCKED = true;
					game.setScreen(new JamesCompleted(game));
					Hud.addScore(5000);
					
				}
				break;				
		}			
	}

	@Override
	public void endContact(Contact contact) {
		// When the two fixtures disconnect from each other.
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// Once something has collided you can change the characteristics of the collision.
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// the result of what happened due to the collision.
		
	}

}
