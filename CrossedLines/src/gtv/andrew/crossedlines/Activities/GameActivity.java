package gtv.andrew.crossedlines.Activities;

import gtv.andrew.crossedlines.Game;
import gtv.andrew.crossedlines.Game.DrawThread;
import gtv.andrew.crossedlines.Game.GameThread;
import gtv.andrew.crossedlines.GameSettings;
import gtv.andrew.crossedlines.IGameOverHandler;
import gtv.andrew.crossedlines.R;
import gtv.andrew.crossedlines.Dialogs.GameOverDialog;
import gtv.andrew.crossedlines.Dialogs.MainMenuDialog;
import gtv.andrew.crossedlines.Dialogs.NewGameDialog;
import gtv.andrew.crossedlines.Views.GameView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class GameActivity extends FragmentActivity {

	GameOverDialog gameOverDialog;
	NewGameDialog newGameDialog;
	MainMenuDialog mainMenuDialog;
	MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initComponents();
		if (Game.Instance().checkCombinedLines()) {
			Game.Instance().gameThread.isPaused = true;
			Game.Instance().drawThread = new DrawThread(Game.Instance().gameView);
			Game.Instance().drawThread.start();
		}
		Game.Instance().sharedPreferences = getPreferences(MODE_PRIVATE);
		Game.Instance().load();
	}

	@Override
	public void onBackPressed() {
		Game.Instance().gameThread.isPaused = true;
		mainMenuDialog.show(getSupportFragmentManager(), "gameOverDialog");
	}

	@Override
	protected void onResume() {
		if (Game.Instance().gameView != null)
			Game.Instance().gameView.invalidate();
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Game.Instance().gameThread.isPaused = true;
	}

	private void initComponents() {
		initGameView();
		initThreads();
		initDialogs();
		mediaPlayer = MediaPlayer.create(this, R.raw.click2);
		Game.Instance().mediaPlayerMenu = MediaPlayer.create(this, R.raw.menu2);
//		Game.Instance().mediaPlayerDisappear = MediaPlayer.create(this, R.raw.disappear);
	}

	private void initGameView() {
		Game.Instance().gameView = (GameView) findViewById(R.id.gameView);
		Game.Instance().gameView.setBackgroundColor(getResources().getColor(R.color.color_background));
		Game.Instance().gameView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (Game.Instance().gameThread != null && Game.Instance().gameThread .isPaused && Game.Instance().isOnGameView(event))
					return false;
				if (Game.Instance().drawThread != null && Game.Instance().drawThread.isAlive())
					return false;
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					return actionDown(event);
				case MotionEvent.ACTION_UP:
					return actionUp(event.getX(), event.getY());
				case MotionEvent.ACTION_MOVE:
					if (!Game.Instance().isOnGameView(event)) {
						if (GameSettings.Instance().width + GameSettings.Instance().rectVerticalStartPoint < event.getY())
							actionUp(event.getX(), GameSettings.Instance().width + GameSettings.Instance().rectVerticalStartPoint - 5);
						if (GameSettings.Instance().rectVerticalStartPoint > event.getY())
							actionUp(event.getX(), GameSettings.Instance().rectVerticalStartPoint + 5);
						return false;
					}
					return actionMove(event);
				}
				return true;
			}
		});
		Game.Instance().gameOverHandler = new IGameOverHandler() {

			@Override
			public void onGameOver() {
				gameOverDialog.show(getSupportFragmentManager(), "gameOverDialog");
				if (Game.Instance().score > Game.Instance().highScore) {
					Game.Instance().save(Game.highScoreField, Game.Instance().score);
				}
			}
		};
	}

	private boolean actionDown(MotionEvent event) {
		if (isOnPauseClick(event)) {
			mediaPlayer.start();
			Game.Instance().gameThread.isPaused = !Game.Instance().gameThread.isPaused;
			return false;
		}
		if (isOnRestartClick(event)) {
//			mediaPlayer.start();
			Game.Instance().gameThread.isPaused = true;
			newGameDialog.show(getSupportFragmentManager(), "newGameDialog");
			return false;
		}
		if (!Game.Instance().isOnGameView(event))
			return false;
		Game.Instance().startX = event.getX() - GameSettings.Instance().rectHorizontalStartPoint;
		Game.Instance().startY = event.getY() - GameSettings.Instance().rectVerticalStartPoint;
		Game.Instance().touchActionMoveStatus = true;
		Game.Instance().clearPreviousInfo();
		Game.Instance().selectStartRect(Game.Instance().startX, Game.Instance().startY);
		return true;
	}
	
	private boolean actionUp(float x, float y) {
		if (Game.Instance().selecetedRect == null)
			return false;
		Game.Instance().touchActionMoveStatus = true;
		Game.Instance().pickUpArr(x, y);
		Game.Instance().clearPreviousInfo();
		if (Game.Instance().checkCombinedLines()) {
			Game.Instance().gameThread.isPaused = true;
			Game.Instance().drawThread = new DrawThread(Game.Instance().gameView);
			Game.Instance().drawThread.start();
		}
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

	private boolean isOnPauseClick(MotionEvent event) {
		return (event.getX() > 0 && event.getX() < GameSettings.Instance().getRectSize() && event.getY() > (GameSettings.Instance().rectVerticalStartPoint + GameSettings.Instance().width) && event.getY() < (GameSettings.Instance().rectVerticalStartPoint + GameSettings.Instance().width + GameSettings.Instance().getRectSize()));
	}
	
	private boolean isOnRestartClick(MotionEvent event) {
		return (event.getX() > (GameSettings.Instance().width - GameSettings.Instance().getRectSize()) && event.getX() < GameSettings.Instance().width && event.getY() > (GameSettings.Instance().rectVerticalStartPoint + GameSettings.Instance().width) && event.getY() < (GameSettings.Instance().rectVerticalStartPoint + GameSettings.Instance().width + GameSettings.Instance().getRectSize()));
	}
	
	private void initThreads() {
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
		newGameDialog = new NewGameDialog();
		newGameDialog.setCancelable(false);
		mainMenuDialog = new MainMenuDialog();
		mainMenuDialog.setCancelable(false);
	}

	public void onYesClick(View view) {
		mediaPlayer.start();
		Game.Instance().gameThread.isDead = true;
		if (newGameDialog.getActivity() != null) {
			Game.Instance().startNewGame();
			newGameDialog.dismiss();
		}
		if (mainMenuDialog.getActivity() != null) {
			mainMenuDialog.dismiss();
			Intent menuActivityIntent = new Intent(getBaseContext(), MenuActivity.class);
			startActivity(menuActivityIntent);
		}
	}
	
	public void onNoClick(View view) {
		mediaPlayer.start();
		Game.Instance().gameThread.isPaused = false;
		if (newGameDialog.getActivity() != null)
			newGameDialog.dismiss();
		if (mainMenuDialog.getActivity() != null)
			mainMenuDialog.dismiss();
	}
	
	public void onNewGameClick(View view) {
		mediaPlayer.start();
		Game.Instance().highScore = Game.Instance().score;
		Game.Instance().score = 0;	
		Game.Instance().gameThread.isDead = true;
		Game.Instance().startNewGame();
		gameOverDialog.dismiss();
	}

	public void onMainMenuClick(View view) {
		mediaPlayer.start();
		Game.Instance().highScore = Game.Instance().score;
		Game.Instance().score = 0;	
		Intent menuActivityIntent = new Intent(getBaseContext(), MenuActivity.class);
		startActivity(menuActivityIntent);
		gameOverDialog.dismiss();
	}

}