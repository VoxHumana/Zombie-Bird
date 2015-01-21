package com.voxhumana.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {
	
	private Random r;
	
	private Rectangle skullUp, skullDown, barUp, barDown;
	
	public static final int VERTICAL_GAP = 45;
	public static final int SKULL_WIDTH = 24;
	public static final int SKULL_HEIGHT = 11;
	
	private float groundY;
	
	private boolean isScored = false;
	
	public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
		r = new Random();
		
		skullUp = new Rectangle();
		skullDown = new Rectangle();
		barUp = new Rectangle();
		barDown = new Rectangle();
		this.groundY = groundY;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		//Sets the top left corner's x and y coordinates for the rectangles
		//along with their width and height.
		
		barUp.set(position.x, position.y, width, height);
		barDown.set(position.x , position.y + height + VERTICAL_GAP,
				width, groundY - (position.y + height + VERTICAL_GAP));
		
		skullUp.set(position.x - (SKULL_WIDTH - width)/2, position.y + height - SKULL_HEIGHT, 
				SKULL_WIDTH, SKULL_HEIGHT);
		skullDown.set(position.x - (SKULL_WIDTH - width)/2, barDown.y, SKULL_WIDTH, SKULL_HEIGHT);
	}
	
	@Override
	public void reset(float newX){
		//Resets the pipe
		super.reset(newX);
		//Generates a random height for a new pipe
		height = r.nextInt(90) + 15;
		//Since the pipe is reset, the player can score from the pipe again
		isScored = false;
	}
	
	public boolean collides(Bird bird) {
		//Checks if the pipe has passed the "front" of the bird.
		//If yes, we would have to check for collision and call the Intersector class
		//If no, a collision is impossible and thus we skip the check to improve performance
		if(position.x < bird.getX() + bird.getWidth()) {
			//Checks if the hitbox of the bird intersects with the hitbox
			//of any of the pipes or the skulls
			return(Intersector.overlaps(bird.getBoundingCircle(), barUp)
					|| Intersector.overlaps(bird.getBoundingCircle(), barDown)
					|| Intersector.overlaps(bird.getBoundingCircle(), skullUp)
					|| Intersector.overlaps(bird.getBoundingCircle(), skullDown));
		}
		return false;
	}
	
	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public Rectangle getSkullUp() {
		return skullUp;
	}

	public Rectangle getSkullDown() {
		return skullDown;
	}

	public Rectangle getBarUp() {
		return barUp;
	}

	public Rectangle getBarDown() {
		return barDown;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean isScored) {
		this.isScored = isScored;
	}

}
