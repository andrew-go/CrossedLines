package com.example.crossedlines.Views;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.crossedlines.GameSettings;
import com.example.crossedlines.PixelLetterContainer;
import com.example.crossedlines.R;

public class MenuView extends View {

	Paint paint;
	
	int rectSize = 20;
	int letterSize = 10;
	int marginRect = 2;
	
	int letterRect = 0;
	
	int[][] emptyRect;
	
	public MenuView(Context context) {
		super(context);
	}

	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Random random = new Random();
		super.onDraw(canvas);
		paint = new Paint();
		initEmptyRect();
		int a = (GameSettings.Instance().width - 450) / (2 * 20);
		for (int i = 0; i < (GameSettings.Instance().width / rectSize); i++)
			for (int j = 0; j < (GameSettings.Instance().height / rectSize); j++) {
				if (j < 13 && i >= a && i < 24 + a && emptyRect[j][i - a] == 0)
					continue;
				paint.setColor(getColor(random.nextInt(GameSettings.Instance().colorsCount + 1) + 1));
				canvas.drawRect(rectSize * i + marginRect, rectSize * j + marginRect, rectSize * i + rectSize - marginRect, rectSize * j + rectSize - marginRect, paint);
			}
		drawGameName(canvas);
	}
	
	private void drawGameName(Canvas canvas) {
		PixelLetterContainer pixelLetterContainer = new PixelLetterContainer();
		int rightShift = (GameSettings.Instance().width - 450) / 2;
		for (int[][] letter : pixelLetterContainer.scrollWord) {
			drawWord(letter, rightShift, 6, canvas);
			rightShift += (letter[0].length + 1) * 10;
		}
		rightShift = (GameSettings.Instance().width - 450) / 2 + 80;
		for (int[][] letter : pixelLetterContainer.lineWord) {
			drawWord(letter, rightShift, 14, canvas);
			rightShift += (letter[0].length + 1) * 10;
		}

	}
	
	private void initEmptyRect() {
		int[][] emptyRect = {{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
							{2,2,2,2,2,2,2,0,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2},
							{2,0,2,0,2,0,2,0,0,0,0,0,2,2,2,0,2,0,2,2,2,2,2,2},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,2,2},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{2,2,2,0,2,2,0,0,0,0,2,2,0,0,0,2,2,2,0,2,2,0,0,2}};
		this.emptyRect = emptyRect;
	}
	
	private void drawWord(int[][] letter, int rightShift, int downShift, Canvas canvas) {
		for (int i = 0; i < letter.length; i ++)
			for (int j = 0; j < letter[i].length; j ++) {
				if (letter[i][j] == 0)
					continue;
				paint.setColor(Color.WHITE);
				canvas.drawRect(letterSize * j + rightShift + letterRect, letterSize * i + downShift * 10 + letterRect + 5, letterSize * j + letterSize  + rightShift - letterRect, letterSize * i + letterSize + downShift * 10 - letterRect + 5, paint);
			}
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
