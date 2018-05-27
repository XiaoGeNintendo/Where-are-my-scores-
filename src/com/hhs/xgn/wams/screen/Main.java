package com.hhs.xgn.wams.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hhs.xgn.gdxMusic.util.VU;
import com.hhs.xgn.wams.play.GameMain;

public class Main implements Screen {

	GameMain gm;
	AssetManager am;
	Stage s;
	
	Image title;
	
	Button start,exit;
	
	Music bgm;
	
	/**Render Utils*/
	float rr,rg,rb;
	/**Render Utils*/
	int md;
	
	public Main(GameMain gm){
		this.gm=gm;
		am=new AssetManager();
		
		//Loading 
		am.load("button/down.png",Texture.class);
		am.load("button/up.png",Texture.class);
		am.load("button/exit_down.jpg",Texture.class);
		am.load("button/exit_up.jpg",Texture.class);
		am.load("title/title.jpg",Texture.class);
		am.finishLoading();
		
		//Creating
		//s=new Stage(new StretchViewport(VU.width, VU.height));
		s=new Stage();
		
		
		title=new Image(am.get("title/title.jpg",Texture.class));
//		title.setWidth(200);
//		title.setHeight(100);
		VU.setTo(title, 0.5f, 2/3f);
		
		start=VU.createButton(am.get("button/down.png"), am.get("button/up.png"),100,100);
		VU.setTo(start, 1/3f, 1/3f);
		
		System.out.println(start.getX()+" "+start.getY());
		start.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				gm.choose();
			}
		});
		
		exit=VU.createButton(am.get("button/exit_down.jpg"), am.get("button/exit_up.jpg"), 100, 100);
		
		exit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gm.EndGame(); 
			}
		});
		
		VU.setTo(exit, 2/3f, 1/3f);
		
		s.addActor(title);
		s.addActor(start);
		s.addActor(exit);
		
//		//Click Processer
		s.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				System.out.println(x+" "+y);
			}
		});
		
		//Music initing
		bgm=Gdx.audio.newMusic(Gdx.files.internal("music/island.wav"));
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		s.dispose();
		am.dispose();
		bgm.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		bgm.stop();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		rr+=md*0.001f;
		rg+=md*0.001f;
		rb+=md*0.001f;
		if(rr>1) md=-md;
		if(rr<0) md=-md;
		
		VU.render(s,rr,rg,rb,1);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		rr=0;
		rg=0;
		rb=0;
		md=1;
		bgm.play();
		bgm.setLooping(true);
		
		Gdx.input.setInputProcessor(s);
	}

}
