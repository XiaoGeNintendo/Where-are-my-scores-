package com.hhs.xgn.wams.storage;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hhs.xgn.gdxMusic.util.Pair;

public class Loader {

	public static void readAll() {
		FileHandle root=Gdx.files.local("songs");
		if(root.exists()==false){
			root.mkdirs();
		}
		
		for(FileHandle bd:root.list()){
			read(bd);
		}
	}
	
	public static void read(FileHandle root){
		Beatmap beat=new Beatmap();
		beat.name=root.nameWithoutExtension();
		
		//Going to parse the Json files
		FileHandle json=Gdx.files.local("songs/"+root.name()+"/beatmap.json");
		
		JsonParser parser=new JsonParser();
		String info=json.readString();
		JsonObject jsr=(JsonObject)parser.parse(info);
		
		beat.bgPath=jsr.get("bgPath").getAsString();
		beat.catcherPath=jsr.get("catcherPath").getAsString();
		beat.dropTime=jsr.get("dropTime").getAsInt();
		beat.l1=jsr.get("l1").getAsInt();
		beat.l100=jsr.get("l100").getAsInt();
		beat.l200=jsr.get("l200").getAsInt();
		beat.l300=jsr.get("l300").getAsInt();
		beat.l400=jsr.get("l400").getAsInt();
		beat.l500=jsr.get("l500").getAsInt();
		beat.l600=jsr.get("l600").getAsInt();
		beat.last=jsr.get("last").getAsInt();
		beat.notePath=jsr.get("notePath").getAsString();
		beat.songPath=jsr.get("songPath").getAsString();
		beat.r=jsr.get("r").getAsFloat();
		beat.g=jsr.get("g").getAsFloat();
		beat.b=jsr.get("b").getAsFloat();
		beat.a=jsr.get("a").getAsFloat();
		
		JsonArray ja=jsr.get("notes").getAsJsonArray();
		for(int i=0;i<ja.size();i++){
			JsonArray eachNote=ja.get(i).getAsJsonArray();
			
			beat.notes.add(new Pair<Float,Integer>(eachNote.get(0).getAsFloat(),eachNote.get(1).getAsInt()));
		}
		
		Memory.beatmaps.add(beat);
	}
}
