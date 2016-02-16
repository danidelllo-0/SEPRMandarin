package Screens.Rounds;


//--------------------CHANGE------------------------------
//changed some bits for passed parameters to extend functionality

//--------------------CHANGE------------------------------
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DuckTator;
import Scenes.Hud;
import Sprites.Morgan;
import tools.universal_b2WorldCreator;

public abstract class Level {

	protected DuckTator game;

	//**********CAMERA VARIABLES******************
	//A viewport is a window into our game, how it looks on our device.
	protected Viewport gamePort;
	
	//The gamecam is what follows along our game world. What the viewport displays.
	protected OrthographicCamera gamecam;
		
	//***********ANIMATION VARIABLES***************
	protected TextureAtlas duck_atlas;
	
	//Importing our class!
	protected Hud hud;
		
	//****TILED MAP VARIABLES****
	//This is what will load our map into the game.
	protected TmxMapLoader mapLoader;	
	
	//Tiled map, reference to the map itself, in the assets folder.
	protected static TiledMap map;	
	
	public String mapstring;
	
	//This renders the map to the screen.
	protected OrthogonalTiledMapRenderer tiled2DRenderer;
		
	//****BOX2D VARIABLES****
	//We need a world, everything in the box2D universe needs to go in this world.
	protected static World world;	
	
	//the Box2DDebugRenderer gives us the outlines for the Box2D world - so the developer can see what's happening.
	protected Box2DDebugRenderer b2dr;
	protected universal_b2WorldCreator b2_world_creator;
	
	//*************MARIO CLASS OBJECT***********
	protected Morgan player;
	
	
	public Level(DuckTator game, String mapString, int x_pos, int y_pos,int lvl_num){
		//****SETTING UP CAMERA*****
		gamecam = new OrthographicCamera();
		
		//****SETTING UP CAMERA*****
		gamecam = new OrthographicCamera();
		
		/*SETTING UP VIEWPORT*
		* First is world width, height, then camera.
		* FitViewPort maintains the aspect ratio without any stretching 
		* */
		gamePort = new FitViewport(DuckTator.V_WIDTH / DuckTator.PPM,DuckTator.V_HEIGHT / DuckTator.PPM,gamecam);
			
		//****SETTING UP THE HD****
		//Creating our hud, passing in the games batch!
		hud = new Hud(game.batch);
				
		//****SETTING UP THE TILED MAP****
		//Creating our map loader object.
		mapLoader = new TmxMapLoader();
		//Create a new map by calling the mapLoader method "load" with the parameter to the level file.
		//map = mapLoader.load("JamesCollege/JamesCollege.tmx");
		map = mapLoader.load(mapString);
		//Then we give our renderer the map to render.
		tiled2DRenderer = new OrthogonalTiledMapRenderer(map, 1/DuckTator.PPM);
		//gamecam defaults to center around 0,0.
		//we don't want this, the game would just be in the positive x,y avxis.
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2,0);
			
		
		/*SETTING UP OUR BOX2D WORLD*
		* world taking in gravity as a 2D vector. The boolean represents if we want to sleep objects
		* that are at rest. */
		world = new World(new Vector2(0,-10 ), true);
		
		//Creating an object of Box2DDebugRenderer so we can access its methods.
		b2dr = new Box2DDebugRenderer();
		
		//****CREATING MORGAN****
		duck_atlas = new TextureAtlas("NewDuck/duckyf.atlas");
		player = new Morgan(world,game,duck_atlas,x_pos,y_pos);
		player.lvl=lvl_num;
		
		//Making an object from our universal_b2WorldCreator.
		//This class allows us the draw the box2d representation of the tiled map.
		b2_world_creator = new universal_b2WorldCreator(game,world,map,player.lvl);

	}
	
	
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void resize(int width, int height) {
		//When we change the size of our screen, the viewport(what the player is seeing) gets adjusted
		gamePort.update(width, height);
	}


}
