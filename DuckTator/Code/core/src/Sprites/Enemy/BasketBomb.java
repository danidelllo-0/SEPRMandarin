package Sprites.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DuckTator;
import Scenes.Hud;
import Sprites.Morgan;

public class BasketBomb extends Enemy {

	private Array<TextureRegion> frames;
	private TextureAtlas burning_atlas;
	private Animation burnAnimation;
	boolean setToDestroy;
	boolean destroyed;
	private float stateTimer;

	public BasketBomb(DuckTator game, World world, float x, float y) {
		//This passes the game, world, x and y into the constructor of the Enemy class.
		super(game, world, x, y);
		this.game = game;
		//An array that contains a number of TextureRegions. 
		frames = new Array<TextureRegion>();
		
		//creating a TextureAtlas object called burning_atlas. Now we can access the different
		//texture regions within the bombf sprite.
		burning_atlas = new TextureAtlas("BasketBomb/bombf.atlas");
		
		/*We loop over our burning_atlas and add all the individual images within it to our frames array.
		* Opening up the bombf atlas in notepad there is a region called 'bomb' this is what we find in 
		* the burning_atlas.findRegion call. The parameters passed in are
		* (110 - how far we move in the x direction in pixels, 0 - how far we move in the y direction in pixels,
		* (60 - The width we draw, 60 the height we draw). */
		for (int i = 0; i<3; i++){
			frames.add(new TextureRegion(burning_atlas.findRegion("bomb"),i*57,0,60,64));
		}
		/* So at the end of the for loop, the frame array contains 3 texture regions ( the regions that are
		* present in the spritesheet bombf.png)
		* Creating the animation using our frames. The floating point parameter determins how quickly it plays. */
		burnAnimation = new Animation(0.8f, frames);
		
		//Set the position and size of the sprite(the bomb) when it's drawn. 
		setBounds(getX(),getY(),64/DuckTator.PPM,64/DuckTator.PPM);
		
		stateTimer = 0;
		
		//If the bomb has been hit by the duck this boolean will be true, signalling we need to make it explode.
		setToDestroy = false;
		
		//If the bomb has exploded we will set it to destroyed.
		destroyed = false;
		
	}

	@Override
	protected void defineEnemy(float x, float y) {
		//A detailed explanation can be found in the Morgan class.
		//This class just produces a box2D representation of our enemy(the bomb).
		BodyDef bdef = new BodyDef();
		bdef.position.set(x,y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2Body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(17/DuckTator.PPM);
		//categoryNots is the "WHAT IS" this fixture
		fdef.filter.categoryBits = DuckTator.BOMB_BIT;
		fdef.restitution = 1f;
		//What the bomb can collide with:
		fdef.filter.maskBits = DuckTator.GROUND_BIT | DuckTator.GROUND_OBJECT| DuckTator.DUCK_BIT | DuckTator.BRICK_BIT ;
		fdef.shape = shape;
		b2Body.createFixture(fdef).setUserData(this);
		
	}

	@Override
	public void hitOnHead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hitOnBody(Morgan player) {
		Gdx.gl.glClearColor(255f, 0, 0, 0);
		//Set the boolean because we want to destroy the bomb.
		setToDestroy = true;
		//If his health is greater than 0 we want to decrement it.
		if (Hud.health_value > 0){
			Hud.decreaseHealth();
			
		//If his health is 0 and the bomb hits Morgan, we want to kill Korgan.
		}else if (Hud.health_value == 0 )
			killed_morgan(player);
		
		
	}

	@Override
	public void reverseVelocity(boolean x, boolean y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		//Following the same principles as the Goose class.
		stateTimer += dt;
		burnAnimation.getKeyFrame(stateTimer,true);

		//If it's setToDestroy meaning Morgan has collided with the bomb, but it is not yet destroyed. 
		if (setToDestroy && !destroyed){
			//We will destroy the body
			world.destroyBody(b2Body);
			//set the flag
			destroyed=true;
			//Setting the region to the exploded bomb using our atlas.
			setRegion(new TextureRegion(burning_atlas.findRegion("bomb"), 171,0,60,64));
			//resetting stateTimer
			stateTimer = 0;
		}
		
		//If the bomb isn't set to be destroyed and it isn't already destroyed we want to run the burn animation.
		else if (!setToDestroy && !destroyed){			
			setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y + 0.1f- getHeight()/2);
			setRegion(burnAnimation.getKeyFrame(stateTimer, true));
		}
		
	}
	
	public void draw(Batch batch){
		//We only draw the bomb if it isn't destroyed and the stateTimer is less than 1
		if (!destroyed || stateTimer < 1)
			super.draw(batch);
	}


	
}
