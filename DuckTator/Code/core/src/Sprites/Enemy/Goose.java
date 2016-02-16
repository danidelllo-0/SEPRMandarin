package Sprites.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DuckTator;
import Scenes.Hud;
import Sprites.Morgan;


public class Goose extends Enemy{
	
	private float stateTimer;
	private Animation walkAnimation;
	private Array<TextureRegion> frames;
	private TextureAtlas atlas;
	boolean setToDestroy;
	boolean destroyed;
	private boolean runningRight;


	public Goose(DuckTator game,World world, float x, float y,int lvl_num) {
		//The goose class works in much the same way as the BaskeBomb class which is documented more, so
		//review that!
		super(game,world, x, y);
		this.game = game;
		
		//pick correct texture for a goose depending on college
		String tex_name = "";
		if (lvl_num==1) tex_name="constgeese_f";
		if (lvl_num==2) tex_name="langwithgoose_f";
		if (lvl_num==3) tex_name="goodrickegoose_f";
		if (lvl_num==4) tex_name="haligeese_f";
		if (lvl_num==5) tex_name="derwentgoose_f";
		if (lvl_num==6) tex_name="alcuingoose_f";
		if (lvl_num==7) tex_name="vanbrughgeese_f";
		if (lvl_num==8) tex_name="goose_f";
		
		
		//****See the Goose class/Morgan. The Goose's animation works in exactly the same way ****
		
		frames = new Array<TextureRegion>();
		atlas = new TextureAtlas("NewGoose/"+tex_name+".pack");		
		for (int i = 0; i<4; i++){
			frames.add(new TextureRegion(atlas.findRegion("goosef"),i*157,0,130,104));
		}
		walkAnimation = new Animation(0.35f, frames);
		stateTimer = 0;
		setToDestroy = false;
		setBounds(getX(),getY(),64/DuckTator.PPM,64/DuckTator.PPM);
		destroyed = false;
		runningRight = true;
		
	}
	
	public void update(float dt){
		stateTimer += dt;
		TextureRegion region;
		region = walkAnimation.getKeyFrame(stateTimer,true);
		if (runningRight == false)
			region.flip(true, false);
		//check to see if its destroyed
		if (setToDestroy && !destroyed){
			world.destroyBody(b2Body);
			destroyed = true;
			setRegion(new TextureRegion(atlas.findRegion("goosef"), 628,0,130,104));
			stateTimer = 0;
		}
		else if (!destroyed){
			b2Body.setLinearVelocity(velocity);
			setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y +0.1f - getHeight()/2);
			setRegion(getFrame(dt));
			
		}
		
		
	}
	
	public TextureRegion getFrame(float dt){
		TextureRegion region;
		region = walkAnimation.getKeyFrame(stateTimer,true);										
		
		if ((b2Body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
			region.flip(true, false);
			runningRight = false;
		}
		else if ((b2Body.getLinearVelocity().x > 0 || runningRight)&&region.isFlipX()){
			region.flip(true, false);
			runningRight = true;
		}

		return region;
		
	}

	@Override
	protected void defineEnemy(float x, float y) {
		// The goose is defined in the same way Morgan is.
		BodyDef bdef = new BodyDef();
		bdef.position.set(x,y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2Body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(15/DuckTator.PPM);
		//Setting what the duck can collide with
		//category is the "WHAT IS" this fixture
		fdef.filter.categoryBits = DuckTator.ENEMY_BIT;
		//What can goose collide with:
		fdef.filter.maskBits = DuckTator.GROUND_BIT | DuckTator.GROUND_OBJECT| DuckTator.BRICK_BIT| DuckTator.DUCK_BIT|DuckTator.STOPPER_BIT;
		fdef.shape = shape;
		b2Body.createFixture(fdef).setUserData(this);
		
		//We need to create a sensor on the goose's head so we can know when Morgan jumps on it.
		EdgeShape goose_head = new EdgeShape();
		goose_head.set(new Vector2(-10/DuckTator.PPM, 35/DuckTator.PPM), new Vector2(10/DuckTator.PPM, 35/DuckTator.PPM));
		fdef.shape = goose_head;
		fdef.filter.categoryBits = DuckTator.ENEMY_HEAD;
		//adds a little bounce when Morgan collides with the head fixture.
		fdef.restitution = 1f;
		b2Body.createFixture(fdef).setUserData(this);
		
	}
	
	public void draw(Batch batch){
		if (!destroyed || stateTimer < 1)
			super.draw(batch);
	}

	@Override
	public void hitOnHead() {
		setToDestroy = true;
		Hud.addScore(500);
	}

	@Override
	public void reverseVelocity(boolean x, boolean y) {
		//When the goose is collided (by morgan/ground object) with we want to reverse the velocity so it walks in the opposite
		//direction.
		if(x)
			velocity.x = -velocity.x;
			runningRight = !runningRight;
		if (y)
			velocity.y = -velocity.y;
	}

	@Override
	public void hitOnBody(Morgan player) {
		if (Hud.protection == 0f){
			Gdx.gl.glClearColor(255f, 0, 0, 0);
		}
		
		if (Hud.health_value > 0){
			Hud.decreaseHealth();
			
		}else if (Hud.health_value == 0)
			killed_morgan(player);
		}
	}
	

