package Sprites.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DuckTator;

import Scenes.Hud;

public class Shield extends InteractiveTileObject{

	public Shield(World world, TiledMap map, Rectangle bounds,int collectibleLayer){
		super(world,map,bounds,collectibleLayer);
		//Creating our body and fixture definitions.		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		//Category bit identifies the object.
		fdef.filter.categoryBits = DuckTator.SHIELD_BIT;
		
		//Creating the shape
		PolygonShape shape = new PolygonShape();
		
		//Defining the bdef properties.				
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ DuckTator.PPM, (bounds.getY()+bounds.getHeight()/2)/ DuckTator.PPM);
		body = world.createBody(bdef);
		
		//remember starts in center
		shape.setAsBox(bounds.getWidth()/2/DuckTator.PPM, bounds.getHeight()/2/DuckTator.PPM);
		fdef.shape = shape;
		
		//the fixture is a sensor so Morgan won't collide with it - but collisions are still deteted
		fdef.isSensor = true;
		fixture = body.createFixture(fdef);
		
		//In the WorldContactListener we will be able to retrieve the user data. This means we can 
		//gain access to the Feather object and thus invoke methods from this class.
		fixture.setUserData(this);
	}
	
	@Override
	public void onBodyHit() {
		//choosing desired color
		Gdx.gl.glClearColor(0.545f, 0.271f, 0.07f, 1);
		/*This will be called when Morgan's body collides with the feather.
		* We get the cell and set it equal to null. This will remove the feather image from the
		* map once Morgan has collided with it. */
		getCell(this.collectibleLayer).setTile(null);
		//Setting the category bilt to destroyed so Morgan can no longer collide with the box2D body.
		setCategoryFilter(DuckTator.DESTROYED_BIT);
		//Adding 5 seconds to protection
		Hud.addProtection(5000);
		
	}
	
}