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

import Sprites.Morgan;

public class GameOver implements Screen {
	
	//Setting variables
	private Texture background;
	private Texture retryTexture;
	private Viewport viewport;
	private Image retryButton,backgroundStage;
	private DuckTator game;
	private Stage stage;
	private Morgan player;
	
	public GameOver(DuckTator game,Morgan player1){
		//Setting the 'game' to the 'game' that is passed in.
		this.game = game;
		//Setting the 'player' to the 'player' that is passed in.
		this.player = player1;
		//Creating textures for our game
		background = new Texture("GameOver/gameover.png");
		retryTexture = new Texture("GameOver/retry.png");
		
		//creating images from our two textures (allowing us to add them to the stage)
		retryButton = new Image(retryTexture);
		backgroundStage = new Image(background);
		
		//Setting our view port.
		viewport = new FitViewport(DuckTator.V_WIDTH , DuckTator.V_HEIGHT);
		
		//Creating our stage setting its dimensions equal to our viewport.
		stage = new Stage(viewport);
		
		//First we add our background IMAGE. It will be drawn starting at 0,0 (default) and so will take up 
		//the entire screen.
		stage.addActor(backgroundStage);
		
		//Then we draw our retry IMAGE. It will also be drawn starting at 0,0 so we need to re position it.
		stage.addActor(retryButton);
		//We reposition it to the center but as the image will be drawn from the bottom left corner we need
		//to account for the offset.
		retryButton.setPosition(DuckTator.V_WIDTH/2 - 125, DuckTator.V_HEIGHT/2 -120);
		
		//All the click listeners will be set inside this method
		InitaliseButtons();
		
		
		
	}
	
	private void InitaliseButtons() {
		//We are overriding the clicked method for when our image is clicked. 
		//In this case we start a new menu screen!
		retryButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				player.dead_morgan(player.lvl);
				//game.setScreen(new MenuScreen(game));
				dispose();
			}
			
				});
		
	}

	@Override
	public void show() {
		//This just means inputs are listened for on the stage (the stage contains our retry button and 
		//our background image)
		Gdx.input.setInputProcessor(stage);
	}
	


	@Override
	public void render(float delta) {
		//setting the screen to black
		Gdx.gl.glClearColor(0, 0, 0, 0);
		//Clearing the screen	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Drawing our stage.
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// if the window is resized we want to update our viewport to the next width and height.		
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
		//disposing of resources we no longer need.
		stage.dispose();
		background.dispose();
		retryTexture.dispose();
		
		
	}

}
