package com.hhs.xgn.wams.play;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.hhs.xgn.gdxMusic.util.VU;
import com.hhs.xgn.wams.screen.*;
import com.hhs.xgn.wams.storage.Memory;

public class GameMain extends Game {

	/**Start Screens*/
	Screen s1,s2;
	
	Screen main;
	Screen choose;
	Screen play;
	
	@Override
	public void create() {
		s1=new StartScreen1(this);
		s2=new StartScreen2(this);
		main=new Main(this);
		choose=new Choose(this);
		play=new Play(this);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.graphics.setDisplayMode(VU.width,VU.height,true);// set resolution to HD ready (1280 x 720) and set full-screen to true  
          
//        Gdx.graphics.setVSync(true);  
		setScreen(s1);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();

		
		VU.disposeAll(s1,s2,main,choose,play);
		
	}

	public void EndGame() {
		WhereAreMyScoresLauncher.app.exit();
	}

	public void Start2() {
		setScreen(s2);
	}

	public void ShowMain(){
		setScreen(main);
	}

	public void choose() {
		setScreen(choose);
		
	}

	public void playGame(int ch) {
		Memory.nowSong=ch;
		setScreen(play);
	}
}
