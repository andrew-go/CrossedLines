package com.example.crossedlines.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.crossedlines.Game;
import com.example.crossedlines.Game.Way;
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
	}

	private void drawRect(int rowIndex, int columnIndex, Canvas canvas) {
		canvas.drawRect((columnIndex * GameSettings.Instance().getRectSize())
				+ GameSettings.Instance().marginRect,
				(rowIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect,
				(columnIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(),
				(rowIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(), paint);
	}

	private void drawHorizontalRect(int rowIndex, int columnIndex, Canvas canvas) {
		keepOnScreen(rowIndex, columnIndex, (columnIndex * GameSettings
				.Instance().getRectSize())
				+ GameSettings.Instance().marginRect
				+ Game.Instance().getXDiffer() * -1,
				(rowIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect,
				(columnIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize()
						+ Game.Instance().getXDiffer() * -1,
				(rowIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(), canvas);
	}

	private void drawVerticalRect(int rowIndex, int columnIndex, Canvas canvas) {
		keepOnScreen(rowIndex, columnIndex,
				(columnIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect,
				(rowIndex * GameSettings.Instance().getRectSize())
						+ GameSettings.Instance().marginRect
						+ Game.Instance().getYDiffer() * -1,
				(columnIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize(),
				(rowIndex * GameSettings.Instance().getRectSize())
						- GameSettings.Instance().marginRect
						+ GameSettings.Instance().getRectSize()
						+ Game.Instance().getYDiffer() * -1, canvas);
	}



	private void keepOnScreen(int rowIndex, int columnIndex, float leftPoint,
			float topPoint, float rightPoint, float bottomPoint, Canvas canvas) {
		if (Game.Instance().isRowMovingHorizontal(rowIndex)) {
			if (rightPoint > GameSettings.Instance().width) {
				canvas.drawRect(leftPoint - GameSettings.Instance().width,
						topPoint, rightPoint - GameSettings.Instance().width,
						bottomPoint, paint);
				if (leftPoint < GameSettings.Instance().width)
					canvas.drawRect(leftPoint, topPoint,
							GameSettings.Instance().width, bottomPoint, paint);
			} else if (leftPoint < GameSettings.Instance().rectHorizontalStartPoint) {
				canvas.drawRect(leftPoint + GameSettings.Instance().width,
						topPoint, rightPoint + GameSettings.Instance().width,
						bottomPoint, paint);
				if (rightPoint > GameSettings.Instance().rectHorizontalStartPoint)
					canvas.drawRect(
							GameSettings.Instance().rectHorizontalStartPoint,
							topPoint, rightPoint, bottomPoint, paint);
			} else {
				canvas.drawRect(leftPoint, topPoint, rightPoint, bottomPoint,
						paint);
			}
//			if (Game.Instance().selecetedRect.columnIndex == columnIndex && Game.Instance().selecetedRect.rowIndex == rowIndex)
//				moveLineInArr(rowIndex, columnIndex, leftPoint, topPoint, rightPoint, bottomPoint);
		} else if (Game.Instance().isColumnMovingVertical(columnIndex)) {
			if (topPoint < GameSettings.Instance().rectVerticalStartPoint) {
				canvas.drawRect(leftPoint, topPoint
						+ GameSettings.Instance().width, rightPoint,
						bottomPoint + GameSettings.Instance().width, paint);
				if (bottomPoint > GameSettings.Instance().rectVerticalStartPoint)
					canvas.drawRect(leftPoint,
							GameSettings.Instance().rectVerticalStartPoint,
							rightPoint, bottomPoint, paint);
			} else if (bottomPoint > GameSettings.Instance().width) {
				canvas.drawRect(leftPoint, topPoint
						- GameSettings.Instance().width, rightPoint,
						bottomPoint - GameSettings.Instance().width, paint);
				if (topPoint < GameSettings.Instance().width)
					canvas.drawRect(leftPoint, topPoint, rightPoint,
							GameSettings.Instance().width, paint);
			} else {
				canvas.drawRect(leftPoint, topPoint, rightPoint, bottomPoint,
						paint);
			}
		}
	}

	private void initComponents() {
		paint = new Paint();
	}

	private int getColor(int colorNumber) {
		switch (colorNumber) {
		case 0:
			return getResources().getColor(R.color.color_brown);
		case 1:
			return getResources().getColor(R.color.color_orange);
		case 2:
			return getResources().getColor(R.color.color_green);
		case 3:
			return getResources().getColor(R.color.color_ivory);
		case 4:
			return getResources().getColor(R.color.color_blue);
		case 5:
			return getResources().getColor(R.color.color_purple);
		}
		return Color.WHITE;
	}

}