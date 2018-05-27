package com.hhs.xgn.wams.display;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hhs.xgn.gdxMusic.util.VU;

public class Note implements Poolable {
	
	public Vector2 pos;
	public boolean alive;
	public float speed;
	
	public Note(){
		pos=new Vector2();
		alive=false;
	}
	
	public void init(float posX,int timeToDrop,float dest){
		pos.set(posX,VU.height);
		float dis=VU.height-dest;
		float a=timeToDrop/1000f;
		
		speed=dis/a;
		alive=true;
	}
	
	
	@Override
	public void reset() {
		pos.set(0,0);
		alive=false;
		speed=0;
	}

	public void update(float delta){
		pos.add(0, -speed*delta);
		
		if(pos.y<0){
			alive=false;
		}
	}
}