package com.hhs.xgn.wams.screen;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.hhs.xgn.gdxMusic.util.Pair;
import com.hhs.xgn.gdxMusic.util.VU;
import com.hhs.xgn.wams.display.Note;
import com.hhs.xgn.wams.play.GameMain;
import com.hhs.xgn.wams.storage.Beatmap;
import com.hhs.xgn.wams.storage.Memory;
import com.hhs.xgn.wams.storage.Saver;

public class Play implements Screen {
	
	GameMain gm;
	
	Beatmap map;
	
	Texture bgt;
	Texture playert,notet;
	
	Image bg,player;
	Image fade;
	
	Stage s;
	
	Label ls,lm,playing,lc;
	
	int score,combo;
	float mark,failMark;
	
	float delta;
	int noteNow,cheatNow;
	
	Music bgm;
	Sound miss,get;
	
	Pool<Note> notePool;
	Array<Note> aliveNotes;
	
	SpriteBatch sb;
	
	boolean autoplay,end;
	
	/* *****************End stage here******************** */
	Label l1,l2,l3,l4;
	Button save;
	Sound clap;
	Stage es;
	int esec,esem;
	Image esbg;
	float fcnt;
	Texture st;
	
	public Play(GameMain gm){
		this.gm=gm;
		notePool=new Pool<Note>(){

			@Override
			protected Note newObject() {
				// TODO Auto-generated method stub
				return new Note();
			}
			
		};
		
		sb=new SpriteBatch();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		VU.disposeAll(bgt,playert,notet,s,bgm,get,miss,clap,es,sb);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		VU.disposeAll(bgt,playert,notet,s,bgm,get,miss,clap,es,sb);
		
		bgt=null;
		playert=null;
		notet=null;
		s=null;
		bgm=null;
		miss=null;
		clap=null;
		es=null;
		sb=null;
		//VU.setNull(bgt,playert,notet,s,bgm);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		//Check framerate
		if(arg0>=0.8f){
			Gdx.app.error("Bad framerate", "Bad framerate:"+arg0+"!!!May cause sync fail!");
		}else{
			delta+=arg0;
		}
		
		if(!end){
			//Fade changing
			if(delta>=0.5f){
				fade.setPosition(player.getX(), player.getY());
				delta=0;
			}
			
			//Doing label changing
			LabelSync();
			
			//Doing note checking
			noteChecking(arg0);
			
			//Collide Checker
			collide();
			
			VU.render(s);
			
			//Draw notes using fucking SpriteBatch...
			spriteBatch();
		}else{
			
			fcnt+=arg0;
			if(fcnt>=0.1f){
				esec=Math.min(esec+1324, score);
				esem=Math.min((esem+3), (int)mark);
				fcnt=0;
			}
			
			l1.setText("Score:"+(String.format("%08d",esec)));
			l2.setText(String.format("%d",esem)+"/300 pts");
			
			VU.render(es);
			
		}
		
		
		
	}
	
	

	private void collide() {
		for(int i=0;i<aliveNotes.size;i++){
			Note n=aliveNotes.get(i);
			
			Rectangle noteRect=new Rectangle(n.pos.x,n.pos.y,notet.getWidth(),notet.getHeight());
			Rectangle playerRect=new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
			
			
			if(noteRect.overlaps(playerRect)){
				//Collide!
				score+=new Random().nextInt(200)+100;
				combo++;
				aliveNotes.removeIndex(i);
				i--;
				notePool.free(n);
				get.play();
			}
		}
	}

	private void spriteBatch() {
		sb.begin();
		for(int i=0;i<aliveNotes.size;i++){
			Note single=aliveNotes.get(i);
			sb.draw(notet,single.pos.x,single.pos.y);
		}
		sb.end();
	}

	private void noteChecking(float delta) {
		
		//Free notes
		for(int i=0;i<aliveNotes.size;i++){
			Note single=aliveNotes.get(i);
			if(single.alive==false){
				aliveNotes.removeIndex(i);
				i--;
				notePool.free(single);
				
				//Fail to catch!! yuyuyuyu!
				combo=0;
				mark-=failMark;
				miss.play();
			}
		}
		
		//Check notes
		while(noteNow<map.notes.size() && bgm.getPosition()>=map.notes.get(noteNow).first-map.dropTime/1000f){
			placeNote(noteNow);
			noteNow++;
		}
		
		//Check cheating notes
		while(cheatNow<map.notes.size() && bgm.getPosition()>=map.notes.get(cheatNow).first-0.1f){
			
			if(autoplay){
				MoveToAction mta=Actions.moveToAligned((float)(map.notes.get(cheatNow).second.intValue()), (float)player.getY(Align.center), Align.center,0.1f);
				player.addAction(mta);
			}
			cheatNow++;
		}
		
		if(cheatNow==map.notes.size() && bgm.getPosition()>=map.notes.get(cheatNow-1).first+2){
			//Time To quick end
			bgm.stop();
			showMarkStage();
		}
		
		//Add notes
		for(Note single:aliveNotes){
			single.update(delta);
		}
	}

	private void placeNote(int pos) {
		// TODO Place a note
		Pair<Float,Integer> pfi=map.notes.get(pos);
		Note note=notePool.obtain();
		note.init(pfi.second, map.dropTime,player.getY(Align.top));
		aliveNotes.add(note);
		
	}

	private void LabelSync() {
		ls.setText(String.format("%08d",score));
		lc.setText(String.format("%06d", combo)+"x");
		
		lm.setText(String.format("%.0f",mark)+"/300 pts");
		playing.setText(map.name+"("+String.format("%.02f", bgm.getPosition())+")");
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
		//Info getting
		map=Memory.beatmaps.get(Memory.nowSong);
		score=0;
		mark=300;
		combo=0;
		delta=0;
		failMark=300/(float)map.notes.size();
		autoplay=false;
		end=false;
		sb=new SpriteBatch();
		
		//Load assets
		if(map.bgPath.equals("")){
			//Nope
		}else{
			bgt=new Texture(Gdx.files.local("songs/"+map.name+"/"+map.bgPath));
		}
		
		if(map.catcherPath.equals("")){
			playert=new Texture(Gdx.files.internal("default/catcher.png"));
		}else{
			playert=new Texture(Gdx.files.local("songs/"+map.name+"/"+map.catcherPath));
		}
		
		if(map.notePath.equals("")){
			notet=new Texture(Gdx.files.internal("default/note.png"));
		}else{
			notet=new Texture(Gdx.files.local("songs/"+map.name+"/"+map.notePath));
		}
		
		//Stage Init
		s=new Stage();
		
		if(bgt!=null){
			bg=new Image(bgt);
			bg.setBounds(0, 0, VU.width, VU.height);
			s.addActor(bg);
		}
		
		player=new Image(playert);
		player.setPosition(VU.width/2, 50);
		s.addActor(player);
		
		lm=VU.createLabel("300");
		lm.setPosition(0, 0, Align.bottomLeft);
		lm.setColor(map.r,map.g,map.b,map.a);
		s.addActor(lm);
		
		lc=VU.createLabel(String.format("%06d",new Integer(combo))+"x");
		
		lc.setPosition(VU.width,0,Align.bottomRight);
		lc.setColor(map.r,map.g,map.b,map.a);
		s.addActor(lc);
		
		ls=VU.createLabel("00000000");
		ls.setPosition(VU.width,VU.height,Align.topRight);
		ls.setColor(map.r,map.g,map.b,map.a);
		s.addActor(ls);
		
		playing=VU.createLabel(map.name);
		playing.setPosition(0, VU.height,Align.topLeft);
		playing.setColor(map.r,map.g,map.b,map.a);
		s.addActor(playing);
		
		//Song init
		bgm=Gdx.audio.newMusic(Gdx.files.local("songs/"+map.name+"/"+map.songPath));
		bgm.play();
		get=Gdx.audio.newSound(Gdx.files.internal("default/get.mp3"));
		miss=Gdx.audio.newSound(Gdx.files.internal("default/miss.mp3"));
		
		bgm.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(Music arg0) {
				showMarkStage();
			}

		});
		
		//Fade init
		fade=new Image(playert);
		fade.setColor(1, 1, 1, 0.5f);
		s.addActor(fade);
		fade.setZIndex(1);
		
		//Player move check
		Gdx.input.setInputProcessor(s);
		
		s.addListener(new InputListener(){
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				if(!autoplay){
					player.setPosition(x, player.getY(Align.center),Align.center);
				}
				
				return super.mouseMoved(event, x, y);
			}
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				switch(keycode){
					case Input.Keys.P:
						autoplay=!autoplay;
						Gdx.app.log("Autoplay", autoplay+"");
						
				}
					
				return super.keyDown(event, keycode);
			}
		});
		
		
		//Beatmap note init
		map.notes.sort(new Comparator<Pair<Float,Integer>>() {

			@Override
			public int compare(Pair<Float, Integer> o1, Pair<Float, Integer> o2) {
				if(o1.first<o2.first) return -1;
				if(o1.first>o2.first) return 1;
				if(o1.second<o2.second) return -1;
				if(o1.second>o2.second) return 1;
				return 0;
			}
			
		});
		
		noteNow=0;
		cheatNow=0;
		
		//Pool setting
		aliveNotes=new Array<Note>();
		
		//END STAGE SETTING
		es=new Stage();
		
		clap=Gdx.audio.newSound(Gdx.files.internal("music/clap.mp3"));
		l1=VU.createLabel("Score:00000000");
		VU.setTo(l1, 0.2f, 0.6f);
		l2=VU.createLabel("Mark:300/300");
		VU.setTo(l2, 0.2f, 0.4f);
		l3=VU.createLabel("1st!");
		VU.setTo(l3, 0.6f, 0.5f);
		
		esbg=new Image(bgt);
		esbg.setColor(1,1,1,0.25f);
		esbg.setBounds(0, 0, VU.width, VU.height);
		
		st=new Texture(Gdx.files.internal("default/save.jpg"));
		
		save=VU.createButton(st, st,100,100);
		VU.setTo(save, 0.8f, 0.2f);
		save.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				//System.out.println("hello/");
				
				//TODO CALL SAVE
				callSave();
				
				
			}
		});
		
		
		es.addActor(esbg);
		es.addActor(save);
		es.addActor(l1);
		es.addActor(l2);
		es.addActor(l3);
		
	}

	protected void callSave() {
		Saver.save(map.name,score,mark);
		gm.choose();
	}

	protected void showMarkStage() {
		end=true;
		Gdx.input.setInputProcessor(es);
		clap.play();
		esec=0;
		esem=0;
		fcnt=0;
		
		l3.setText(getMark(mark));
	}

	private CharSequence getMark(float marks) {
		if(marks>=map.l1){
			return "1st! :D";
		}
		if(marks>=map.l100){
			return "100th!";
		}
		if(marks>=map.l200){
			return "200th..";
		}
		if(marks>=map.l300){
			return "300th..";
		}
		if(marks>=map.l400){
			return "400th:(";
		}
		if(marks>=map.l500){
			return "500th:(";
		}
		if(marks>=map.l600){
			return "600thXD";
		}
		if(marks>=map.last){
			return "600+ XD";
		}
		return "last :[";
	}

	

}
