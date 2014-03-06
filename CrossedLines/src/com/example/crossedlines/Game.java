package com.example.crossedlines;

import java.util.Random;

public class Game {
	
	static Game instance;
	
	public static Game Instance() {
		return instance == null ? instance = new Game() : instance;
	}
	
	public int[][] gameArr = new int[8][8];
	public Random random = new Random();
	
	public Game() {
		createArray();
	}
	
	private void createArray() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				gameArr[i][j] = random.nextInt(6);
	}
	
}
