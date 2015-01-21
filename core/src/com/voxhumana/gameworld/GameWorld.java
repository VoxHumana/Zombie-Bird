package com.voxhumana.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.voxhumana.gameobjects.Bird;
import com.voxhumana.gameobjects.ScrollHandler;
import com.voxhumana.zbHelpers.AssetLoader;

public class GameWorld {
	private Bird bird;
	
	private ScrollHandler scroller;
	
	private Rectangle ground;
	//private Rectangle sky;
	
	private int score = 0;
	
	public int midPointY;
	
	public enum GameState{
		READY, RUNNING, GAMEOVER, HIGHSCORE
	}
	
	private GameState currentState;

	
	public GameWorld(int midPointY){
		bird = new Bird(33, midPointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midPointY + 66);
		ground = new Rectangle(0, midPointY + 66, 136, 11);
		//sky = new Rectangle(0, 0, 136, 1);
		currentState = GameState.READY;
		this.midPointY = midPointY;
	}
	
	public void updateRunning(float delta) {
		
		if (delta > .15f){
			delta = .15f;
		}
		
		bird.update(delta);
		scroller.update(delta);
		
		if (bird.isAlive() && scroller.collides(bird)) {
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
		}
		
		if(Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			scroller.stop();
			bird.die();
			bird.decelerate();
			currentState = GameState.GAMEOVER;
			
			if(score > AssetLoader.getHighScore()){
				AssetLoader.setHighScore(score);
				currentState = GameState.HIGHSCORE;
			}
		}
		
		 /*if(Intersector.overlaps(bird.getBoundingCircle(), sky)){
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
		}*/
	}
	
	private void updateReady(float delta) {
		
	}
	
	public void update(float delta){
		switch(currentState){
		case READY:
			updateReady(delta);
			break;
			
		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		
		}
	}
	
	public void start() {
		currentState = GameState.RUNNING;
	}
	
	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		currentState = GameState.GAMEOVER.READY;
	}
	
	public boolean isGameOver(){
		return currentState == GameState.GAMEOVER;
	}
	
	public void addScore(int increment){
		score += increment;
	}
	
	public Bird getBird(){
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}
	
	public int getScore(){
		return score;
	}

	public boolean isReady() {
		
		return currentState == GameState.READY;
	}

	public boolean isHighScore() {

		return currentState == GameState.HIGHSCORE;
	}

}
