package com.example.crossedlines;

import java.util.ArrayList;
import java.util.List;

public class PixelLetterContainer {
	
	private int[][] s;
	private int[][] c; 
	private int[][] r; 
	private int[][] o; 
	private int[][] l;
	private int[][] i; 
	private int[][] n;
	private int[][] e;
	
	public List<int[][]> scrollWord;
	public List<int[][]> lineWord;
	
	public PixelLetterContainer() {
		initLetters();
	}
	
	private void initLetters() {
		s = getS();
		c = getC();
		r = getR();
		o = getO();
		l = getL();
		i = getI();
		n = getN();
		e = getE();
		scrollWord = new ArrayList<int[][]>();
		scrollWord.add(s);
		scrollWord.add(c);
		scrollWord.add(r);
		scrollWord.add(o);
		scrollWord.add(l);
		scrollWord.add(l);
		lineWord = new ArrayList<int[][]>();
		lineWord.add(l);
		lineWord.add(i);
		lineWord.add(n);
		lineWord.add(e);
	}

	private int[][] getS() {
		int[][] letterS = {{0,1,1,1,1,1,0},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,0,0},
							{0,1,1,1,1,1,0},
							{0,0,0,0,0,1,1},
							{1,1,0,0,0,1,1},
							{0,1,1,1,1,1,0}};
		return letterS;
	}
	
	private int[][] getC() {
		int[][] letterC = {{0,1,1,1,1,1,0},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,0,0},
							{1,1,0,0,0,0,0},
							{1,1,0,0,0,0,0},
							{1,1,0,0,0,1,1},
							{0,1,1,1,1,1,0}};
		return letterC;
	}
	
	private int[][] getR() {
		int[][] letterR = {{1,1,1,1,1,1,0},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,1,1},
							{1,1,0,0,1,1,1},
							{1,1,1,1,1,0,0},
							{1,1,0,1,1,1,0},
							{1,1,0,0,1,1,1}};
		return letterR;
	}
	
	private int[][] getO() {
		int[][] letterO = {{0,1,1,1,1,1,0},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,1,1},
							{1,1,0,0,0,1,1},
							{0,1,1,1,1,1,0}};
		return letterO;
	}
	
	private int[][] getL() {
		int[][] letterL = {{1,1,0,0,0,0},
							{1,1,0,0,0,0},
							{1,1,0,0,0,0},
							{1,1,0,0,0,0},
							{1,1,0,0,0,0},
							{1,1,0,0,0,0},
							{1,1,1,1,1,1}};
		return letterL;
	}
	
	private int[][] getI() {
		int[][] letterI = {{1,1,1,1},
							{0,1,1,0},
							{0,1,1,0},
							{0,1,1,0},
							{0,1,1,0},
							{0,1,1,0},
							{1,1,1,1}};
		return letterI;
	}
	
	private int[][] getN() {
		int[][] letterN = {{1,1,0,0,0,0,1,1},
							{1,1,1,0,0,0,1,1},
							{1,1,1,1,0,0,1,1},
							{1,1,0,1,1,0,1,1},
							{1,1,0,0,1,1,1,1},
							{1,1,0,0,0,1,1,1},
							{1,1,0,0,0,0,1,1},};
		return letterN;
	}
	
	private int[][] getE() {
		int[][] letterE = {{1,1,1,1,1,1,1},
							{1,1,0,0,0,0,0},
							{1,1,0,0,0,0,0},
							{1,1,1,1,1,1,0},
							{1,1,0,0,0,0,0},
							{1,1,0,0,0,0,0},
							{1,1,1,1,1,1,1}};
		return letterE;
	}
}
