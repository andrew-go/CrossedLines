package gtv.andrew.crossedlines.Views;

import gtv.andrew.crossedlines.GameSettings;
import gtv.andrew.crossedlines.PixelLetterContainer;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import gtv.andrew.crossedlines.R;

public class MenuView extends View {

	Paint paint;

	int rectSize = 20;
	int bigLetterSize = 10;
	int smallLetterSize = 5;
	int marginRect = 2;

	int bigLetterRect = 0;
	int smallLetterRect = 0;

	int[][] emptyRect;
	
	int[][] menuArr;

	public MenuView(Context context) {
		super(context);
		initArray();
	}

	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initArray();
	}

	public MenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initArray();
	}

	public void initArray() {
		Random random = new Random();
		menuArr = new int[GameSettings.Instance().width / rectSize][GameSettings.Instance().height / rectSize];
		for (int i = 0; i < (GameSettings.Instance().width / rectSize); i++)
			for (int j = 0; j < (GameSettings.Instance().height / rectSize); j++)
				menuArr[i][j] = random.nextInt(GameSettings.Instance().colorsCount + 1) + 1;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint = new Paint();
		initEmptyRect();
		int a = (GameSettings.Instance().width - 450) / (2 * 20);
		for (int i = 0; i < (GameSettings.Instance().width / rectSize); i++)
			for (int j = 0; j < (GameSettings.Instance().height / rectSize); j++) {
				if (j < 13 && i >= a && i < 24 + a && emptyRect[j][i - a] == 0)
					continue;
				paint.setColor(getColor(menuArr[i][j]));
				canvas.drawRect(rectSize * i + marginRect, rectSize * j + marginRect, rectSize * i + rectSize - marginRect, rectSize * j + rectSize - marginRect, paint);
			}
		drawGameName(canvas);
	}

	private void drawGameName(Canvas canvas) {
		PixelLetterContainer pixelLetterContainer = new PixelLetterContainer();
		int rightShift = (GameSettings.Instance().width - 450) / 2;
		for (int[][] letter : pixelLetterContainer.scrollWord) {
			drawBigWord(letter, rightShift, 6, canvas);
			rightShift += (letter[0].length + 1) * bigLetterSize;
		}
		rightShift = (GameSettings.Instance().width - 450) / 2 + 80;
		for (int[][] letter : pixelLetterContainer.lineWord) {
			drawBigWord(letter, rightShift, 14, canvas);
			rightShift += (letter[0].length + 1) * bigLetterSize;
		}
//		rightShift = GameSettings.Instance().width/2 - 135/2;
//		for (int[][] letter : pixelLetterContainer.lineWord) {
//			drawSmallWord(letter, rightShift, 30, canvas);
//			rightShift += (letter[0].length + 1) * smallLetterSize;
//		}

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

	private void drawBigWord(int[][] letter, int rightShift, int downShift, Canvas canvas) {
		for (int i = 0; i < letter.length; i ++)
			for (int j = 0; j < letter[i].length; j ++) {
				if (letter[i][j] == 0)
					continue;
				paint.setColor(Color.WHITE);
				canvas.drawRect(bigLetterSize * j + rightShift + bigLetterRect, bigLetterSize * i + downShift * 10 + bigLetterRect + 5, bigLetterSize * j + bigLetterSize  + rightShift - bigLetterRect, bigLetterSize * i + bigLetterSize + downShift * 10 - bigLetterRect + 5, paint);
			}
	}
	
	private void drawSmallWord(int[][] letter, int rightShift, int downShift, Canvas canvas) {
		for (int i = 0; i < letter.length; i ++)
			for (int j = 0; j < letter[i].length; j ++) {
				if (letter[i][j] == 0)
					continue;
				paint.setColor(getResources().getColor(R.color.color_background));
				canvas.drawRect(smallLetterSize * j + rightShift + smallLetterRect, smallLetterSize * i + downShift * 10 + smallLetterRect + 2, smallLetterSize * j + smallLetterSize  + rightShift - smallLetterRect, smallLetterSize * i + smallLetterSize + downShift * 10 - smallLetterRect + 2, paint);
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