
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
	//To keep track of the time
	private Integer worldTimer;
	//To keep track of Morgan's health
	public static int health_value;
	//To keep track of the score
	private static int score;
	//Will count to a second - used for colouring the hud.
	private float timeCount;
	//
	private String task_string = " ";
	private float flyingTimer;
	//private float flyingUpdateTimeInterval=0.1f;
	
	//The wigits we will be placing onto our stage are 'Label's. So we need one for each of our key pieces
	//of information.
	public Label countdownLabel;
	public Label timeLabel;
	public Label worldLabel;
	static Label healthLabel;
	static Label scoreLabel;
	static Label task;
	static Label flyingLabel;
	
	public Hud(SpriteBatch sb){
		//Initialising variables setting timer/score/health
		worldTimer = 0;
		timeCount = 0;
		score = 0;
		health_value = 10;
		flyingTimer = 0.1f;
		
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
		countdownLabel = new Label(String.format("TIME: %d", worldTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		scoreLabel = new Label(String.format("SCORE: %06d", score),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		healthLabel = new Label(String.format("HEALTH: %d", health_value),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		task = new Label(String.format("OBJECTIVE: %s",task_string),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
		flyingLabel = new Label(String.format("FLYING LOCK: %s",flyingTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE) );
	
		//Adding the labels to our table
		//if we have multiple things that "expandX" they all have equal space. We just pad down from the top 5 pixels.
		table.add(scoreLabel).expandX().pad(2);
		table.add(healthLabel).expandX().pad(2);
		table.add(countdownLabel).expandX().pad(2);
		table.add(task).expandX().pad(2);
		table.add(flyingLabel).expandX().pad(2);
		
		//add the table to our stage!
		stage.addActor(table);
		
	}
	
	public void update(float dt){
		//the update method is going to be called 60 times a second by the render method.
		//dt is just approximately 1/60. So summing it up we can compute when a second has passed. 
		timeCount += dt;
		
		//If the timeCount is equal/greater than 1 then 1 second has passed.
		//Therefore we want to increment the world Timer.		
		if(timeCount >= 1){
			worldTimer ++;
			//We then update our label to the new time
			countdownLabel.setText(String.format("TIME: %d", worldTimer));
			//Setting the timeCoutn back to 0 so we can wait for another second to pass
			timeCount = 0;
			
		}
		if (!Morgan.allowedToFly){
			if (Morgan.timeStateFlyingLock >= flyingTimer){
				flyingLabel.setText(String.format("FLYING LOCK: %s", String.format("%.1f", 2 - Morgan.timeStateFlyingLock)));
				flyingTimer += 0.1f;
			}
		}
		else{
			flyingTimer = 0.1f;
			flyingLabel.setText(String.format("FLYING LOCK: %s", "Disabled"));
		}
	}
	
	//Public method we can access outside of the Hud class to decrease Morgan's health.
	public static void decreaseHealth(){
		health_value -= 1;
		healthLabel.setText(String.format("HEALTH: %d", health_value));
		
	}
	
	//Public method we can access outside of the Hud class to increase Morgan's health.
	public static void increaseHealth(){
		if (health_value<10){
			health_value += 1;
			healthLabel.setText(String.format("HEALTH: %d", health_value));
		}
		
	}
	
	//sets new task
	public void setTask(String task_str)
	{
		task.setText(String.format("OBJECTIVE: %s",task_str));
	}
	
	//Access outside the class to set health back to 10.
	public static void regenerateHealth(){
		health_value = 10;
		healthLabel.setText(String.format("HEALTH: %d", health_value));
		
	}
	
	//Access outside the class to increase the score by the value passed in.	
	public static void addScore(int value){
		score += value;
	
		scoreLabel.setText(String.format("SCORE: %06d", score));
	}
	
	public void setScoreHealth(int Svalue,int Hvalue)
	{
		score = Svalue;
		health_value = Hvalue;
		healthLabel.setText(String.format("HEALTH: %d", health_value));
		scoreLabel.setText(String.format("SCORE: %06d", score));
	}
	
	public static int getScore()
	{
		return score;
	}

	public static int getHealth()
	{
		return health_value;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//We no longer need out stage so we can dispose of it.
		stage.dispose();
	}

}
