package com.example.crossedlines;

public class GameSettings {	

	static GameSettings instance;
	
	public static GameSettings Instance() {
		return instance == null ? instance = new GameSettings() : instance;
	}
	
	public int height;
	public int width;
	
	public int rectSize = width/8;
	
	public int rectPoint = 0;

}