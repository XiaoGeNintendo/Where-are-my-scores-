package com.hhs.xgn.wams.play;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class WhereAreMyScoresLauncher {

	public static LwjglApplication app;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config=new LwjglApplicationConfiguration();
		config.width=960;
		config.height=585;
		config.resizable=false;
//		config.fullscreen=true;
//		config.vSyncEnabled=false;
		
		app=new LwjglApplication(new GameMain(),config);
		
	}

}
