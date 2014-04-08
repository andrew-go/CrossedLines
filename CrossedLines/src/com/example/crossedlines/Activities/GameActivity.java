package com.example.crossedlines.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.crossedlines.Game;
import com.example.crossedlines.Game.GameThread;
import com.example.crossedlines.GameSettings;
import com.example.crossedlines.IGameOverHandler;
import com.example.crossedlines.R;
import com.example.crossedlines.Dialogs.GameOverDialog;
import com.example.crossedlines.Views.GameView;

public class GameActivity extends FragmentActivity {

	GameOverDialog gameOverDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		setGameSettings();
		initComponents();
		checkCombinedLines();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Game.Instance().gameThread.interrupt();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Game.Instance().isGameOver = true;
	}

	private void setGameSettings() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		GameSettings.Instance().height = displaymetrics.heightPixels;
		GameSettings.Instance().width = displaymetrics.widthPixels;
	}
	
	private void initComponents() {
		initGameView();
		initGameThread();
		initDialogs();
	}
	
	private void initGameView() {
		Game.Instance().gameView = (GameView) findViewById(R.id.gameView);
		Game.Instance().gameView.setBackgroundColor(getResources().getColor(R.color.color_background));
		Game.Instance().gameView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					return actionDown(event);
				case MotionEvent.ACTION_UP:
					return actionUp(event);
				case MotionEvent.ACTION_MOVE:
					if (!Game.Instance().isOnGameView(event))
						return false;
					return actionMove(event);
				}
				return true;
			}
		});
		Game.Instance().gameOverHandler = new IGameOverHandler() {
			
			@Override
			public void onGameOver() {
			    Bundle args = new Bundle();
			    args.putInt("score", Game.Instance().score);
			    gameOverDialog.setArguments(args);
				gameOverDialog.show(getSupportFragmentManager(), "gameOverDialog");
			}
		};
	}
	
	private boolean actionDown(MotionEvent event) {
		if (!Game.Instance().isOnGameView(event))
			return false;
		Game.Instance().startX = event.getX() - GameSettings.Instance().rectHorizontalStartPoint;
		Game.Instance().startY = event.getY() - GameSettings.Instance().rectVerticalStartPoint;
		Game.Instance().touchActionMoveStatus = true;
		Game.Instance().clearPreviousInfo();
		Game.Instance().selectStartRect(Game.Instance().startX, Game.Instance().startY);
		return true;
	}
	
	private boolean actionUp(MotionEvent event) {
		Game.Instance().touchActionMoveStatus = true;
		Game.Instance().pickUpArr(event);
		Game.Instance().clearPreviousInfo();
		while (Game.Instance().checkCombinedLines());
			Game.Instance().gameView.postInvalidate();
		return true;
	}
	
	private boolean actionMove(MotionEvent event) {
		Game.Instance().currentX = event.getX() - GameSettings.Instance().rectHorizontalStartPoint;
		Game.Instance().currentY = event.getY() - GameSettings.Instance().rectVerticalStartPoint;
		if (Game.Instance().touchActionMoveStatus)
			Game.Instance().setWay();
		Game.Instance().gameView.postInvalidate();
		return true;
	}
	
	private void initGameThread() {
		if (Game.Instance().gameThread != null) {
			Game.Instance().startNewGame();
			return;
		}
		Game.Instance().gameThread = new GameThread(Game.Instance().gameView, Game.Instance().gameOverHandler);
		Game.Instance().gameThread.start();
	}

	private void initDialogs() {
		gameOverDialog = new GameOverDialog();		
		gameOverDialog.setCancelable(false);
	}
	
	private void checkCombinedLines() {
		Game.Instance().checkCombinedLines();
	}
	
	public void startNewGame(View view) {
		Game.Instance().startNewGame();
		gameOverDialog.dismiss();
	}
	
	public void startMainMenu(View view) {
		Intent menuActivityIntent = new Intent(getBaseContext(), MenuActivity.class);
		startActivity(menuActivityIntent);
		gameOverDialog.dismiss();
	}

}