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

public class JamesCompleted implements Screen{

	private Texture background;
	private Texture returnWorldMapTexture;
	private Viewport viewport;
	private Image returnWorldMapButton,backgroundStage;
	private DuckTator game;
	private Stage stage;
	
	public JamesCompleted(DuckTator game){
		this.game = game;
		//Creating the textures.
		background = new Texture("RoundCompleted/JamesCompleted.png");
		returnWorldMapTexture = new Texture ("RoundCompleted/returnworldmap.png");
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
	}

	private void drawButtons() {
		// TODO Auto-generated method stub
		//First we add our background IMAGE. It will be drawn starting at 0,0 (default) and so will take up 
		//the entire screen.
		stage.addActor(backgroundStage);
		
		//Then we add our return IMAGE. We reposition it to the middle - as it's also drawn at 0,0
		stage.addActor(returnWorldMapButton);
		returnWorldMapButton.setPosition(DuckTator.V_WIDTH/2 - 250, DuckTator.V_HEIGHT/2 -120);	
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
