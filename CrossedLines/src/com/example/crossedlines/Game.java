package com.example.crossedlines;

import java.util.Random;

import android.view.MotionEvent;

public class Game {

	int thresHold = 10;

	public float startX;
	public float startY;

	public float currentX;
	public float currentY;

	public enum Way {
		UP, DOWN, LEFT, RIGHT
	}

	public Way way;
	public int[][] gameArr = new int[GameSettings.Instance().rectsCount][GameSettings
			.Instance().rectsCount];
	public int[][] gameArrClone = new int[GameSettings.Instance().rectsCount][GameSettings
			.Instance().rectsCount];
	public Random random = new Random();
	public SelectedRect selecetedRect;
	public boolean touchActionMoveStatus;

	public static Game instance;

	public static Game Instance() {
		return instance == null ? instance = new Game() : instance;
	}

	public Game() {
		initArray();
	}

	public void initArray() {
		for (int i = 0; i < GameSettings.Instance().rectsCount; i++)
			for (int j = 0; j < GameSettings.Instance().rectsCount; j++)
				gameArr[i][j] = random
						.nextInt(GameSettings.Instance().colorsCount + 1);
	}

	public void setWay(float firstX, float firstY, float secondX, float secondY) {
		if ((secondX < (firstX - thresHold) && (secondY > (firstY - thresHold)) && (secondY < (firstY + thresHold)))) {
			Game.Instance().way = Way.LEFT;
			touchActionMoveStatus = false;
		} else if ((secondX > (firstX + thresHold)
				&& (secondY > (firstY - thresHold)) && (secondY < (firstY + thresHold)))) {
			Game.Instance().way = Way.RIGHT;
			touchActionMoveStatus = false;
		} else if ((secondY < (firstY - thresHold)
				&& (secondX > (firstX - thresHold)) && (secondX < (firstX + thresHold)))) {
			Game.Instance().way = Way.UP;
			touchActionMoveStatus = false;
		} else if ((secondY > (firstY + thresHold)
				&& (secondX > (firstX - thresHold)) && (secondX < (firstX + thresHold)))) {
			Game.Instance().way = Way.DOWN;
			touchActionMoveStatus = false;
		}

	}

	public boolean isRowMovingHorizontal(int rowIndex) {
		return isMovingHorizontal()
				&& Game.Instance().selecetedRect.rowIndex == rowIndex;
	}

	public boolean isMovingHorizontal() {
		return Game.Instance().way != null
				&& (Game.Instance().way == Way.LEFT || Game.Instance().way == Way.RIGHT);
	}

	public boolean isColumnMovingVertical(int columnIndex) {
		return isMovingVertical()
				&& Game.Instance().selecetedRect.columnIndex == columnIndex;
	}

	private boolean isMovingVertical() {
		return Game.Instance().way != null
				&& (Game.Instance().way == Way.UP || Game.Instance().way == Way.DOWN);
	}

	public void clearPreviousInfo() {
		way = null;
		selecetedRect = null;
	}

	public void selectStartRect(float x, float y) {
		Game.Instance().selecetedRect = new SelectedRect(
				(int) (x / (GameSettings.Instance().width / GameSettings
						.Instance().rectsCount)),
				(int) (y / (GameSettings.Instance().width / GameSettings
						.Instance().rectsCount)));
	}

	public float getXDiffer() {
		return startX - currentX;
	}

	public float getYDiffer() {
		return startY - currentY;
	}

	public void pickUpArr(MotionEvent event) {
		int currentRowIndex = (int) (event.getY() / (GameSettings.Instance().width / GameSettings
				.Instance().rectsCount));
		int currentColumnIndex = (int) (event.getX() / (GameSettings.Instance().width / GameSettings
				.Instance().rectsCount));
		if (way == Way.RIGHT) {
			for (int i = 0; i < (currentColumnIndex - selecetedRect.columnIndex); i++)
				changeRowRight();
		}
		if (way == Way.LEFT) {
			for (int i = 0; i < (selecetedRect.columnIndex - currentColumnIndex); i++)
				changeRowLeft();
		}
		if (way == Way.UP) {
			for (int i = 0; i < (selecetedRect.rowIndex - currentRowIndex); i++)
				changeColumnUp();
		}
		if (way == Way.DOWN) {
			for (int i = 0; i < (currentRowIndex - selecetedRect.rowIndex); i++)
				changeColumnDown();
		}
		selecetedRect.rowIndex++;
	}

	private void changeRowRight() {
		int prev = -1;
		int next = -1;
		for (int i = 0; i < GameSettings.Instance().rectsCount; i++) {
			prev = next;
			if (i == GameSettings.Instance().rectsCount - 1)
				gameArr[selecetedRect.rowIndex][0] = gameArr[selecetedRect.rowIndex][i];
			else				
				next = gameArr[selecetedRect.rowIndex][i];
			gameArr[selecetedRect.rowIndex][i] = prev; 
		}		
	}
	
	private void changeRowLeft() {
		int prev = -1;
		int next = -1;
		for (int i = GameSettings.Instance().rectsCount - 1; i >= 0; i--) {
			prev = next;
			if (i == 0)
				gameArr[selecetedRect.rowIndex][GameSettings.Instance().rectsCount - 1] = gameArr[selecetedRect.rowIndex][i];
			else				
				next = gameArr[selecetedRect.rowIndex][i];
			gameArr[selecetedRect.rowIndex][i] = prev; 
		}		
	}
	
	private void changeColumnDown() {
		int prev = -1;
		int next = -1;
		for (int i = 0; i < GameSettings.Instance().rectsCount; i++) {
			prev = next;
			if (i == GameSettings.Instance().rectsCount - 1)
				gameArr[0][selecetedRect.columnIndex] = gameArr[i][selecetedRect.columnIndex];
			else
				next = gameArr[i][selecetedRect.columnIndex];
			gameArr[i][selecetedRect.columnIndex] = prev; 
		}
		
	}
	
	private void changeColumnUp() {
		int prev = -1;
		int next = -1;
		for (int i = GameSettings.Instance().rectsCount - 1; i >= 0; i--) {
			prev = next;
			if (i == 0)
				gameArr[GameSettings.Instance().rectsCount - 1][selecetedRect.columnIndex] = gameArr[i][selecetedRect.columnIndex];
			else
				next = gameArr[i][selecetedRect.columnIndex];
			gameArr[i][selecetedRect.columnIndex] = prev; 
		}
		
	}

	public static class SelectedRect {

		public int columnIndex;
		public int rowIndex;

		public SelectedRect(int columnIndex, int rowIndex) {
			this.columnIndex = columnIndex;
			this.rowIndex = rowIndex;
		}

	}

}