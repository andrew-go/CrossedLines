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
		if (!Game.Instance().isGameOver)
			drawInfoPanel(canvas);
		else
			drawGameOver(canvas);
	}

	private void drawRect(int rowIndex, int columnIndex, Canvas canvas) {
		canvas.drawRect(getLeftEdgePoint() + getIndexPosition(columnIndex)
				+ GameSettings.Instance().marginRect, getTopEdgePoint()
				+ getIndexPosition(rowIndex)
				+ GameSettings.Instance().marginRect, getLeftEdgePoint()
				+ getIndexPosition(columnIndex)
				- GameSettings.Instance().marginRect
				+ GameSettings.Instance().getRectSize(), getTopEdgePoint()
				+ getIndexPosition(rowIndex)
				- GameSettings.Instance().marginRect
				+ GameSettings.Instance().getRectSize(), paint);
	}

	private void drawHorizontalRect(int rowIndex, int columnIndex, Canvas canvas) {
		drawFixedRect(rowIndex,	columnIndex, getLeftEdgePoint() + getIndexPosition(columnIndex)	+ GameSettings.Instance().marginRect + getHorizontalDiffer(),
				getTopEdgePoint() + getIndexPosition(rowIndex) + GameSettings.Instance().marginRect,
				getLeftEdgePoint() + getIndexPosition(columnIndex) - GameSettings.Instance().marginRect	+ GameSettings.Instance().getRectSize()	+ getHorizontalDiffer(), 
				getTopEdgePoint() + getIndexPosition(rowIndex) - GameSettings.Instance().marginRect	+ GameSettings.Instance().getRectSize(), canvas);
	}

	private void drawVerticalRect(int rowIndex, int columnIndex, Canvas canvas) {
		drawFixedRect(
				rowIndex,
				columnIndex,
				getLeftEdgePoint() + getIndexPosition(columnIndex)
						+ GameSettings.Instance().marginRect,
				getTopEdgePoint() + getIndexPosition(rowIndex)
						+ GameSettings.Instance().marginRect
						+ getVerticalDiffer(),
				getLeftEdgePoint() + getIndexPosition(columnIndex)
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(),
				getTopEdgePoint() + getIndexPosition(rowIndex)
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize()
						+ getVerticalDiffer(), canvas);
	}

	private void drawGameOver(Canvas canvas) {
		paint.setColor(Color.BLACK);
		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
		paint.setTypeface(tf);
		paint.setTextSize(180);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText("Game", GameSettings.Instance().width / 2, 325, paint);
		canvas.drawText("Over", GameSettings.Instance().width / 2, 485, paint);
	}

	private void drawInfoPanel(Canvas canvas) {
		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
		paint.setTypeface(tf);
		paint.setColor(Color.WHITE);
		paint.setTextSize(60);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(
				String.format("%d / %d", Game.Instance().score,
						GameSettings.Instance().time),
				GameSettings.Instance().width
						- GameSettings.Instance().marginRect, 70, paint);
	}

	private void drawFixedRect(int rowIndex, int columnIndex, float leftPoint,
			float topPoint, float rightPoint, float bottomPoint, Canvas canvas) {

		if (Game.Instance().isRowMovingHorizontal(rowIndex)) {

			if (isPointOverRightEdge(rightPoint)) {
				canvas.drawRect(getFixedLeftTurnedLeftPoint(leftPoint),
						topPoint, getLeftTurnedPoint(rightPoint), bottomPoint,
						paint);

				if (!isPointOverRightEdge(leftPoint))
					canvas.drawRect(leftPoint, topPoint, getRightEdgePoint(),
							bottomPoint, paint);

			} else if (isPointOverLeftEdge(leftPoint)) {
				canvas.drawRect(getRightTurnedPoint(leftPoint), topPoint,
						getFixedRightTurnedRightPoint(rightPoint), bottomPoint,
						paint);

				if (!isPointOverLeftEdge(rightPoint))
					canvas.drawRect(getLeftEdgePoint(), topPoint, rightPoint,
							bottomPoint, paint);

			} else
				canvas.drawRect(leftPoint, topPoint, rightPoint, bottomPoint,
						paint);

		} else if (Game.Instance().isColumnMovingVertical(columnIndex)) {
			if (isPointOverTopEdge(topPoint)) {
				canvas.drawRect(leftPoint, getBottomTurnedPoint(topPoint),
						rightPoint,
						getFixedBottomTurnedBottomPoint(bottomPoint), paint);
				if (!isPointOverTopEdge(bottomPoint))
					canvas.drawRect(leftPoint, getTopEdgePoint(), rightPoint,
							bottomPoint, paint);
			} else if (isPointOverBottomEdge(bottomPoint)) {
				canvas.drawRect(leftPoint, getFixedTopTurnedTopPoint(topPoint),
						rightPoint, getTopTurnedPoint(bottomPoint), paint);

				if (!isPointOverBottomEdge(topPoint))
					canvas.drawRect(leftPoint, topPoint, rightPoint,
							getBottomEdgePoint(), paint);

			} else
				canvas.drawRect(leftPoint, topPoint, rightPoint, bottomPoint,
						paint);
		}
	}

	private int getIndexPosition(int index) {
		return index * GameSettings.Instance().getRectSize();
	}

	private float getHorizontalDiffer() {
		return Game.Instance().getXDiffer() * -1;
	}

	private float getVerticalDiffer() {
		return Game.Instance().getYDiffer() * -1;
	}

	private float getRightEdgePoint() {
		return GameSettings.Instance().rectHorizontalStartPoint
				+ GameSettings.Instance().width;
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

	private float getTopEdgePoint() {
		return GameSettings.Instance().rectVerticalStartPoint;
	}

	private boolean isPointOverTopEdge(float point) {
		return point < getTopEdgePoint();
	}

	private float getBottomEdgePoint() {
		return GameSettings.Instance().rectVerticalStartPoint
				+ GameSettings.Instance().width;
	}

	private boolean isPointOverBottomEdge(float point) {
		return point > getBottomEdgePoint();
	}

	private float getLeftTurnedPoint(float point) {
		return point - GameSettings.Instance().width;
	}

	private float getTopTurnedPoint(float point) {
		return point - GameSettings.Instance().width;
	}

	private float getRightTurnedPoint(float point) {
		return point + GameSettings.Instance().width;
	}

	private float getBottomTurnedPoint(float point) {
		return point + GameSettings.Instance().width;
	}

	private boolean isLeftTurnedPointOverTheLeftEdge(float point) {
		return getLeftTurnedPoint(point) < GameSettings.Instance().rectHorizontalStartPoint;
	}

	private boolean isRightTurnedPointOverTheRightEdge(float point) {
		return getRightTurnedPoint(point) > getRightEdgePoint();
	}

	private boolean isTopTurnedPointOverTheTopEdge(float point) {
		return getTopTurnedPoint(point) < getTopEdgePoint();
	}

	private boolean isBottomTurnedPointOverTheBottomEdge(float point) {
		return getBottomTurnedPoint(point) > getBottomEdgePoint();
	}

	private float getFixedLeftTurnedLeftPoint(float leftPoint) {
		return isLeftTurnedPointOverTheLeftEdge(leftPoint) ? getLeftEdgePoint()
				: getLeftTurnedPoint(leftPoint);
	}

	private float getFixedRightTurnedRightPoint(float rightPoint) {
		return isRightTurnedPointOverTheRightEdge(rightPoint) ? getRightEdgePoint()
				: getRightTurnedPoint(rightPoint);
	}

	private float getFixedTopTurnedTopPoint(float topPoint) {
		return isTopTurnedPointOverTheTopEdge(topPoint) ? getTopEdgePoint()
				: getTopTurnedPoint(topPoint);
	}

	private float getFixedBottomTurnedBottomPoint(float bottomPoint) {
		return isBottomTurnedPointOverTheBottomEdge(bottomPoint) ? getBottomEdgePoint()
				: getBottomTurnedPoint(bottomPoint);
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