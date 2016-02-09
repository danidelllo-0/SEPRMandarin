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

public class MenuScreen implements Screen {
	
	private Texture background;
	private Texture playTexture, tutorialTexture, menulogoTexture;
	private Viewport viewport;
	private Image playButton,backgroundStage, tutorialButton, menuLogoStage;
	protected DuckTator game;
	private Stage stage;
	
	public MenuScreen(final DuckTator game){
		this.game = game;
		//Creating our Textures
		background = new Texture("Menu/background.png");
		playTexture = new Texture("Menu/storyMode.png");
		
		tutorialTexture = new Texture("Menu/TutorialLogo.png");
		menulogoTexture = new Texture("Menu/menulogo.png");
		
		//Creating images from our textures so we can use them on a stage.
		playButton = new Image(playTexture);
		backgroundStage = new Image(background);
		tutorialButton = new Image(tutorialTexture);
		menuLogoStage = new Image(menulogoTexture);
		
		//Creating our viewport
		viewport = new FitViewport(DuckTator.V_WIDTH, DuckTator.V_HEIGHT);
		
		//Creating our stage, settings its dimensions to the viewport
		stage = new Stage(viewport);

		//Adding our background to the stage. It will be drawn starting at 0,0 and will take up the entire screen.
		stage.addActor(backgroundStage);
		
		//Drawing the buttons to the screen.
		drawButtons();
		
		//Setting up what happens when the buttons are pressed.
		InitaliseButtons();
	}

	

	private void drawButtons() {
		//Adding the buttons to the stage and resizing them. As they're drawn starting from 0,0 we need to reposition them.
		stage.addActor(playButton);
		playButton.setPosition(DuckTator.V_WIDTH/2 - 150, DuckTator.V_HEIGHT/2);
		playButton.setSize(300, 50);
		
		
		stage.addActor(tutorialButton);
		tutorialButton.setPosition(DuckTator.V_WIDTH/2 - 100, DuckTator.V_HEIGHT/2-75);
		tutorialButton.setSize(200, 50);
		
		stage.addActor(menuLogoStage);
		menuLogoStage.setSize(150, 150);
		menuLogoStage.setPosition(DuckTator.V_WIDTH/2 - 80, 350);
		
	}



	private void InitaliseButtons() {
		//When the play button Image is clicked the following code will execute. 
		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new WorldMap(game));
			}
				});
		
		tutorialButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen(new Tutorial(game));
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
		//Setting the screen colour to black
		Gdx.gl.glClearColor(0, 0, 0, 0);
		//Clearing the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
	
		//Drawing the stage
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// If the window is enlarged we want to update our viewport.
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
		//once we've finished with the stage we can dispose of it. 
		stage.dispose();
		playTexture.dispose();
		tutorialTexture.dispose();
	}
	
}
