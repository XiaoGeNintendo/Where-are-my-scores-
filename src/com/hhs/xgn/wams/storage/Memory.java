package com.hhs.xgn.wams.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage information <br/>
 * 1.Each beatmap in one folder. The folder is its name. <br/>
 * 2.In each folder contains "beatmap.json" and the required file <br/>
 * 3. in "beatmap.json" it should contain the keywords of Beatmap <br/>
 * 
 * @author XGN
 *
 */
public class Memory {
	public static List<Beatmap> beatmaps=new ArrayList<Beatmap>();
	public static int nowSong;
	
}
