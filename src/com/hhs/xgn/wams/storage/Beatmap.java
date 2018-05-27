package com.hhs.xgn.wams.storage;

import java.util.*;

import com.hhs.xgn.gdxMusic.util.Pair;

public class Beatmap {
	public String name;
	/**Unit:Ms*/
	public int dropTime;
	/**The first is time the second is x axis*/
	public List<Pair<Float,Integer>> notes=new ArrayList<Pair<Float,Integer>>();
	public String bgPath;
	public String notePath;
	public String catcherPath;
	public String songPath;
	/**The score lines*/
	public int l1,l100,l200,l300,l400,l500,l600,last;
	/**Font colors*/
	public float r,g,b,a;
	@Override
	public String toString() {
		return "Beatmap [name=" + name + ", dropTime=" + dropTime + ", notes=" + notes + ", bgPath=" + bgPath
				+ ", notePath=" + notePath + ", catcherPath=" + catcherPath + ", songPath=" + songPath + ", l1=" + l1
				+ ", l100=" + l100 + ", l200=" + l200 + ", l300=" + l300 + ", l400=" + l400 + ", l500=" + l500
				+ ", l600=" + l600 + ", last=" + last + ", r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}
	
	
}
