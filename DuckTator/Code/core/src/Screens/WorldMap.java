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

import Scenes.Hud;
import Screens.Rounds.JamesCollege;
import Screens.Rounds.LangwithCollege;
import Screens.Rounds.VanburghCollege;
import Screens.Rounds.ConstantineCollege;
import Screens.Rounds.GoodrickeCollege;
import Screens.Rounds.HalifaxCollege;

public class WorldMap implements Screen{
	
	private Texture background;
	private Texture alcuinTexture, constantineTexture, goodrickeTexture,langwithTexture, vanbrughTexture, derwentTexture, halifaxTexture, jamesTexture;
	
	private Viewport viewport;
	
	private Image backgroundStage,alcuinButton, constantineButton, goodrickeButton, langwithButton, vanbrughButton, derwentButton, halifaxButton, jamesButton;
	
	public DuckTator game;
	Stage stage;

	
	
	public WorldMap(DuckTator game){
		this.game = game;
		viewport = new FitViewport(DuckTator.V_WIDTH, DuckTator.V_HEIGHT);
		//Creating a texture for each of our rounds & the background.
		background = new Texture("WorldMap/world_background.png");
		alcuinTexture = new Texture("WorldMap/Alcuin.png");
		constantineTexture = new Texture("WorldMap/Constantine.png");
		goodrickeTexture = new Texture("WorldMap/Goodricke.png");
		langwithTexture = new Texture("WorldMap/Langwith.png");
		vanbrughTexture = new Texture("WorldMap/Vanbrugh.png");
		derwentTexture = new Texture("WorldMap/Derwent.png");
		halifaxTexture = new Texture("WorldMap/Halifax.png");
		jamesTexture = new Texture("WorldMap/James.png");
		
		//Creating images from our textures allowing us to place them on the stage.
		backgroundStage = new Image(background);
		alcuinButton = new Image(alcuinTexture);
		constantineButton = new Image(constantineTexture);
		goodrickeButton = new Image(goodrickeTexture);
		langwithButton = new Image(langwithTexture);
		vanbrughButton = new Image(vanbrughTexture);
		derwentButton = new Image(derwentTexture);
		halifaxButton = new Image(halifaxTexture);
		jamesButton = new Image(jamesTexture);
		
		//Creating a stage to draw our background and buttons onto
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
		stage.addActor(vanbrughButton);
		//re-positioning the button.
		vanbrughButton.setPosition(50, 350);
		vanbrughButton.setSize(116, 124);
		
		//The above method applies to the rest of the buttons. 
		stage.addActor(alcuinButton);
		alcuinButton.setPosition(200,400);
		alcuinButton.setSize(106, 124);

		stage.addActor(constantineButton);
		constantineButton.setPosition(940, 400);
		constantineButton.setSize(90, 124);
		
		stage.addActor(goodrickeButton);
		goodrickeButton.setPosition(650, 300);
		goodrickeButton.setSize(115, 124);
				
		stage.addActor(langwithButton);
		langwithButton.setPosition(860, 300);
		langwithButton.setSize(124, 112);
		
		stage.addActor(jamesButton);
		jamesButton.setPosition(60, 180);
		jamesButton.setSize(98, 124);
	
		stage.addActor(halifaxButton);
		halifaxButton.setPosition(220, 70);
		halifaxButton.setSize(124, 70);
		
		stage.addActor(derwentButton);
		derwentButton.setPosition(200, 200);
		derwentButton.setSize(97, 124);
		
	}
	
	private void initaliseButtons() {
		
	
		
		constantineButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new ConstantineCollege(game));
				Hud.health_value = 10;
			}
			
				});
		
		alcuinButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("Alcuin");
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
		
		
		langwithButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new LangwithCollege(game));
				Hud.health_value = 10;
			}
			
				});
		
		
		halifaxButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new HalifaxCollege(game));
			}
			
				});
		
		vanbrughButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new VanburghCollege(game));
			}
			
				});
		
		derwentButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				System.out.println("Derwent");
			}
			
				});
		goodrickeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new GoodrickeCollege(game));
			}
			
				});
		
		
		
	}
	
	

	private void lockMap() {
		/*Checks if the boolean for each round is false. If it does equal false then set the color
			to black to show it's locked and clear the listener so it cannot be clicked on. */
		if (DuckTator.ALCUIN_UNLOCKED == false){
			alcuinTexture = new Texture("WorldMap/AlcuinGray.png");
			alcuinButton = new Image(alcuinTexture);
			drawButton();
			alcuinButton.clearListeners();
		}
		
		if(DuckTator.CONSTANTINE_UNLOCKED == false){
			constantineTexture = new Texture("WorldMap/ConstantineGray.png");
			constantineButton = new Image(constantineTexture);
			drawButton();
			constantineButton.clearListeners();
		}
		
		if (DuckTator.GOODRICKE_UNLOCKED == false){
			goodrickeTexture = new Texture("WorldMap/GoodrickeGray.png");
			goodrickeButton = new Image(goodrickeTexture);
			drawButton();
			goodrickeButton.clearListeners();
		}
		
		if (DuckTator.DERWENT_UNLOCKED == false){
			derwentTexture = new Texture("WorldMap/DerwentGray.png");
			derwentButton = new Image(derwentTexture);
			drawButton();
			derwentButton.clearListeners();
		}
		
		if (DuckTator.HALIFAX_UNLOCKED == false){
			halifaxTexture = new Texture("WorldMap/HalifaxGray.png");
			halifaxButton = new Image(halifaxTexture);
			drawButton();
			halifaxButton.clearListeners();
		}
		
		if (DuckTator.JAMES_UNLOCKED == false){
			jamesTexture = new Texture("WorldMap/JamesGray.png");
			jamesButton = new Image(jamesTexture);
			drawButton();
			jamesButton.clearListeners();
		}
		
		if (DuckTator.LANGWITH_UNLOCKED == false){
			langwithTexture = new Texture("WorldMap/LangwithGray.png");
			langwithButton = new Image(langwithTexture);
			drawButton();
			langwithButton.clearListeners();
		}
		
		if (DuckTator.VANBURGH_UNLOCKED == false){
			vanbrughTexture = new Texture("WorldMap/VanbrughGray.png");
			vanbrughButton = new Image(vanbrughTexture);
			drawButton();
			vanbrughButton.clearListeners();
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
		alcuinTexture.dispose();
		constantineTexture.dispose();
		goodrickeTexture.dispose();
		langwithTexture.dispose();
		vanbrughTexture.dispose();
		derwentTexture.dispose();
		halifaxTexture.dispose();
		jamesTexture.dispose();
		stage.dispose();
		
	}

}
