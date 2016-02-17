package tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.DuckTator;
import Scenes.Hud;
import Sprites.Morgan;
import Sprites.Collectables.Feather;
import Sprites.Collectables.Health;
import Sprites.Collectables.Shield;
import Sprites.Enemy.BasketBomb;
import Sprites.Enemy.Enemy;
import Screens.LevelCompleted;

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
					((Enemy) fixA.getUserData()).hitOnBody(player);
				}else{
					((Enemy) fixB.getUserData()).reverseVelocity(true, false);
					((Enemy) fixB.getUserData()).hitOnBody(player);
				}
				break;
			
				//--------------------CHANGE------------------------------
				//Added handling of health and shield power-ups and swimming feature
				
				//TESTING IF THE DUCK HIT A HEART
			case DuckTator.DUCK_BIT | DuckTator.HEALTH_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.HEALTH_BIT){
					((Health) fixA.getUserData()).onBodyHit();
				}				
				else{
					((Health) fixB.getUserData()).onBodyHit();
				}
				break;
				
				//TESTING IF THE DUCK HIT A shield
			case DuckTator.DUCK_BIT | DuckTator.SHIELD_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.SHIELD_BIT){
					((Shield) fixA.getUserData()).onBodyHit();
				}				
				else{
					((Shield) fixB.getUserData()).onBodyHit();
				}
				break;
				
				//TETSING IF THE DUCK HIT WATER
			case DuckTator.DUCK_BIT | DuckTator.WATER_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.DUCK_BIT){
					((Morgan) fixA.getUserData()).inwater = true;
				}
				else {
					((Morgan) fixB.getUserData()).inwater = true;
				}
				break;
				//--------------------/CHANGE------------------------------
				
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
					((BasketBomb) fixA.getUserData()).hitOnBody(player);
				}
				else {
					
					((BasketBomb) fixB.getUserData()).hitOnBody(player);
				}
				break;
				
			//TESTING IF THE DUCK COLLIDED WITH THE CAGE	
			case DuckTator.DUCK_BIT | DuckTator.CAGE_BIT:
				if (fixA.getFilterData().categoryBits == DuckTator.DUCK_BIT){				
					}
				
				//--------------------CHANGE------------------------------
				//Changed due to the addition of new levels
				
			    //level is completed, unlock next college in order
				if (player.lvl==1){
					DuckTator.LANGWITH_UNLOCKED=true;
				}
				if (player.lvl==2){
					DuckTator.GOODRICKE_UNLOCKED=true;
				}
				if (player.lvl==3){
					DuckTator.HALIFAX_UNLOCKED=true;
				}
				if (player.lvl==4){
					DuckTator.DERWENT_UNLOCKED=true;
				}
				if (player.lvl==5) {
					DuckTator.ALCUIN_UNLOCKED=true;
				}
				if (player.lvl==6){
					DuckTator.VANBURGH_UNLOCKED=true;
				}
				if (player.lvl==7){
					DuckTator.JAMES_UNLOCKED=true;
				}
				
				Hud.addScore(5000);
				game.score=Hud.getScore();
				game.health=Hud.getHealth();
				game.setScreen(new LevelCompleted(game,player.lvl));//new JamesCompleted(game));
				//--------------------/CHANGE------------------------------
				
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
