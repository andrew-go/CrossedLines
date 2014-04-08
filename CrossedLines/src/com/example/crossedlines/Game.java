package com.example.crossedlines;

import java.util.Random;

import android.view.MotionEvent;
import android.view.View;

import com.example.crossedlines.Views.GameView;

public class Game {

	int thresHold = 10;

	public float startX;
	public float startY;

	public float currentX;
	public float currentY;
	
	public float moveX;
	public float moveY;

	public enum Way {
		UP, DOWN, LEFT, RIGHT
	}

	public Way way;
	public int[][] gameArr = new int[GameSettings.Instance().rectsCount][GameSettings
			.Instance().rectsCount];
	public Random random = new Random();
	public SelectedRect selecetedRect;
	public boolean touchActionMoveStatus;

	public int score;
	
	public int gameTime = GameSettings.Instance().time;

	public boolean isGameOver = false;

	public GameView gameView;
	public GameThread gameThread;
	
	public IGameOverHandler gameOverHandler;

	public static Game instance;

	public static Game Instance() {
		return instance == null ? instance = new Game() : instance;
	}

	public Game() {
		initArray();
	}

	public void startNewGame() {
		if (gameThread == null)
			return;
		Game.Instance().gameThread.interrupt();
		Game.Instance().initArray();

		Game.Instance().score = 0;
		gameTime = GameSettings.Instance().time;
		Game.Instance().isGameOver = false;
		
		gameThread = new GameThread(gameView, gameOverHandler);
		gameThread.start();
		gameView.postInvalidate();
	}

	public void initArray() {
		for (int i = 0; i < GameSettings.Instance().rectsCount; i++)
			for (int j = 0; j < GameSettings.Instance().rectsCount; j++)
				gameArr[i][j] = random
						.nextInt(GameSettings.Instance().colorsCount + 1) + 1;
	}
		
	public void setWay() {
		if ((currentX < (startX - thresHold) && (currentY > (startY - thresHold)) && (currentY < (startY + thresHold)))) {
			Game.Instance().way = Way.LEFT;
			touchActionMoveStatus = false;
		} else if ((currentX > (startX + thresHold)
				&& (currentY > (startY - thresHold)) && (currentY < (startY + thresHold)))) {
			Game.Instance().way = Way.RIGHT;
			touchActionMoveStatus = false;
		} else if ((currentY < (startY - thresHold)
				&& (currentX > (startX - thresHold)) && (currentX < (startX + thresHold)))) {
			Game.Instance().way = Way.UP;
			touchActionMoveStatus = false;
		} else if ((currentY > (startY + thresHold)
				&& (currentX > (startX - thresHold)) && (currentX < (startX + thresHold)))) {
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
		selecetedRect = new SelectedRect((int) (x / (GameSettings.Instance().width / GameSettings.Instance().rectsCount)),
				(int) (y / (GameSettings.Instance().width / GameSettings.Instance().rectsCount)));
	}

	public float getXDiffer() {
		return startX - currentX;
	}

	public float getYDiffer() {
		return startY - currentY;
	}

	public void pickUpArr(MotionEvent event) {
		int currentRowIndex = (int) ((event.getY() - GameSettings.Instance().rectVerticalStartPoint) / (GameSettings.Instance().width / GameSettings
				.Instance().rectsCount));
		int currentColumnIndex = (int) ((event.getX() - GameSettings.Instance().rectHorizontalStartPoint) / (GameSettings.Instance().width / GameSettings
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

	public boolean checkCombinedLines() {
		boolean wasChanged = false;
		int count = 0;
		for (int i = 0; i < GameSettings.Instance().rectsCount; i ++)
			for (int j = 0; j < GameSettings.Instance().rectsCount; j ++) {
				if (j < GameSettings.Instance().rectsCount - 1 && Math.abs(gameArr[i][j]) == Math.abs(gameArr[i][j+1]))
					count++;
				else {
					if (count > 2) {
						gameTime += count - 2;
						for (int k = j - count; 0 <= count; k++) {
							gameArr[i][k] = Math.abs(gameArr[i][k]) * -1;
							count--;
							wasChanged = true;
							score++;
						}
					}
					count = 0;
				}
			}
		for (int i = 0; i < GameSettings.Instance().rectsCount; i ++)
			for (int j = 0; j < GameSettings.Instance().rectsCount; j ++) {
				if (j < GameSettings.Instance().rectsCount - 1 && Math.abs(gameArr[j][i]) == Math.abs(gameArr[j+1][i]))
					count++;
				else {
					if (count > 2) {
						gameTime += count - 2;
						for (int k = j - count; 0 <= count; k++) {
							gameArr[k][i] = Math.abs(gameArr[k][i]) * -1;
							count--;
							wasChanged = true;
							score++;
						}
					}
					count = 0;
				}
			}
		if (wasChanged) {

			gameThread.isPaused = true;
//			drawThread = new DrawThread(gameView);
//			drawThread.start();
			gameView.postInvalidate();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameThread.isPaused = false;
		}
		for (int i = 0; i < GameSettings.Instance().rectsCount; i ++)
			for (int j = 0; j < GameSettings.Instance().rectsCount; j ++) 
				if (gameArr[i][j] < 0)
					lowerColumn(i, j);
		return wasChanged;
	}
	
	private void lowerColumn(int rowIndex, int columnIndex) {
		for (int i = rowIndex; i > 0; i--)
			gameArr[i][columnIndex] = gameArr[i - 1][columnIndex];
		gameArr[0][columnIndex] = random.nextInt(GameSettings.Instance().colorsCount + 1) + 1;
	}

	public static class SelectedRect {

		public int columnIndex;
		public int rowIndex;

		public SelectedRect(int columnIndex, int rowIndex) {
			this.columnIndex = columnIndex;
			this.rowIndex = rowIndex;
		}
	}

	public static class GameThread extends Thread {

		View view;
		IGameOverHandler gameOverHandler;
		boolean isPaused;

		public GameThread(View view, IGameOverHandler gameOverHandler) {
			this.view = view;
			this.gameOverHandler = gameOverHandler;
		}

	    public void run() {
    		int count = 0;
	    	while (!Game.Instance().isGameOver) {
	    		if (isPaused)
	    			continue;
	    		if (Game.Instance().gameTime == 0) {
	    			Game.Instance().isGameOver = true;
	    			gameOverHandler.onGameOver();
	    			return;
	    		}   		
		    	try {
					Thread.sleep(100);
					if (count++ == 10) {
						Game.Instance().gameTime--;
						count = 0;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	view.postInvalidate();
	    	}
	    }

	}
	
//	public static class DrawThread extends Thread {
//
//		View view;
//
//		public DrawThread(View view) {
//			this.view = view;
//		}
//
//	    public void run() {
//    		int count = 0;
//	    	while (count < 3) {
//	    		try {
//					sleep(300);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	    		count++;
//		    	view.postInvalidate();
//	    	}
//	    }
//
//	}
	
	public boolean isOnGameView(MotionEvent event) {
		return event.getX() > GameSettings.Instance().rectHorizontalStartPoint && event.getX() < GameSettings.Instance().rectHorizontalStartPoint + GameSettings.Instance().width 
				&& event.getY() > GameSettings.Instance().rectVerticalStartPoint && event.getY() < GameSettings.Instance().rectVerticalStartPoint + GameSettings.Instance().width;
	}

}