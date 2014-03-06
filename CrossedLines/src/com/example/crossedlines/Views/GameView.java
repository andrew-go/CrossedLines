package com.example.crossedlines.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.crossedlines.Game;
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
				canvas.drawRect((i * 60) + 2, (j * 60) + 2, (i * 60) - 2 + 60, (j * 60) - 2 + 60, paint);				
			}
		//canvas.drawRect(0, 0, 60, 60, paint);	
				
	}
	
	private void initComponents() {
		paint = new Paint();
		paint.setColor(Color.RED);
	}
	
	private int getColor(int colorNumber) {
		switch(colorNumber) {
			case 0 : return getResources().getColor(R.color.color_0);
			case 1 : return getResources().getColor(R.color.color_1);
			case 2 : return getResources().getColor(R.color.color_2);
			case 3 : return getResources().getColor(R.color.color_3);
			case 4 : return getResources().getColor(R.color.color_4);
			case 5 : return getResources().getColor(R.color.color_5);
		}
		return -1;
	}
	

}
