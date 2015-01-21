package com.voxhumana.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.voxhumana.zbHelpers.AssetLoader;

public class Bird {
	
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	private float rotation;
	private int height;
	private int width;
	
	private Circle boundingCircle;
	
	private boolean isAlive;
	
	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);
		boundingCircle = new Circle();
		isAlive = true;
	}
	
	public void update(float delta){
		velocity.add(acceleration.cpy().scl(delta));
		
		if(velocity.y > 200)
			velocity.y = 200;
		
		if(position.y < -13){
			position.y = -13;
			velocity.y = 0;
		}
		
		position.add(velocity.cpy().scl(delta));
		
		//This sets the circle to be used as the hitbox
		//for the bird as (9, 6) with respect to the bird.
		//The radius of this circle is 6.5f
		boundingCircle.set(position.x + 9, position.y+6, 6.5f);
		
		//Rotate counterclockwise if the bird is climbing altitude
		if(velocity.y <0) {
			rotation -= 600 * delta;
			
			if (rotation < -20) {
				rotation = -20;
			}
		}
		//Rotate clockwise if the bird is falling or dies
		if (isFalling() || !isAlive) {
			rotation += 480 * delta;
			if(rotation > 90) {
				rotation = 90;
			}
		}
	}
	
	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.y = 0;
		velocity.x = 0;
		acceleration.x = 0;
		acceleration.y = 460;
		isAlive = true;
	}
	
	public void onClick(){
		if(isAlive){
			AssetLoader.flap.play();
			velocity.y = -140;
		}
	}
	
	public void die(){
		isAlive = false;
		velocity.y = 0;
	}
	
	public void decelerate() {
		//Bird must stop accelerating once it dies
		acceleration.y = 0;
	}
	
	public boolean isFalling() {
		return velocity.y > 110;
	}
	
	public boolean shouldntFlap() {
		return velocity.y > 70 || !isAlive;
	}
	

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getRotation() {
		return rotation;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
		

}
