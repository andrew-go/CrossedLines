package com.example.crossedlines.Views;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.crossedlines.GameSettings;
import com.example.crossedlines.R;

public class MenuView extends View {

	Paint paint;
	
	int rectSize = 20;
	int marginRect = 2;
	
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
		for (int i = 0; i < (GameSettings.Instance().width / rectSize); i++)
			for (int j = 0; j < (GameSettings.Instance().height / rectSize); j++) {
				paint.setColor(getColor(random.nextInt(GameSettings.Instance().colorsCount + 1) + 1));
				canvas.drawRect(rectSize * i + marginRect, rectSize * j + marginRect, rectSize * i + rectSize - marginRect, rectSize * j + rectSize - marginRect, paint);
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
