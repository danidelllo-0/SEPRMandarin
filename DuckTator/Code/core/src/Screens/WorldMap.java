package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DuckTator;

import Screens.Rounds.JamesCollege;
import Screens.Rounds.ConstantineCollege;

public class WorldMap implements Screen{
	
	private Texture background;
	private Texture yorkSportTexture, libraryTexture, marketsquareTexture,roncookhubTexture, centralTexture, derwentTexture, halifaxTexture, jamesTexture;
	
	private Viewport viewport;
	
	private Image backgroundStage,yorkSportButton, libraryButton, marketButton, ronButton, centralButton, derwentButton, halifaxButton, jamesButton;
	
	public DuckTator game;
	Stage stage;

	
	
	public WorldMap(DuckTator game){
		this.game = game;
		viewport = new FitViewport(DuckTator.V_WIDTH, DuckTator.V_HEIGHT);
		//Creating a texture for each of our rounds & the background.
		background = new Texture("WorldMap/world_background.png");
		yorkSportTexture = new Texture("WorldMap/York_Sport.png");
		libraryTexture = new Texture("WorldMap/library.png");
		marketsquareTexture = new Texture("WorldMap/Market_Square.png");
		roncookhubTexture = new Texture("WorldMap/ronCook.png");
		centralTexture = new Texture("WorldMap/Central_Hall.png");
		derwentTexture = new Texture("WorldMap/Derwent.png");
		halifaxTexture = new Texture("WorldMap/Halifax.png");
		jamesTexture = new Texture("WorldMap/James.png");
		
		//Creating images from our textures allowing us to place them on the stage.
		backgroundStage = new Image(background);
		yorkSportButton = new Image(yorkSportTexture);
		libraryButton = new Image(libraryTexture);
		marketButton = new Image(marketsquareTexture);
		ronButton = new Image(roncookhubTexture);
		centralButton = new Image(centralTexture);
		derwentButton = new Image(derwentTexture);
		halifaxButton = new Image(halifaxTexture);
		jamesButton = new Image(jamesTexture);
		
		//Creating a stage to draw our backgroudn and buttons onto
		stage = new Stage(viewport);
		
		//Calling the method to draw our buttons to the screen.
		drawButton();
		//Calling the method to define what happens when a button is pressed.
		initaliseButtons();
		
		lockMap();
		
	}
	
	




	private void drawButton() {
		//Initally we draw the background onto the stage. This will be drawn starting at 0,0
		stage.addActor(backgroundStage);
		
		/*We add our button to the stage, initally it will be drawn into 0,0. We will have to re position it
			to the correct place on the map. */
		stage.addActor(ronButton);
		//re-positioning the button.
		ronButton.setPosition(700, 250);
		
		//The above method applies to the rest of the buttons. 
		stage.addActor(yorkSportButton);
		yorkSportButton.setPosition(800, 400);
		yorkSportButton.setSize(120, 70);

		stage.addActor(libraryButton);
		libraryButton.setPosition(180, 400);
		libraryButton.setSize(120, 85);
		
		stage.addActor(marketButton);
		marketButton.setPosition(90, 340);
		marketButton.setSize(100, 75);
				
		stage.addActor(centralButton);
		centralButton.setPosition(90, 260);
		centralButton.setSize(100, 75);
		
		stage.addActor(jamesButton);
		jamesButton.setPosition(60, 180);
		jamesButton.setSize(100, 75);
	
		stage.addActor(halifaxButton);
		halifaxButton.setPosition(290, 1);
		halifaxButton.setSize(100, 75);
		
		stage.addActor(derwentButton);
		derwentButton.setPosition(200, 300);
		derwentButton.setSize(150, 75);
		
	}
	
	private void initaliseButtons() {
		
	
		
		yorkSportButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new ConstantineCollege(game));
			}
			
				});
		
		libraryButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("library");
			}
			
				});
		
		
		jamesButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				//James is implemented - a set screen method is called. 
				game.setScreen(new JamesCollege(game));
			}
			
				});
		
		
		centralButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("Central Hall");
			}
			
				});
		
		
		halifaxButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("Halifax");
			}
			
				});
		
		marketButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("Market");
			}
			
				});
		
		derwentButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("Derwent");
			}
			
				});
		
		
		
	}
	
	

	private void lockMap() {
		/*Checks if the boolean for each round is false. If it does equal false then set the color
			to black to show it's locked and clear the listener so it cannot be clicked on. */
		if (DuckTator.SPORTVILLAGE_UNLOCKED == false){
			yorkSportButton.setColor(Color.BLACK);
			yorkSportButton.clearListeners();
		}
		
		if(DuckTator.MARKET_UNLOCKED == false){
			marketButton.setColor(Color.BLACK);
			marketButton.clearListeners();
		}
		
		if (DuckTator.CENTRAL_UNLOCKED == false){
			centralButton.setColor(Color.BLACK);
			centralButton.clearListeners();
		}
		
		if (DuckTator.DERWENT_UNLOCKED == false){
			derwentButton.setColor(Color.BLACK);
			derwentButton.clearListeners();
		}
		
		if (DuckTator.HALIFAX_UNLOCKED == false){
			halifaxButton.setColor(Color.BLACK);
			halifaxButton.clearListeners();
		}
		
		if (DuckTator.JAMES_UNLOCKED == false){
			jamesButton.setColor(Color.BLACK);
			jamesButton.clearListeners();
		}
		
		if (DuckTator.LIBRARY_UNLOCKED == false){
			libraryButton.setColor(Color.BLACK);
			libraryButton.clearListeners();
		}
		
		if (DuckTator.RONCOOK_UNLOCKED == false){
			ronButton.setColor(Color.BLACK);
			ronButton.clearListeners();
		}
	}

	@Override
	public void show() {
		// This just means the stage is checked for inputs. 
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		//Setting colour to black
		Gdx.gl.glClearColor(0, 0, 0, 0);
		//Clearing the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Drawing our stage to the screen.
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		yorkSportTexture.dispose();
		libraryTexture.dispose();
		marketsquareTexture.dispose();
		roncookhubTexture.dispose();
		centralTexture.dispose();
		derwentTexture.dispose();
		halifaxTexture.dispose();
		jamesTexture.dispose();
		stage.dispose();
		
	}

}
