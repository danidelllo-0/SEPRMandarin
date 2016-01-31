package tools;

import java.util.Random;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DuckTator;

import Sprites.Collectables.Feather;
import Sprites.Enemy.BasketBomb;
import Sprites.Enemy.Goose;


public class universal_b2WorldCreator {
	private Array<Goose> geese;
	private Array<BasketBomb> bombs;
	protected World world;
	protected TiledMap map;
	protected BodyDef bdef;
	protected PolygonShape shape;
	protected FixtureDef fdef;
	protected Body body;
	protected DuckTator game;
	protected Random rand;
	public universal_b2WorldCreator(DuckTator game, World world, TiledMap map){
		//Setting our world,map and game variables.
		this.world = world;
		this.map = map;
		this.game = game;
		
		//Creating our body definition
		bdef = new BodyDef();		
		//This is the shape we are going to be drawing around our objects.
		shape = new PolygonShape();
		
		//Fixture definition.
		fdef = new FixtureDef();	
		
		//We will use rand to generate the random coordinates to place basketball bombs.
		rand = new Random();
		
	}
	
	
	
	public void rectangle_layer(int layer, short categoryBit) {
		for(MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
			//First we're going to get the rectangle MAPOBJECT. So the rectangle drawn on the tmx file.
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			//Now we're defining our body. Static just means it isn't affected by forces/gravity.
			bdef.type = BodyDef.BodyType.StaticBody;
			
			/*Now we're setting the position of our body. It needs to be OVER the rectangle objects that are 
			* on the map.
			* rect IS the object on the map. We can get its x position, and the width of it!
			* the method .getX gives the x coordinate in the bottom left hand corner of the rectangle. 
			* We want the coordinate of the middle of the rectangle so we have to add on half the width!*/
			bdef.position.set((rect.getX()+rect.getWidth()/2)/ DuckTator.PPM, (rect.getY()+rect.getHeight()/2)/ DuckTator.PPM);
			
			//Now we create our body in the world.
			body = world.createBody(bdef);
			
			//Finally we're going to add our fixture to the body.
			shape.setAsBox(rect.getWidth()/2 /DuckTator.PPM, rect.getHeight()/2/DuckTator.PPM);
			fdef.shape = shape;
			//We pass in the category bit when we call the function.
			fdef.filter.categoryBits = categoryBit;
			body.createFixture(fdef);
			
		}
	}
	

		
	public void feather_layer(int layer){
		//For all the objects in the map layer that contains the feather objects(passed in) it gets each 
		//rectangular object that was placed in Tiled2D.
		for(MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
			//Getting the rectangle.
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			//Creating a new feather, passing in the rectangle because this is where we want to create a feather.
			new Feather(world, map, rect);
	
		}
	}

	public void goose_layer(int layer){
		//Creating an array of Goose objects.
		geese = new Array<Goose>();
		//Iterates through the layer that's passed in finding all the rectangular objects that were placed
		//as objects on the layer.
		for(MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
			//Getting the rectangle.
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			//Creating a goose using the coordinates provided by the rectangle. Thus a goose object will be
			//created where the rectangles are in the tiled2D map. 
			geese.add(new Goose(game, world, rect.getX()/DuckTator.PPM, rect.getY()/DuckTator.PPM));
		}
		
	}
	
	
	public void random_bombs(){
		//array to store our BasketBomb objects.
		bombs = new Array<BasketBomb>();
		for (int i = 0; i < 20; i++)
		{
			//Generating random x coordinates. Adding 1 so they won't spawn at 0 and fall off the map.
			int bomb_x = rand.nextInt(140)+20;
			//Fixing the y at 4 so they don't spawn inside buildings.
			int bomb_y = 4;
			//creating a new BasketBomb object using the random coordinates and adding it to our array.
			//We will retrieve this array in the round and draw all of the objects inside it.
			bombs.add(new BasketBomb(game, world, bomb_x, bomb_y));
		}
		
		for (int i = 0; i < 5; i++)
		{
			//Generating random x coordinates. Adding 1 so they won't spawn at 0 and fall off the map.
			int bomb_x = rand.nextInt(8)+30;
			//We know there aren't any buildings here as it's the court!
			int bomb_y = rand.nextInt(5);
			//creating a new BasketBomb object using the random coordinates and adding it to our array.
			//We will retrieve this array in the round and draw all of the objects inside it.
			bombs.add(new BasketBomb(game, world, bomb_x, bomb_y));
		}
		
	}

	//Our getters to retrieve the array of goose and basketbomb objects.
	public Array<Goose> getGeese(){
		return geese;
	}
	
	public Array<BasketBomb> getBombs(){
		return bombs;
	}


}
