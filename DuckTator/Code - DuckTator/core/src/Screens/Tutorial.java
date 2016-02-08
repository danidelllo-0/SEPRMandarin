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

public class Tutorial implements Screen {
	
	private Texture background, returnMenuTexture;
	private DuckTator game;
	private Viewport viewport;
	private Image backgroundStage,returnButton;
	private Stage stage;
	
	
	public Tutorial(DuckTator game){
		this.game = game;
		//Creating textures
		background = new Texture("Menu/Tutorial/tutorial.png");
		returnMenuTexture = new Texture("Menu/Tutorial/return.png");
		
		//Creating images to use on our stage
		backgroundStage = new Image(background);
		returnButton = new Image(returnMenuTexture);
		//Creating our viewport
		viewport = new FitViewport(DuckTator.V_WIDTH, DuckTator.V_HEIGHT);
		
		//Creating our stage, settings its dimensions to the viewport
		stage = new Stage(viewport);
		
		//Drawing the tutorial screen.
		drawButtons();
		
		//Setting what happens when a button is clicked
		initaliseButtons();
	}

	private void drawButtons() {
		//adding background to the stage
		stage.addActor(backgroundStage);
		
		//adding return button to the stage
		stage.addActor(returnButton);
		returnButton.setPosition(25, DuckTator.V_HEIGHT-145);
		
	}

	private void initaliseButtons() {
		// TODO Auto-generated method stub
		returnButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new MenuScreen(game));
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
		
		//Drawing the stage to the screen.
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
		// Disposing of unused resources
		background.dispose();
		returnMenuTexture.dispose();
		stage.dispose();
	}

}
