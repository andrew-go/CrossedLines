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
		createArray();
	}

	private void createArray() {
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
		int[][] arr = new int[GameSettings.Instance().rectsCount][GameSettings
				.Instance().rectsCount];
		int currentRowIndex = (int) (event.getY() / (GameSettings.Instance().width / GameSettings
				.Instance().rectsCount));
		int currentColumnIndex = (int) (event.getX() / (GameSettings.Instance().width / GameSettings
				.Instance().rectsCount));

	}

	private void changeRow() {
		int prev = -1;
		int next = -1;
		for (int i = 0; i < GameSettings.Instance().rectsCount; i++) {
			if (i == GameSettings.Instance().rectsCount - 1) {
				gameArr[selecetedRect.columnIndex][0] = gameArr[selecetedRect.columnIndex][i];
			}
			else { 
				prev = next;
				next = gameArr[selecetedRect.columnIndex][i];
			}
			gameArr[selecetedRect.columnIndex][i] = prev; 
		}
	}
	
	private void changeColumn() {
		int prev = -1;
		int next = -1;
		for (int i = 0; i < GameSettings.Instance().rectsCount; i++) {
			if (i == GameSettings.Instance().rectsCount - 1) {
				gameArr[i][selecetedRect.columnIndex] = gameArr[i][selecetedRect.columnIndex];
			}
			else { 
				prev = next;
				next = gameArr[i][selecetedRect.columnIndex];
			}
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