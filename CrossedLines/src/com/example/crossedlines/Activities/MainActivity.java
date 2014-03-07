package com.example.crossedlines.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.crossedlines.Game;
import com.example.crossedlines.GameSettings;
import com.example.crossedlines.R;
import com.example.crossedlines.Views.GameView;

public class MainActivity extends Activity {

	float touchActionDownX;
	float touchActionDownY;

	boolean touchActionMoveStatus;
	float touchActionMoveX;
	float touchActionMoveY;
	Toast toast;
	GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setGameSettings();
		gameView = new GameView(getBaseContext());
		setContentView(gameView);
		toast = new Toast(getBaseContext());
		gameView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!isOnGameView(event))
					return false;
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					Log.i("test", "Down");

					touchActionDownX = (int) event.getX();
					touchActionDownY = (int) event.getY();
					Game.Instance().startX = (int) event.getX();
					Game.Instance().startY = (int) event.getY();
					touchActionMoveStatus = true;
					Game.Instance().clearPreviousInfo();
					Game.Instance().selectStartRect(event.getX(), event.getY());
					break;

				case MotionEvent.ACTION_UP:

					touchActionMoveStatus = false;
					Game.Instance().clearPreviousInfo();
					gameView.postInvalidate();
					break;

				case MotionEvent.ACTION_MOVE:

					Game.Instance().currentX = (int) event.getX();
					Game.Instance().currentY = (int) event.getY();

					if (touchActionMoveStatus) {
						touchActionMoveX = (int) event.getX();
						touchActionMoveY = (int) event.getY();
						Game.Instance().setWay(touchActionDownX,
								touchActionDownY, touchActionMoveX,
								touchActionMoveY);
					}
					gameView.postInvalidate();
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

	private void setGameSettings() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		GameSettings.Instance().height = displaymetrics.heightPixels;
		GameSettings.Instance().width = displaymetrics.widthPixels;
	}

	private boolean isOnGameView(MotionEvent event) {
		return event.getY() <= GameSettings.Instance().width;
	}

}