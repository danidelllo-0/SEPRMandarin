//--------------------CHANGE------------------------------

//added implementation of level completion for any college (all of this file)


package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class LevelCompleted implements Screen{

	private Texture background;
	private Texture returnWorldMapTexture;
	private Viewport viewport;
	private Image returnWorldMapButton,backgroundStage;
	private DuckTator game;
	private Stage stage;
	
	public LevelCompleted(DuckTator game,int lvl_completed){
		this.game = game;
		
		//--------------------CHANGE------------------------------
		//Change because new levels were added and they each need a completion screen
		//if statements to pick a desired screen completion image to output based on current level
		String name = "";
		if (lvl_completed==1) name = "Constantine";
		if (lvl_completed==2) name = "Langwith";
		if (lvl_completed==3) name = "Goodricke";
		if (lvl_completed==4) name = "Halifax";
		if (lvl_completed==5) name = "Derwent";
		if (lvl_completed==6) name = "Alcuin";
		if (lvl_completed==7) name = "Vanburgh";
		if (lvl_completed==8) name = "JamesCompleted";
		
		
		//Creating the textures.
		background = new Texture("RoundCompleted/"+name+".png");
		returnWorldMapTexture = new Texture ("RoundCompleted/returnworldmap.png");
		//--------------------/CHANGE------------------------------
		
		//Creating images to use on our stage.
		backgroundStage = new Image(background);
		returnWorldMapButton = new Image(returnWorldMapTexture);
		
		//Setting our view port.
		viewport = new FitViewport(DuckTator.V_WIDTH , DuckTator.V_HEIGHT);
		
		//Creating our stage setting its dimensions equal to our viewport.
		stage = new Stage(viewport);
		
		//Drawing the buttons to the stage.
		drawButtons();
		
		//Initalising buttons (defining what happens when they are pressed)
		initialiseButtons();
		
		//--------------------CHANGE------------------------------
		//Change due to new type of objective in Vanbrugh
		//Resets the Vanbrugh flag at the completion screen so that only the Vanbrugh level has the Vanbrugh flag set
		Hud.vanbrughFlag = false;
		//--------------------/CHANGE------------------------------
	}

	private void drawButtons() {
		// TODO Auto-generated method stub
		//First we add our background IMAGE. It will be drawn starting at 0,0 (default) and so will take up 
		//the entire screen.
		stage.addActor(backgroundStage);
		
		//Then we add our return IMAGE. We reposition it to the middle - as it's also drawn at 0,0
		stage.addActor(returnWorldMapButton);
		returnWorldMapButton.setPosition(DuckTator.V_WIDTH/2 - 220, DuckTator.V_HEIGHT/2 -120);	
	}

	private void initialiseButtons() {
		// TODO Auto-generated method stub
		returnWorldMapButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new WorldMap(game));
				
			}
				});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		//Clearing the screen	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		//disposing anything we don't need.
		background.dispose();
		returnWorldMapTexture.dispose();
		stage.dispose();
		
	}
}
//--------------------/CHANGE------------------------------