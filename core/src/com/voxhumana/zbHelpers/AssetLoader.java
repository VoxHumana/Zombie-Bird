package com.voxhumana.zbHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture;
	public static TextureRegion bg, grass;
	
	public static Animation birdAnimation;
	public static TextureRegion birdUp, birdDown, bird;
	
	public static TextureRegion skullUp, skullDown, bar;
	
	public static Sound dead, flap, coin;
	
	public static BitmapFont font, shadow;
	
	public static Preferences prefs;
	
	public static void load(){
		
		//Initializes the png that contains all the textures used
		//by the objects in this game.
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture. setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		//Initializes the background texture used in this game by specifying coordinates 
		//for the region in texture.png that is the background
		bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);
		
		//Initializes the grass texture used in this game by specifying coordinates 
		//for the region in texture.png that is the grass
		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);
		
		//Initializes the texture for one of the animation frames for the bird,
		//specifically the frame for the bird's wings flapping downwards
		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
	    birdDown.flip(false, true);
	    
	    //Initializes the texture for one of the animation frames for the bird,
	  	//specifically the frame for which the bird is stationary
	    bird = new TextureRegion(texture, 153, 0, 17, 12);
	    bird.flip(false, true);
	    
	    //Initializes the texture for one of the animation frames for the bird,
	  	//specifically the frame for the bird's wings flapping upwards
	    birdUp = new TextureRegion(texture, 170, 0, 17, 12);
	    birdUp.flip(false, true);
	    
	    //Uses the three frames for the bird to create an animation that loops continuously
	    TextureRegion[] birds = {birdDown, bird, birdUp};
	    birdAnimation = new Animation(0.06f, birds);
	    birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
	    
	    //Initializes the texture for the skull that is right side up
	    skullUp = new TextureRegion(texture, 192, 0, 24, 14);
	    
        //Initializes the texture for the skull that is upside down by flipping skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);
        
        //Initializes the texture region for the pipes
        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);
        
        //Initializes the sounds to be played when the bird dies, flaps, or passes through a pipe
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        
        //Initializes the fonts to use in this game
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);
        
        //Initializes or retrieves existing preferences file
        prefs = Gdx.app.getPreferences("ZombieBird");
        
        if(!prefs.contains("highScore")){
        	prefs.putInteger("highScore", 0);
        }
  
		
	}
	
	public static void dispose(){
		//Dispose all of our assets when we are done using them
		texture.dispose();
		font.dispose();
		shadow.dispose();
		dead.dispose();
		coin.dispose();
		flap.dispose();
	}
	
	public static void setHighScore(int val){
		prefs.putInteger("highScore", val);
		prefs.flush();
	}
	
	public static int getHighScore(){
		return prefs.getInteger("highScore");
	}
}
