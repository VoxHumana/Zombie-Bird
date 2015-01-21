package com.voxhumana.zombiebird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.voxhumana.screens.GameScreen;
import com.voxhumana.zbHelpers.AssetLoader;

public class ZBGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("ZBGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
		
	}
	
	@Override
	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}

}
