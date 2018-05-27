package com.hhs.xgn.gdxMusic.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

/**
 * The Util Class.
 * @author XGN
 */

public class VU {
	
	/**Global width*/
	public static int width=960;
	/**Global height*/
	public static int height=585;
	
	public static void setMiddle(Actor a,int ww,int hh){
		a.setSize(ww, hh);
		a.setPosition(width/2-a.getWidth()/2,height/2-a.getHeight()/2);	
	}
	
	/**
	 * Set the actor to the hor of width and ver of height <br/>
	 * like setTo(a,0.5f,0.5f)=setMiddle(a)
	 * @param a
	 * @param hor
	 * @param ver
	 */
	public static void setTo(Actor a,float hor,float ver){
		float expectX=(width*hor);
		float expectY=(height*ver);
		a.setPosition(expectX-a.getWidth()/2, expectY-a.getHeight()/2);
	}
	
	public static Label createLabel(String s){
		LabelStyle ls=new LabelStyle();
		
		BitmapFont bf=new BitmapFont(Gdx.files.internal("font/zjs.fnt"));
		ls.font=bf;
		ls.fontColor=new Color(1,1,1,1);
		Label lb=new Label(s,ls);
		return lb;
	}
	
	public static Button createButton(Texture down,Texture up,int sx,int sy){
		ButtonStyle bs=new ButtonStyle();
		bs.down=new TextureRegionDrawable(new TextureRegion(down));
		bs.up=new TextureRegionDrawable(new TextureRegion(up));
		Button b=new Button(bs);
		b.setSize(sx, sy);
		return b;
	}
	
	public static Button createButton(Texture down,Texture up,int x,int y,int sx,int sy){
		ButtonStyle bs=new ButtonStyle();
		bs.down=new TextureRegionDrawable(new TextureRegion(down));
		bs.up=new TextureRegionDrawable(new TextureRegion(up));
		Button b=new Button(bs);
		b.setPosition(x, y);
		b.setSize(sx, sy);
		return b;
	}
	
	public static void clear(float r,float g,float b,float a){
		Gdx.gl.glClearColor(r,g,b,a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void setMiddle(Actor actor) {
		setMiddle(actor,(int) actor.getWidth(), (int)actor.getHeight());
	}

	/**
	 * Easy render the stage
	 * @param s
	 */
	public static void render(Stage s) {
		VU.clear(0, 0, 0, 1);
		s.act();
		s.draw();
		
	}

	/**
	 * Easy render the stage with rgba
	 * @param s
	 */
	public static void render(Stage s,float r,float g,float b,float a) {
		VU.clear(r, g, b, a);
		s.act();
		s.draw();
		
	}
	
	public static void disposeAll(Screen... ss) {
		for(Screen s:ss){
			//System.out.println("DisposeAll:"+s);
			if(s!=null){
				s.dispose();
			}
		}
	}
	
	public static void disposeAll(Disposable... ss) {
		for(Disposable s:ss){
			//System.out.println("DisposeAll:"+s);
			if(s!=null){
				s.dispose();
			}
		}
	}

	/**
	 * Check if a point is in a actor
	 * @param x
	 * @param y
	 * @param actor
	 * @return
	 */
	public static boolean pointInActor(float x, float y, Actor actor) {
		Rectangle r=new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		return (r.contains(x,y));
		
	}

	/**
	 * Doesn't work. Don't use
	 * @param obj
	 */
	@Deprecated
	public static void setNull(Object... obj) {
		for(Object o:obj){
			o=null;
		}
	}
}
