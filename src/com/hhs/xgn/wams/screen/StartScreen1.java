package com.hhs.xgn.wams.screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hhs.xgn.gdxMusic.util.VU;
import com.hhs.xgn.wams.play.GameMain;

public class StartScreen1 implements Screen {

	AssetManager am;
	Image i;
	Stage s;
	GameMain gm;
	float sum;
	
	public StartScreen1(GameMain gm){
		this.gm=gm;
		
		am=new AssetManager();
		s=new Stage(new StretchViewport(VU.width, VU.height));
		
		am.load("start/hhs.jpg",Texture.class);
		
		am.finishLoading();
		
		i=new Image(am.get("start/hhs.jpg",Texture.class));
		
		VU.setMiddle(i,100,100);
		s.addActor(i);
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		s.dispose();
		am.dispose();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		sum+=arg0;
		
		
		
		
		s.act();
		
		s.draw();
		if(sum>=1f){
			gm.Start2();
		}
		
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
		sum=0;
	}

}
