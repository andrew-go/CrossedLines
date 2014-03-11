package com.example.crossedlines.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.crossedlines.Game;
import com.example.crossedlines.Game.GameThread;
import com.example.crossedlines.GameSettings;
import com.example.crossedlines.R;
import com.example.crossedlines.Views.GameView;

public class MainActivity extends Activity {

	float touchActionDownX;
	float touchActionDownY;

	float touchActionMoveX;
	float touchActionMoveY;
	Toast toast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setGameSettings();
		Game.Instance().gameView = new GameView(getBaseContext());
		setContentView(Game.Instance().gameView);
		Game.Instance().checkCombinedLines();
		toast = new Toast(getBaseContext());
		Game.Instance().gameThread = new GameThread(Game.Instance().gameView);
		Game.Instance().gameThread.start();
		Game.Instance().gameView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					if (Game.Instance().isGameOver) {
						Game.Instance().startNewGame();
						return false;
					}
					if (!isOnGameView(event))
						return false;
					Log.i("test", "Down");

					touchActionDownX = (int) event.getX()
							- GameSettings.Instance().rectHorizontalStartPoint;
					touchActionDownY = (int) event.getY()
							- GameSettings.Instance().rectVerticalStartPoint;
					Game.Instance().startX = (int) event.getX()
							- GameSettings.Instance().rectHorizontalStartPoint;
					Game.Instance().startY = (int) event.getY()
							- GameSettings.Instance().rectVerticalStartPoint;
					Game.Instance().touchActionMoveStatus = true;
					Game.Instance().clearPreviousInfo();
					Game.Instance()
							.selectStartRect(
									event.getX()
											- GameSettings.Instance().rectHorizontalStartPoint,
									event.getY()
											- GameSettings.Instance().rectVerticalStartPoint);
					break;

				case MotionEvent.ACTION_UP:

					Game.Instance().touchActionMoveStatus = true;
					Game.Instance().pickUpArr(event);
					Game.Instance().clearPreviousInfo();
					while (Game.Instance().checkCombinedLines());
					Game.Instance().gameView.postInvalidate();
					break;

				case MotionEvent.ACTION_MOVE:
					if (!isOnGameView(event))
						return false;

					Game.Instance().currentX = (int) event.getX()
							- GameSettings.Instance().rectHorizontalStartPoint;
					Game.Instance().currentY = (int) event.getY()
							- GameSettings.Instance().rectVerticalStartPoint;

					if (Game.Instance().touchActionMoveStatus) {
						touchActionMoveX = (int) event.getX()
								- GameSettings.Instance().rectHorizontalStartPoint;
						touchActionMoveY = (int) event.getY()
								- GameSettings.Instance().rectVerticalStartPoint;
						Game.Instance().setWay(touchActionDownX,
								touchActionDownY, touchActionMoveX,
								touchActionMoveY);
					}
					Game.Instance().gameView.postInvalidate();
					break;
				}

				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Game.Instance().startNewGame();
		return super.onMenuItemSelected(featureId, item);
	}

	private void setGameSettings() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		// GameSettings.Instance().height = displaymetrics.heightPixels;
		// GameSettings.Instance().width = displaymetrics.widthPixels;
	}

	private boolean isOnGameView(MotionEvent event) {
		return event.getX() > GameSettings.Instance().rectHorizontalStartPoint && event.getX() < GameSettings.Instance().rectHorizontalStartPoint + GameSettings.Instance().width 
				&& event.getY() > GameSettings.Instance().rectVerticalStartPoint && event.getY() < GameSettings.Instance().rectVerticalStartPoint + GameSettings.Instance().width;
	}

}