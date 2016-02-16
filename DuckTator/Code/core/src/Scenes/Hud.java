
package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DuckTator;

import Sprites.Morgan;


public class Hud implements Disposable{
	/*The hud is where the score, health, level and all the key information is kept.
	* We are using scene 2d - it's a way to manage wigits. We can place text onto our stage and udpate them when 
	* our key information changes. */
	//Our stage, what we're going to add too
	public Stage stage;
	//When the game world moves we want the HUD to stay in the same position. Thus we need a new camera&viewport.
	private Viewport viewport;
	//To keep track of Morgan's health
	public static int health_value;
	//To keep track of the score
	private static int score;
	//--------------------CHANGE------------------------------
	//protection timer
	public static float protection;
	//--------------------/CHANGE------------------------------
	//Will count to a second - used for colouring the hud.
	private float timeCount;
	//string used in setting a new task to the GUI
	private String task_string = " ";
	//timer used to tell user when they can fly again
	private float flyingTimer;
	
	//The wigits we will be placing onto our stage are 'Label's. So we need one for each of our key pieces
	//of information.
	//--------------------CHANGE------------------------------
	public Label timeLabel; //time passed of game
	static Label protectionLabel; //if the user has temporary protection
	public Label worldLabel;
	static Label task; //current objective
	static Label flyingLabel; //time until can fly again
	//--------------------/CHANGE------------------------------
	static Label healthLabel; //current health 
	static Label scoreLabel;  //current score

	
	public Hud(SpriteBatch sb){
		//Initialising variables setting timer/score/health
		timeCount = 0;
		score = 0;
		health_value = 10;
		//--------------------CHANGE------------------------------
		flyingTimer = 0.1f;
		protection = 0;
		//--------------------/CHANGE------------------------------
		
		//Setting our viewport.
		viewport = new FitViewport(DuckTator.V_WIDTH, DuckTator.V_HEIGHT, new OrthographicCamera());
		
		//See implementation documentation for an explaination of the stage.
		stage = new Stage(viewport,sb);
		
		//Creating our table to insert into the stage - providing structure.
		Table table = new Table();
		table.top();
		//Means our table is the size of our stage.
		table.setFillParent(true);
		
		//Now we provide the text to be displayed in our table.
		scoreLabel = new Label(String.format("SCORE: %06d", score),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		healthLabel = new Label(String.format("HEALTH: %d", health_value),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		//--------------------CHANGE------------------------------
		//added new labels
		task = new Label(String.format("OBJECTIVE: %s",task_string),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		flyingLabel = new Label(String.format("FLYING LOCK: %s",flyingTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		protectionLabel = new Label(String.format("INVINCIBILITY: %s",protection/1000),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		//--------------------/CHANGE------------------------------
	
		//Adding the labels to our table
		//if we have multiple things that "expandX" they all have equal space. We just pad down from the top 5 pixels.
		table.add(scoreLabel).expandX().pad(2);
		table.add(healthLabel).expandX().pad(2);
		table.add(task).expandX().pad(2);
		//--------------------CHANGE------------------------------
		//added new labels
		table.add(flyingLabel).expandX().pad(2);
		table.add(protectionLabel).expandX().pad(2);
		//--------------------/CHANGE------------------------------
		
		//add the table to our stage!
		stage.addActor(table);
		
	}
	
	public void update(float dt){
		//the update method is going to be called 60 times a second by the render method.
		//dt is just approximately 1/60. So summing it up we can compute when a second has passed. 
		timeCount += dt;
		
		if (!Morgan.allowedToFly){
			if (Morgan.timeStateFlyingLock >= flyingTimer){
				flyingLabel.setText(String.format("FLYING LOCK: %s", String.format("%.1f", 2 - Morgan.timeStateFlyingLock)));
				flyingLabel.setColor(Color.RED);
				flyingTimer += 0.1f;
			}
		}
		else{
			flyingTimer = 0.1f;
			flyingLabel.setText(String.format("FLYING LOCK: %s", "Disabled"));
			flyingLabel.setColor(Color.GREEN);
		}
		
		//The countdown timer for protection in intervals of 0.1 seconds
		if (protection != 0){
			if (timeCount >= 0.1){
				protection -= 100;
				protectionLabel.setText(String.format("INVINCIBILITY: %s", protection/1000));
				timeCount = 0;
			}
		}
		else if (protection == 0f){
			protectionLabel.setText(String.format("INVINCIBILITY: %s", 0));
			protectionLabel.setColor(Color.RED);
		}
	}
	
	//Public method we can access outside of the Hud class to decrease Morgan's health.
	public static void decreaseHealth(){
		if (protection==0)
		{
			health_value -= 1;
			healthLabel.setText(String.format("HEALTH: %d", health_value));
		}
		
	}
	//--------------------CHANGE------------------------------
	//added new functions accessible from outside to modify some variables
	
	
	
	//Public method we can access outside of the Hud class to increase Morgan's health.
	public static void increaseHealth(){
		if (health_value<10){
			health_value += 1;
			healthLabel.setText(String.format("HEALTH: %d", health_value));
		}
		
	}
	
	
	//sets new task by passed string
	public void setTask(String task_str)
	{
		task.setText(String.format("OBJECTIVE: %s",task_str));
	}
	
	//Access outside the class to set health back to 10.
	public static void regenerateHealth(){
		health_value = 10;
		healthLabel.setText(String.format("HEALTH: %d", health_value));
		
	}
	
	//updates health to 10 and score to passed value (used in initialising new level)
		public void setScoreHealth(int Svalue)
		{
			score = Svalue;
			health_value = 10;
			healthLabel.setText(String.format("HEALTH: %d", health_value));
			scoreLabel.setText(String.format("SCORE: %06d", score));
		}
		
	//--------------------/CHANGE------------------------------
	
	//Access outside the class to increase the score by the value passed in.	
	public static void addScore(int value){
		score += value;
	
		scoreLabel.setText(String.format("SCORE: %06d", score));
	}
	
	
	//getter for score
	public static int getScore()
	{
		return score;
	}
	//getter for health
	public static int getHealth()
	{
		return health_value;
	}
	
	//adds passed amount of time to the protection timer
	public static void addProtection(float n)
	{
		protection +=n;
		protectionLabel.setText(String.format("INVINCIBILITY: %s", protection/1000));
		protectionLabel.setColor(Color.GREEN);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//We no longer need out stage so we can dispose of it.
		stage.dispose();
	}

}
