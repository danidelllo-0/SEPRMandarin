package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.DuckTator;
import Scenes.Hud;
import Screens.Rounds.JamesCollege;

public class Morgan extends Sprite{
	
	//Enumeration for our ducks states.
	public enum State {FALLING,JUMPING,STANDING,RUNNING};
	//Creating our variables
	public State currentState;
	public State previousState;
	private Animation duckRun;
	private boolean runningRight;
	
	//This keeps track of how long we're in a state, e.g. how long we're in a running state.
	private float stateTimer;
	
	//Our box2D world
	public World world;
	
	//Morgans box 2d body!
	public Body duck_b2Body;
	
	//Game
	DuckTator game;
	
	//atlas
	TextureAtlas atlas;
	
	//Morgan standing
	private TextureRegion duckStand;
	
	Timer gtimer = new Timer();
	float timeState=0f; 
	
	public Morgan(World world, DuckTator game, TextureAtlas atlas ){
		super(atlas.findRegion("ducky"));
		
		//Initialising variables
		this.world = world;
		this.game = game;
		this.atlas = atlas;
		
		//****INITIALISATION THE STATES****
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		//Creating an array of texture regions (this is going to hold the different textures
		//for Morgan running.
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i=1; i<4; i++)
			//move 125 pixels along the spritesheet, then take the width is 70 and the height is 66.
			frames.add(new TextureRegion(getTexture(),i*125,0,70,66));
		//Creating our animation using our frame array.
		duckRun = new Animation(0.1f,frames);
		frames.clear();
		
		//Calling our box2D definition of Morgan.
		defineMorgan();
		
		//New TextureRegion object - setting it equal to the area of the spritesheet representing Morgan just standing.
		duckStand = new TextureRegion(getTexture(),0,0,70,66);
		//How big we should draw Morgan.
		setBounds(0,0,32/DuckTator.PPM,32/DuckTator.PPM);
		setRegion(duckStand);
		
	}
	
	public void update(float dt){
		//remember b2d is in the center
		setPosition(duck_b2Body.getPosition().x - getWidth()/2, duck_b2Body.getPosition().y-getHeight()/2);
		
		//This is setting our box2d representation to the animation frame.
		setRegion(getFrame(dt));


	}
	
	public TextureRegion getFrame(float dt){
		/*This method will return the frame we need to display as the sprites texture
		* We compute what state the duck is in - standing or running?
		* Statetimer determines what frame gets pulled
		* from the animation. If it gest passed 0.1f it will move to the next one */

		currentState = getState();
		TextureRegion region;
		
		/*Depending on what state we're in we will use a different animation
		* This will allow for a jumping duck/falling duck etc to be implemented
		* but for the moment only the running animation is used.*/
		switch(currentState){

			case RUNNING:
				/* stateTimer decides what frame gets rendered from the animation.
				/* If the stateTimer is passed in 0.1f it will advance to the next frame.
				/* The boolean true just means it's a looping animation so it will 
				/* start from the beginning once it finishes. */
				region = duckRun.getKeyFrame(stateTimer,true);
				break;
			default:
				region = duckStand;
				break;													
		}
		
		/* If he's running in a positive x direction we need to draw him to the right.
		* If he's running in a negative x direction we need to draw him to teh left.
		* we need to remember if he's running to the right!
		* So he's moving 
		* flipx = returns true if the texture is flipped, so duck going left
		* This is just saying, If Morgan is running to the left (as the body has negative linear
		* velocity OR the boolean 'runningRight' is false. AND the region HAS NOT BEEN FLIPPED */
		
		if ((duck_b2Body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
			//Then we need to flip the region in the x axis.
			region.flip(true, false);
			//Need to update our boolean so we know Morgan is running to the left and the texture is.
			runningRight = false;
		}
		
		/*If the b2Body is running right due to a positive linear velocity,
		* OR the boolean says he's running right AND the region HAS BEEN FLIPPED meaning the sprite
		* is facing to the left. */
		else if ((duck_b2Body.getLinearVelocity().x > 0 || runningRight)&&region.isFlipX()){
			//We need to flip the region in the x axis so the sprite is facing to the RIGHT 
			region.flip(true, false);
			//Updating our boolean.
			runningRight = true;
		}
		
		/*Need to set the statetimer.
		* Does our current state equal our previous state?
		* If it does then our state timer + dt.
		* ELSE the stateTimer will equal 0. If it doesn't equal the previous state we must
		* have transitioned to a new state so we need to reset the timer. */
		stateTimer = currentState == previousState? stateTimer + dt : 0;
		previousState = currentState;
		return region;
		
	}
	
	public State getState(){
		//state is based on B2Body
		//So we are just testing whether the duck's b2Body has a velocity upwards/downwards etc.
		if(duck_b2Body.getLinearVelocity().y >0 || duck_b2Body.getLinearVelocity().y<0 && previousState == State.JUMPING)
			return State.JUMPING;
		
		else if(duck_b2Body.getLinearVelocity().y<0)
			return State.FALLING;
		else if (duck_b2Body.getLinearVelocity().x !=0)
			return State.RUNNING;
		else
			return State.STANDING;
	}

	private void defineMorgan() {
		/*Example:
		* The body is the 'name' - CAR
		* The fixtures that make up the card are the two circles for wheels and the square for the driver.
		* We have already created a BODY called buck_b2Body. Now we need to define our body.
		* First we need to define our body. At the moment it is just a shell.	*/	
		
		BodyDef bdef = new BodyDef();
		//Initial position of the duck's B2DBody
		bdef.position.set(1200/DuckTator.PPM,320/DuckTator.PPM);
		//Ducks going to be a dynamic body meaning
		bdef.type = BodyDef.BodyType.DynamicBody;		
		//Creating the body within our world.
		duck_b2Body = world.createBody(bdef);
		
		
		//Secondly we need to define our fixtures. These are the physical attributes of our body.
		FixtureDef fdef = new FixtureDef();
		//We need our duck to be represented by a shape. We will use a circle!
		CircleShape shape = new CircleShape();
		//Set the radius to five, increasing will make the duck's B2dBody larger.
		shape.setRadius(12/DuckTator.PPM);
		
		//Setting our fixture equal to our shape 
		fdef.shape = shape;
		//Category bit is the "WHAT IS THIS SHAPE"
		fdef.filter.categoryBits = DuckTator.DUCK_BIT;
		//What can duck collide with:
		fdef.filter.maskBits = DuckTator.GROUND_BIT | DuckTator.WATER_BIT| DuckTator.BRICK_BIT | DuckTator.BOMB_BIT| DuckTator.CAGE_BIT| DuckTator.BOMB_BIT| DuckTator.GROUND_OBJECT| DuckTator.FEATHER_BIT| DuckTator.HEALTH_BIT | DuckTator.ENEMY_HEAD| DuckTator.SPIKE_BIT| DuckTator.ENEMY_BIT;
		//Now we need to add the fixture we've just created (the circle) to our body.
		duck_b2Body.createFixture(fdef).setUserData(this);;


	}
	
	public void handle_input(float dt){
		/*Called each time the game renders. Checks to see if the various input keys are pressed. If they are
		* then a linear impulse is applied to the players box2d body in the corresponding direction. 
		* Using a timer to ensure that the space bar cannot be continuously pressed allowing Morgan
		* to fly through the entire map.*/
		
		timeState+=Gdx.graphics.getDeltaTime();
		
		if (timeState >= 0.2f){
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && (getState() != Morgan.State.STANDING ) ){
				duck_b2Body.applyLinearImpulse(new Vector2(0,2.1f), duck_b2Body.getWorldCenter(), true);
				timeState = 0;
				//System.out.println("FLYING");
			}
		}
		
		//To move up we apply a linear impulse in the (0,5f) direction, so nothing in the x, 0.5 in the y.
		if(((Gdx.input.isKeyJustPressed(Input.Keys.UP)) | (Gdx.input.isKeyJustPressed(Input.Keys.W)) && (getState() != Morgan.State.JUMPING )) ){
			duck_b2Body.applyLinearImpulse(new Vector2(0,4f), duck_b2Body.getWorldCenter(), true);
			//System.out.println("JUMPING");
		}
		//Owen - Stops the smooth deceleration of running. Once running keys have stopped being pressed the character becomes stationary instantly
		if ((getState() == Morgan.State.RUNNING) && (!((Gdx.input.isKeyPressed(Input.Keys.RIGHT)) | (Gdx.input.isKeyPressed(Input.Keys.D)) | (Gdx.input.isKeyPressed(Input.Keys.LEFT)) | (Gdx.input.isKeyPressed(Input.Keys.A))))) {
			duck_b2Body.setLinearVelocity(0,0);
		}
		//To move to the right we apply a linear impulse in the positive x.
		if(((Gdx.input.isKeyPressed(Input.Keys.RIGHT) | (Gdx.input.isKeyPressed(Input.Keys.D))) && (duck_b2Body.getLinearVelocity().x<=2))){
			duck_b2Body.applyLinearImpulse(new Vector2(0.3f,0), duck_b2Body.getWorldCenter(), true);
			//System.out.println("MOVING RIGHT");
		}
		
		//To move to the left we apply a linear impulse in the negative x.
		if(((Gdx.input.isKeyPressed(Input.Keys.LEFT) | (Gdx.input.isKeyPressed(Input.Keys.A)))  && (duck_b2Body.getLinearVelocity().x>=-2))){
			duck_b2Body.applyLinearImpulse(new Vector2(-0.3f,0), duck_b2Body.getWorldCenter(), true);
			//System.out.println("MOVING LEFT");
		}
		
		//If R is pressed and regeneration is true allow the health to be regenerated
		if (Gdx.input.isKeyPressed(Input.Keys.R) && (DuckTator.REGENERATION == true)){
			Hud.regenerateHealth();
		}
	}
	
	public void dead_morgan()
	{	
		//If Morgan is killed set the screen to the beginning of the level.
		game.setScreen(new JamesCollege(game));
	}
	
	public void unlock_regeneration(){
		//Updates the boolean so regeneration can be used.
		DuckTator.REGENERATION = true;
	}


}
