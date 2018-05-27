package com.hhs.xgn.gdxMusic.util;

/**
 * Pair Float Int in C++
 * @author XGN
 *
 */
public class Pair<T1,T2> {
	public T1 first;
	public T2 second;
	public Pair(T1 a,T2 b){
		first=a;
		second=b;
	}
	public Pair(){
		
	}
	
	public String toString(){
		return first+" "+second;
	}
}
