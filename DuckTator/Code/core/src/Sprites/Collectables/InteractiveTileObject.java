package Sprites.Collectables;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DuckTator;

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	
	protected Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	
	protected int collectibleLayer;
	
	
	public InteractiveTileObject(World world, TiledMap map, Rectangle bounds, int collectibleLayer){
		
		this.collectibleLayer=collectibleLayer;
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		
	}
	
	public abstract void onBodyHit();
	
	public TiledMapTileLayer.Cell getCell(int layer_number){
		//Method that can be used by all classes that will return the CELL that an image is on.
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layer_number);
		return layer.getCell((int) (body.getPosition().x * DuckTator.PPM / 16), (int) (body.getPosition().y * DuckTator.PPM / 16));
		
	}
	
	public void setCategoryFilter(short filterBit){
		//Sets the category bit (the 'WHAT IS') of a fixture
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	
	
	

}
