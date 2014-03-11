package com.example.crossedlines.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.view.View;

import com.example.crossedlines.Game;
import com.example.crossedlines.GameSettings;
import com.example.crossedlines.R;

public class GameView extends View {

	Paint paint;

	public GameView(Context context) {
		super(context);
		initComponents();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < GameSettings.Instance().rectsCount; i++)
			for (int j = 0; j < GameSettings.Instance().rectsCount; j++) {
				paint.setColor(getColor(Game.Instance().gameArr[i][j]));
				if (Game.Instance().isRowMovingHorizontal(i)) {
					drawHorizontalRect(i, j, canvas);
				} else if (Game.Instance().isColumnMovingVertical(j)) {
					drawVerticalRect(i, j, canvas);
				} else {
					drawRect(i, j, canvas);
				}
			}
		if (!Game.Instance().isGameOver) {
		   	Paint paint2 = new Paint();
		   	Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
		    paint2.setTypeface(tf);
		    paint2.setColor(Color.WHITE);
		    paint2.setTextSize(60);
		    paint2.setAntiAlias(true);
		    paint2.setTextAlign(Align.RIGHT);
		    canvas.drawText(String.format("%d / %d", Game.Instance().score, GameSettings.Instance().time), GameSettings.Instance().width - GameSettings.Instance().marginRect, 70, paint2);
//		    canvas.drawText(String.format("Time: %d", GameSettings.Instance().time), 0, 20, paint2);
		}
		else {
		   	Paint paint2 = new Paint();
		    paint2.setColor(Color.BLACK);
		   	Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
		    paint2.setTypeface(tf);
		    paint2.setTextSize(180);
		    paint2.setAntiAlias(true);
		    paint2.setTextAlign(Align.CENTER);

		    canvas.drawText("Game", GameSettings.Instance().width / 2, 325, paint2);
		    canvas.drawText("Over", GameSettings.Instance().width / 2, 485, paint2);
		}
	}

	private void drawRect(int rowIndex, int columnIndex, Canvas canvas) {
		canvas.drawRect(GameSettings.Instance().rectHorizontalStartPoint
				+ (columnIndex * GameSettings.Instance().getRectSize())
				+ GameSettings.Instance().marginRect,
				GameSettings.Instance().rectVerticalStartPoint
						+ (rowIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect,
				GameSettings.Instance().rectHorizontalStartPoint
						+ (columnIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(),
				GameSettings.Instance().rectVerticalStartPoint
						+ (rowIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(), paint);
	}

	private void drawHorizontalRect(int rowIndex, int columnIndex, Canvas canvas) {
		keepOnScreen(rowIndex, columnIndex,
				GameSettings.Instance().rectHorizontalStartPoint
						+ (columnIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect
						+ Game.Instance().getXDiffer() * -1,
				GameSettings.Instance().rectVerticalStartPoint
						+ (rowIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect,
				GameSettings.Instance().rectHorizontalStartPoint
						+ (columnIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize()
						+ Game.Instance().getXDiffer() * -1,
				GameSettings.Instance().rectVerticalStartPoint
						+ (rowIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(), canvas);
	}

	private void drawVerticalRect(int rowIndex, int columnIndex, Canvas canvas) {
		keepOnScreen(rowIndex, columnIndex,
				GameSettings.Instance().rectHorizontalStartPoint
						+ (columnIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect,
				GameSettings.Instance().rectVerticalStartPoint
						+ (rowIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect
						+ Game.Instance().getYDiffer() * -1,
				GameSettings.Instance().rectHorizontalStartPoint
						+ (columnIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(),
				GameSettings.Instance().rectVerticalStartPoint
						+ (rowIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize()
						+ Game.Instance().getYDiffer() * -1, canvas);
	}

	private void keepOnScreen(int rowIndex, int columnIndex, float leftPoint,
			float topPoint, float rightPoint, float bottomPoint, Canvas canvas) {
		
		if (Game.Instance().isRowMovingHorizontal(rowIndex)) {
			
			if (isPointOverRightEdge(rightPoint)) {				
				canvas.drawRect(getFixedLeftTurnedLeftPoint(leftPoint),	topPoint, getLeftTurnedPoint(rightPoint), bottomPoint, paint);
				
				if (!isPointOverRightEdge(leftPoint))
					canvas.drawRect(leftPoint, topPoint, getRightEdgePoint(), bottomPoint, paint);
				
			} else if (isPointOverLeftEdge(leftPoint)) {
				canvas.drawRect(getRightTurnedPoint(leftPoint),	topPoint, getFixedRightTurnedLeftPoint(rightPoint), bottomPoint, paint);
				
				if (!isPointOverLeftEdge(rightPoint))
					canvas.drawRect(getLeftEdgePoint(), topPoint, rightPoint, bottomPoint, paint);
				
			} else {
				canvas.drawRect(leftPoint, topPoint, rightPoint, bottomPoint,
						paint);
			}
			
		} else if (Game.Instance().isColumnMovingVertical(columnIndex)) {
			if (topPoint < GameSettings.Instance().rectVerticalStartPoint) {
				canvas.drawRect(
						leftPoint,
						topPoint + GameSettings.Instance().width,
						rightPoint,
						(bottomPoint + GameSettings.Instance().width) > (GameSettings
								.Instance().rectVerticalStartPoint + GameSettings
								.Instance().width) ? (GameSettings.Instance().rectVerticalStartPoint + GameSettings
								.Instance().width)
								: (bottomPoint + GameSettings.Instance().width),
						paint);
				if (bottomPoint > GameSettings.Instance().rectVerticalStartPoint)
					canvas.drawRect(leftPoint,
							GameSettings.Instance().rectVerticalStartPoint,
							rightPoint, bottomPoint, paint);
			} else if (bottomPoint > GameSettings.Instance().rectVerticalStartPoint
					+ GameSettings.Instance().width) {
				canvas.drawRect(
						leftPoint,
						(topPoint - GameSettings.Instance().width) < GameSettings
								.Instance().rectVerticalStartPoint ? GameSettings
								.Instance().rectVerticalStartPoint
								: (topPoint - GameSettings.Instance().width),
						rightPoint,
						bottomPoint - GameSettings.Instance().width, paint);

				if (topPoint < GameSettings.Instance().rectVerticalStartPoint
						+ GameSettings.Instance().width)
					canvas.drawRect(leftPoint, topPoint, rightPoint,
							GameSettings.Instance().rectVerticalStartPoint
									+ GameSettings.Instance().width, paint);
			} else {
				canvas.drawRect(leftPoint, topPoint, rightPoint, bottomPoint,
						paint);
			}
		}
	}
	
	private float getRightEdgePoint() {
		return GameSettings.Instance().rectHorizontalStartPoint + GameSettings.Instance().width;
	}
	
	private boolean isPointOverRightEdge(float point) {
		return point > getRightEdgePoint();
	}
	
	private float getLeftEdgePoint() {
		return GameSettings.Instance().rectHorizontalStartPoint;
	}
	
	private boolean isPointOverLeftEdge(float point) {
		return point < getLeftEdgePoint();
	}
	

	
	private float getLeftTurnedPoint(float point) {
		return point - GameSettings.Instance().width;
	}
	
	private float getRightTurnedPoint(float point) {
		return point + GameSettings.Instance().width;
	}
	
	
	
	private boolean isLeftTurnedPointOverTheLeftEdge(float point) {
		return getLeftTurnedPoint(point) < GameSettings.Instance().rectHorizontalStartPoint;
	}
	
	private boolean isRightTurnedPointOverTheLeftEdge(float point) {
		return getRightTurnedPoint(point) > getRightEdgePoint();
	}
	
	private float getFixedLeftTurnedLeftPoint(float leftPoint) {
		return isLeftTurnedPointOverTheLeftEdge(leftPoint) ? getLeftEdgePoint() : getLeftTurnedPoint(leftPoint);
	}
	
	private float getFixedRightTurnedLeftPoint(float rightPoint) {
		return isRightTurnedPointOverTheLeftEdge(rightPoint) ? getRightEdgePoint() : getRightTurnedPoint(rightPoint);
	}
	
	private void initComponents() {
		paint = new Paint();
	}

	private int getColor(int colorNumber) {
		switch (colorNumber) {
		case 1:
			return getResources().getColor(R.color.color_brown);
		case 2:
			return getResources().getColor(R.color.color_orange);
		case 3:
			return getResources().getColor(R.color.color_green);
		case 4:
			return getResources().getColor(R.color.color_ivory);
		case 5:
			return getResources().getColor(R.color.color_blue);
		case 6:
			return getResources().getColor(R.color.color_purple);
		}
		return Color.WHITE;
	}

}