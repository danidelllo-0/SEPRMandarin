
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
	//Will count to a second - used for colouring the hud.
	private float timeCount;
	
	//--------------------CHANGE------------------------------
	//protection is a timer that keeps track of how long the invincibility power-up is enabled for
	public static float protection;
	//string stores the current objective for that is displayed on the HUD
	private String task_string;
	//Used to reduce the interval in which the flying timer should update when activated
	private float flyingTimer;
	//Stores the current score of the game when the Vanbrugh level starts used to calcualte the points objective 
	private static int vanbrughInitialScore;
	//A flag that is set when the Vanbrugh level starts to enable the different objective type
	public static boolean vanbrughFlag;
	//--------------------/CHANGE------------------------------
	
	//The wigits we will be placing onto our stage are 'Label's. So we need one for each of our key pieces
	//of information.
	//--------------------CHANGE------------------------------
	//Label to display the invincibility time on the HUD
	static Label protectionLabel;
	//Label to display the current objective on the HUD
	static Label task;
	//Label to display the whether flying is locked and if so how much time is left on the HUD
	static Label flyingLabel;
	//--------------------/CHANGE------------------------------
	public Label worldLabel;
	static Label healthLabel;
	static Label scoreLabel;  

	
	
	public Hud(SpriteBatch sb){
		//Initialising variables setting timer/score/health
		timeCount = 0;
		score = 0;
		health_value = 10;
		//--------------------CHANGE------------------------------
		//Initialises new variables for the new HUD items
		flyingTimer = 0.1f;
		protection = 0;
		task_string = " ";
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
		//Initialise the new labels for the HUD with the desired text and colour
		task = new Label(String.format("OBJECTIVE: %s",task_string),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		flyingLabel = new Label(String.format("FLYING LOCK: %s",flyingTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		protectionLabel = new Label(String.format("INVINCIBILITY: %s",protection/1000),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		//--------------------/CHANGE------------------------------
		
		//Adding the labels to our table
		//if we have multiple things that "expandX" they all have equal space. We just pad down from the top 5 pixels.
		table.add(scoreLabel).expandX().pad(2);
		table.add(healthLabel).expandX().pad(2);
		
		//--------------------CHANGE------------------------------
		//New Labels that appear on the HUD
		table.add(task).expandX().pad(2);
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
		
		//--------------------CHANGE------------------------------
		//Added new statements that implement the new timers on the HUD
		//Decreases the flying lock timer (if it is enabled) every 0.1 seconds and displays it in the HUD.
		if (!Morgan.allowedToFly){
			if (Morgan.timeState >= flyingTimer){
				flyingLabel.setText(String.format("FLYING LOCK: %s", String.format("%.1f", 2 - Morgan.timeState)));
				flyingLabel.setColor(Color.RED);
				flyingTimer += 0.1f;
			}
		}
		else{
			flyingTimer = 0.1f;
			flyingLabel.setText(String.format("FLYING LOCK: %s", "Disabled"));
			flyingLabel.setColor(Color.GREEN);
		}
		
		//Decreases the invincibility timer (if it is enabled) every 0.1 seconds and displays it in the HUD.
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
		//--------------------/CHANGE------------------------------
		
	}
	
	//Public method we can access outside of the Hud class to decrease Morgan's health.
	public static void decreaseHealth(){
		if (protection==0)
		{
			health_value -= 1;
			healthLabel.setText(String.format("HEALTH: %d", health_value));
		}
		
	}
	
	//Public method we can access outside of the Hud class to increase Morgan's health.
	public static void increaseHealth(){
		if (health_value<10){
			health_value += 1;
			healthLabel.setText(String.format("HEALTH: %d", health_value));
		}
		
	}
	
	//Access outside the class to increase the score by the value passed in.	
	public static void addScore(int value){
		score += value;
		scoreLabel.setText(String.format("SCORE: %06d", score));
		if (vanbrughFlag == true){
			if ((getScore() - vanbrughInitialScore) >= 5000){
				task.setColor(Color.GREEN);
			}
		}
	}
	
	//--------------------CHANGE------------------------------
	//New levels have been added to the HUD items need to be updated when starting a new level
	//updates health to 10 and score to passed value (used in initialising new level)
	//When score is initialised it saves the initial score of that round used for the Vanbrugh points objective
	public void setScoreHealth(int Svalue)
	{
		vanbrughInitialScore = Svalue;
		score = Svalue;
		health_value = 10;
		healthLabel.setText(String.format("HEALTH: %d", health_value));
		scoreLabel.setText(String.format("SCORE: %06d", score));
	}
	//--------------------/CHANGE------------------------------
	
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
	
	//--------------------CHANGE------------------------------
	//added new functions accessible from outside to modify some variables
	//Sets the new objective by each level calling this function with the appropriate objective
	public void setTask(String task_str){
		task.setText(String.format("OBJECTIVE: %s",task_str));
		if (vanbrughFlag == true)
			task.setColor(Color.RED);
	}
	
	//Adds a specified time to the invincibility power-up when a shield is collected
	public static void addProtection(float n)
	{
		protection +=n;
		protectionLabel.setText(String.format("INVINCIBILITY: %s", protection/1000));
		protectionLabel.setColor(Color.GREEN);
	}
	//--------------------/CHANGE------------------------------
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//We no longer need out stage so we can dispose of it.
		stage.dispose();
	}

}
