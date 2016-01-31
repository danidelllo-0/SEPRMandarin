package Screens.Rounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.DuckTator;
import Sprites.Enemy.Enemy;
import Sprites.Enemy.Goose;
import tools.WorldContactListener;

public class JamesCollege extends Level implements Screen  {
	
	//Variables representing the layers in the Tiled2D map.
	private static final int BLOCKS_LAYER = 21;
	private static final int BOUNDARIES_LAYER = 22;
	private static final int GROUND_LAYER = 23;
	private static final int CAGE_LAYER = 24;
	private static final int DEATH_LAYER = 25;
	private static final int FEATHER_LAYER = 26;
	private static final int BUILDINGS_LAYER = 27;
	private static final int GEESE_LAYER = 28;
	private static final int STOPPERS_LAYER = 29;
	
	//Variable for our game.
	private DuckTator game;
	
	//Variable for the file path that links to the map rendered by this round.
	private static String mapString = "NewJames/JamesCollege/JamesCollege.tmx" ;

	//We'll be placing a 'boss' goose in this round. So we will be creating a Goose object.
	private Goose boss;
	
	/*We're going to use a timer to know when to reset the hud to black.
	 * When Morgan collides with a goose the hud will be coloured red. Then after 3 seconds has passed
	 * the render method here will reset it to black.
	 */
	 Timer gtimer = new Timer();
	float timeState=0f; 
	
	
	public JamesCollege(DuckTator game){
		//Calling the LEVEL class constructor. Passing in the map!
		super(game, mapString);
		
		//This brings in our game class, particularly allowing for us to use the SetScreen method!
		this.game = game;
		
		/*We pass in the layer on the tiled2Dmap that we want to draw in the box2D world. Also the
		*category bit (what we use to identify something on that layer) is passed in.
		*/
		//STOPPER LAYER - What geese will bounce off.
		b2_world_creator.rectangle_layer(STOPPERS_LAYER, DuckTator.STOPPER_BIT);
		//GROUND LAYER
		b2_world_creator.rectangle_layer(GROUND_LAYER, DuckTator.GROUND_BIT);
		//SPIKES LAYER
		b2_world_creator.rectangle_layer(DEATH_LAYER, DuckTator.SPIKE_BIT);
		//BRICKS LAYER
		b2_world_creator.rectangle_layer(BLOCKS_LAYER, DuckTator.BRICK_BIT);
		//CAGE LAYER
		b2_world_creator.rectangle_layer(CAGE_LAYER, DuckTator.CAGE_BIT);
		//BOUNDARIES LAYER - So Morgan can't fall off the map
		b2_world_creator.rectangle_layer(BOUNDARIES_LAYER, DuckTator.GROUND_BIT);
		//BUILDING LAYER
		b2_world_creator.rectangle_layer(BUILDINGS_LAYER, DuckTator.BRICK_BIT);
		
		/*The following methods draw in the box2d representations, but they create a feather object, goose object and a brick object. 
		*See the universal_b2WorldCreator for more detail.*/
		//FEATHER LAYER
		b2_world_creator.feather_layer(FEATHER_LAYER);
		//GOOSE LAYER
		b2_world_creator.goose_layer(GEESE_LAYER);
		//RANDOM BOMBS LAYER
		b2_world_creator.random_bombs();
		

		/* CONTACT LISTENER
		* WorldContactListener is a custom class. When a collision is
		* detected by box2D between two fixtures (e.g. Morgan and a goose)
		* the WorldContactListener defines what should happen. (e.g.
		* increase in points/health
		* */
		world.setContactListener(new WorldContactListener(game));
		
		//Calling the method to create the 'boss goose'
		bossGoose();
			
		
	}
	
	
	public void handleInput(float delta){
		/*This method is called via the update method which is called 60 times a second.
		* The Morgan class contains the implementation of his controls.	
		*/
		player.handle_input(delta);
	}
	

	
	public void bossGoose(){
		//Positioning the boss goose at the end of the level.
		boss = new Goose(game, world, 10500/DuckTator.PPM, 100/DuckTator.PPM);
		//Setting him larger than the standard goose.
		boss.setBounds(boss.getX(), boss.getY(), 200/DuckTator.PPM, 200/DuckTator.PPM);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		//We have to set the radius larger.
		shape.setRadius(55/DuckTator.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = DuckTator.ENEMY_BIT;
		fdef.filter.maskBits = DuckTator.GROUND_BIT | DuckTator.GROUND_OBJECT| DuckTator.BRICK_BIT| DuckTator.DUCK_BIT|DuckTator.STOPPER_BIT;
		boss.b2Body.setActive(true);
		boss.b2Body.createFixture(fdef).setUserData(boss);
		//We need to create a sensor on the goose's head so we can know when Morgan jumps on it.
		EdgeShape goose_head = new EdgeShape();
		goose_head.set(new Vector2(-40/DuckTator.PPM, 65/DuckTator.PPM), new Vector2(40/DuckTator.PPM, 65/DuckTator.PPM));
		fdef.shape = goose_head;
		fdef.filter.categoryBits = DuckTator.ENEMY_HEAD;
		//adds a little bounce when Morgan collides with the head fixture.
		fdef.restitution = 1f;
		fdef.isSensor = true;
		boss.b2Body.createFixture(fdef).setUserData(boss);
		
/*TEST FOR BASKETBALL BOMB RANDOM LOCATIONS. SEE TESTS CLASS FOR INSTRUCTIONS ON HOW TO RUN
		Array<BasketBomb> test = universal_b2WorldCreator.getBombs();
		int number = 1;
		for (Enemy enemy : test){
			if((enemy.b2Body.getPosition().x < 161) && (enemy.b2Body.getPosition().x > 0)){
				System.out.println(number);
			}
		number = number + 1;
		
		}
		*/
	}
	
	public void update (float delta){
		//Takes a float delta time. Updates everything in our world.
		
		//Checking for any inputs that occur
		handleInput(delta);
		
		/*Remember our world is the Box2D world.
		* In order to execute the physics simulation we need to tell it how many times to calculate
		* per second. world.step takes in a time stamp, velocity iteration/position iteration.
		* Velocity iteration/position - these are recommended b2Body values.
		* Timestamp means 1/60f so 60 times a second!*/
		world.step(1/60f, 6, 2);
		
		//Every time we update we need to update our player/boss goose to make sure the correct sprite is shown.
		player.update(delta);
		boss.update(delta);
		
		//In case the geese walk into the water, they only start moving when the duck is near. 
		for (Enemy enemy :b2_world_creator.getGeese()){
			enemy.update(delta);
			//waking up the body when the duck is close
			if(enemy.getX() < player.getX() + 1000/DuckTator.PPM )
				enemy.b2Body.setActive(true);
				
		}
		//The animation on the bombs only begins when the player is near. Included in case the bombs were
		//implemented to roll to make the levels more interesting / challenging. 
		for (Enemy enemy :b2_world_creator.getBombs()){
			enemy.update(delta);
			//waking up the body when the duck is close
			if(enemy.getX() < player.getX() + 1000/DuckTator.PPM )
				enemy.b2Body.setActive(true);
				
		}
		
		//Need to call our hud objects update method so the time/score/health is updated.
		hud.update(delta);
		
		/*we want to track our player so we're setting the gamecam equal to the position
		 *of the players box 2d body. Thus gamcam will follow the player as they move. 
		 */
		gamecam.position.x = player.duck_b2Body.getPosition().x;
		
		//Always need to update the camera anytime it moves.
		gamecam.update();
				
		//This links our tiled2D render to JUST the part of the map that the gamcam can see.
		tiled2DRenderer.setView(gamecam);
		
	}
	
	
	

	
	@Override
	public void render(float delta) {
	
		/*The render method is called continuously 
		* the delta float is the time passed since the last render. Equal to approximately 1/60 meaning
		* that the game is rendering with 60 frames per second. 
		*/
		
		/*Here we're summing up the time that's elapased and storing it into 'timeState'
		* Once 3 seconds has passed we will clear the screen to black - so the part of the screen that isn't
		* taken up by the game will be black. This is because this part of the screen may have been set
		* to red/yellow depending on whether Morgan collided with a feather/Goose.
		*/
		timeState+=Gdx.graphics.getDeltaTime();
		if (timeState >= 3f){
			timeState = 0f;
			Gdx.gl.glClearColor(0, 0, 0, 0);
		}
	
		//Clears the screen.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		
		//We need to recognise where the camera is in the game world, and only render what the camera can see.
		game.batch.setProjectionMatrix(gamecam.combined);
		
		//The first thing we want to call everytime the render method is called is the update function.
		update(delta);
		
		//****RENDERING THE TILED 2D MAP****
		tiled2DRenderer.render();
		
		/*RENDERING THE HUD*
		* Set projection matrix just means what is going to be shown by our camera.
		* We need to pass in the camera from the hud class - remember this camera doesn't follow the player,
		* so it will remain stationary. We then draw this! */
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		//****RENDER BOX2D LINES****
		//Comment out so you can't see the box2D representation.
		//b2dr.render(world, gamecam.combined);
		
		/*We don't want to render the entire map every frame - there's no point. We only want to render
		* what the camera can see at each frame. So we need to set the batch (we "spill" everything out of 
		* the batch to render it) to the gamecam (the camera that follows the player) thus, only what the
		* player can see will be rendered.	
		*/
		
		game.batch.setProjectionMatrix(gamecam.combined);
		
		//Opening our batch (remember we have 1 spritebatch shared among all the screens so we have to access
		//it through out game object.
		game.batch.begin();
		
		//Calling the players draw method, passing in the spritebatch. This will render Morgan to the screen.
		player.draw(game.batch);
		boss.draw(game.batch);
		
		//Drawing the goose / bombs. Iterating through all the objects of enemy in their respective arrays.
		//Then just drawing them
		for (Enemy enemy :b2_world_creator.getGeese())
			enemy.draw(game.batch);
		
		for (Enemy bomb :b2_world_creator.getBombs())
			bomb.draw(game.batch);
		
		//Always need to close the sprite batch!
		game.batch.end();
	}

	@Override
	public void dispose() {
		//When we are done, get rid of them!
		map.dispose();
		tiled2DRenderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
		
	}

	

}