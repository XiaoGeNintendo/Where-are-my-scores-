package com.hhs.xgn.wams.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.hhs.xgn.gdxMusic.util.Pair;

public class Saver {

	public static void save(String name, int score, float mark) {
		Preferences p=Gdx.app.getPreferences(name);
		int mx_s=p.getInteger("max_score",-1);
		float mx_m=p.getFloat("max_mark",-1);
		p.putInteger("max_score", Math.max(score, mx_s));
		p.putFloat("max_mark", Math.max(mark, mx_m));
		p.flush();
	}
	
	/**
	 * Returns the maximum score of <i>name</i> map. <br/>
	 * first=max_score second=max_mark  <br/>
	 * -1 for didn't play
	 * @param name
	 * @return
	 */
	public static Pair<Integer,Float> played(String name){
		Preferences p=Gdx.app.getPreferences(name);
		Pair<Integer,Float> pif=new Pair<Integer,Float>();
		pif.first=p.getInteger("max_score",-1);
		pif.second=p.getFloat("max_mark",-1);
		return pif;
	}
}
