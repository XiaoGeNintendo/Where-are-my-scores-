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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hhs.xgn.gdxMusic.util.Pair;
import com.hhs.xgn.gdxMusic.util.VU;
import com.hhs.xgn.wams.play.GameMain;
import com.hhs.xgn.wams.storage.Beatmap;
import com.hhs.xgn.wams.storage.Loader;
import com.hhs.xgn.wams.storage.Memory;
import com.hhs.xgn.wams.storage.Saver;

public class Choose implements Screen{

	GameMain gm;
	AssetManager am;
	Button up,down,start;
	Stage s;
	Label l;
	Music bgm;
	Label title;
	Array<Pair<Integer,Float>> info;
	
	int ch;
	
	public Choose(GameMain gm){
		this.gm=gm;
		am=new AssetManager();
		
		//Reading User File
		Loader.readAll();
		
		System.out.println(Memory.beatmaps);
		
		//Loading resources
		
		am.load("button/up_down.png",Texture.class);
		am.load("button/up_up.png",Texture.class);
		am.load("button/down_down.png",Texture.class);
		am.load("button/down_up.png",Texture.class);
		am.load("button/start_down.png",Texture.class);
		//am.load("button/start_up.jpg",Texture.class);
		
		am.finishLoading();
		
		//Making Stages
		s=new Stage();
		
		up=VU.createButton(am.get("button/up_down.png"),am.get("button/up_up.png"), 100,100);
		VU.setTo(up, 1/3f, 2/3f);
		//up.setRotation(90);
		
		up.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				if(ch>0){
					ch--;
					if(bgm!=null){
						bgm.stop();
						bgm.dispose();
					}
					bgm=Gdx.audio.newMusic(Gdx.files.local("songs/"+Memory.beatmaps.get(ch).name+"/"+Memory.beatmaps.get(ch).songPath));
					bgm.play();
				}
			}
		});
		
		down=VU.createButton(am.get("button/down_down.png"),am.get("button/down_up.png"), 100,100);
		VU.setTo(down, 1/3f, 1/3f);
		//down.setRotation(90);
		
		down.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				if(ch<Memory.beatmaps.size()-1){
					ch++;
					if(bgm!=null){
						bgm.stop();
						bgm.dispose();
					}
					bgm=Gdx.audio.newMusic(Gdx.files.local("songs/"+Memory.beatmaps.get(ch).name+"/"+Memory.beatmaps.get(ch).songPath));
					bgm.play();
				}
			}
		});
		
		
		l=VU.createLabel("Select a song!");
		VU.setTo(l, 0.6f, 0.5f);
		
		title=VU.createLabel("Select a song by clicking the buttons.\nThen click START!\nThe beatmaps you have played will be marked in green.");
		VU.setTo(title, 0.5f, 7/8f);
		
		start=VU.createButton(am.get("button/start_down.png"), am.get("button/start_down.png"), 100, 100);
		VU.setTo(start, 0.5f, 1/8f);
		
		start.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				if(ch!=-1){
					gm.playGame(ch);
				}
			}
		});
		
		s.addActor(up);
		s.addActor(down);
		s.addActor(l);
		s.addActor(title);
		s.addActor(start);
//		s.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				// TODO Auto-generated method stub
//				super.clicked(event, x, y);
//				System.out.println("ooo");
//			}
//		});
//		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		s.dispose();
		am.dispose();
		if(bgm!=null)bgm.dispose();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if(bgm!=null){
			bgm.stop();
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		
		if(ch!=-1){
			if(info.get(ch).first!=-1){
				l.setText(Memory.beatmaps.get(ch).name+"\nmax score:"+info.get(ch).first+"\nmax mark:"+info.get(ch).second+"/300pts");
				l.setColor(0,1,0,1);
			}else{
				l.setText(Memory.beatmaps.get(ch).name);
				l.setColor(1,1,1,1);
			}
			
		}else{
			l.setText("Select a song!");
			l.setColor(1,0,0,1);
		}
		
		VU.render(s);
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
		ch=-1;
		Gdx.input.setInputProcessor(s);
		
		//Generate played info
		info=new Array<Pair<Integer,Float>>();
		
		for(Beatmap bm:Memory.beatmaps){
			info.add(Saver.played(bm.name));
		}
	}
	
}
