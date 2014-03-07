package com.example.crossedlines.Views;

import android.content.Context;
import android.graphics.Canvas;
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

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				paint.setColor(getColor(Game.Instance().gameArr[i][j]));
				if (isMoovingHorizontal(i)) {
					drawHorizontalRect(i, j, canvas);
				} else if (isMoovingVertical(j)) {
					drawVerticalRect(i, j, canvas);
				} else {
					drawRect(i, j, canvas);
				}
			}
	}

	private void drawRect(int rowIndex, int columnIndex, Canvas canvas) {
		canvas.drawRect((columnIndex * 60) + 2, (rowIndex * 60) + 2,
				(columnIndex * 60) - 2 + 60, (rowIndex * 60) - 2 + 60, paint);
	}

	private void drawHorizontalRect(int rowIndex, int columnIndex, Canvas canvas) {
		keepOnScreen(
				rowIndex,
				columnIndex,
				(columnIndex * 60) + 2 + Game.Instance().getXDiffer() * -1,
				(rowIndex * 60) + 2,
				(columnIndex * 60) - 2 + 60 + Game.Instance().getXDiffer() * -1,
				(rowIndex * 60) - 2 + 60, canvas);
		canvas.drawRect((columnIndex * 60) + 2 + Game.Instance().getXDiffer()
				* -1, (rowIndex * 60) + 2, (columnIndex * 60) - 2 + 60
				+ Game.Instance().getXDiffer() * -1, (rowIndex * 60) - 2 + 60,
				paint);
	}

	private void drawVerticalRect(int rowIndex, int columnIndex, Canvas canvas) {
		keepOnScreen(rowIndex, columnIndex, (columnIndex * 60) + 2,
				(rowIndex * 60) + 2 + Game.Instance().getYDiffer() * -1,
				(columnIndex * 60) - 2 + 60, (rowIndex * 60) - 2 + 60
						+ Game.Instance().getYDiffer() * -1, canvas);
		canvas.drawRect((columnIndex * 60) + 2, (rowIndex * 60) + 2
				+ Game.Instance().getYDiffer() * -1,
				(columnIndex * 60) - 2 + 60, (rowIndex * 60) - 2 + 60
						+ Game.Instance().getYDiffer() * -1, paint);
	}

	private boolean isMoovingHorizontal(int rowIndex) {
		return Game.Instance().way != null
				&& (Game.Instance().way == Way.LEFT || Game.Instance().way == Way.RIGHT)
				&& Game.Instance().selecetedRect.getY() == rowIndex;
	}

	private boolean isMoovingVertical(int columnIndex) {
		return Game.Instance().way != null
				&& (Game.Instance().way == Way.UP || Game.Instance().way == Way.DOWN)
				&& Game.Instance().selecetedRect.getX() == columnIndex;
	}

	private void keepOnScreen(int rowIndex, int columnIndex, float leftPoint,
			float topPoint, float rightPoint, float bottomPoint, Canvas canvas) {
		if (isMoovingHorizontal(rowIndex)) {
			if (rightPoint > GameSettings.Instance().width)
				canvas.drawRect(leftPoint - GameSettings.Instance().width,
						topPoint, rightPoint - GameSettings.Instance().width,
						bottomPoint, paint);
			else if (leftPoint < 0)
				canvas.drawRect(leftPoint + GameSettings.Instance().width,
						topPoint, rightPoint + GameSettings.Instance().width,
						bottomPoint, paint);
		} else if (isMoovingVertical(columnIndex)) {
			if (topPoint < 0)
				canvas.drawRect(leftPoint, topPoint
						+ GameSettings.Instance().width, rightPoint,
						bottomPoint + GameSettings.Instance().width, paint);
			else if (bottomPoint > GameSettings.Instance().width)
				canvas.drawRect(leftPoint, topPoint
						- GameSettings.Instance().width, rightPoint,
						bottomPoint - GameSettings.Instance().width, paint);
		}
	}

	private void initComponents() {
		paint = new Paint();
	}

	private int getColor(int colorNumber) {
		switch (colorNumber) {
		case 0:
			return getResources().getColor(R.color.color_0);
		case 1:
			return getResources().getColor(R.color.color_1);
		case 2:
			return getResources().getColor(R.color.color_2);
		case 3:
			return getResources().getColor(R.color.color_3);
		case 4:
			return getResources().getColor(R.color.color_4);
		case 5:
			return getResources().getColor(R.color.color_5);
		}
		return -1;
		// return Color.RED;
	}

}