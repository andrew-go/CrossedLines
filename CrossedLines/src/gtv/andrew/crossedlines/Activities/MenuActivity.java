package gtv.andrew.crossedlines.Activities;

import gtv.andrew.crossedlines.Game;
import gtv.andrew.crossedlines.GameSettings;
import gtv.andrew.crossedlines.Views.MenuView;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gtv.andrew.crossedlines.R;

public class MenuActivity extends Activity {

	MenuView menuView;
	TextView tvNewGame;
	TextView tvSettings;
	TextView tvHowToPlay;
	TextView tvRateApp;
	MediaPlayer mediaPlayer;
	MediaPlayer mediaPlayerNewGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setGameSettings();
		setContentView(R.layout.activity_menu);
		initComponents();
		applyResizing();
	}
	
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	private void setGameSettings() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		GameSettings.Instance().height = displaymetrics.heightPixels;
		GameSettings.Instance().width = displaymetrics.widthPixels;
	}
	
	private void applyResizing() {
		if (GameSettings.Instance().height <= 800) {
			menuView.rectSize = 10;
			menuView.bigLetterSize = 5;
			menuView.marginRect = 1;
		}
		else {
			menuView.rectSize = 20;
			menuView.bigLetterSize = 10;
			menuView.marginRect = 2;
		}
		menuView.initArray();
		tvNewGame = (TextView) findViewById(R.id.tvNewGame);
		tvSettings = (TextView) findViewById(R.id.tvSettings);
		tvHowToPlay = (TextView) findViewById(R.id.tvHowToPlay);
		tvRateApp = (TextView) findViewById(R.id.tvRateApp);
		int a = (GameSettings.Instance().width/menuView.rectSize) / 2;
		int b = ((a % 2) == 0 ? (a/2) : ((a + 1) / 2));
		int buttonLenght = a + b;
		int rectsOnButtonArea = (GameSettings.Instance().height / menuView.rectSize - 12) / 4;
		int rectsOnButton = ((int)rectsOnButtonArea / 3);
		int rectsOnEmptyArea = ((int) rectsOnButtonArea / 3);
		if (rectsOnButtonArea % 3 == 1)
			rectsOnButton++;
		else if (rectsOnButtonArea % 3 == 2)
			rectsOnEmptyArea++;
		RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(buttonLenght * menuView.rectSize, menuView.rectSize * rectsOnButton);
		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(buttonLenght * menuView.rectSize, menuView.rectSize * rectsOnButton);
		RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(buttonLenght * menuView.rectSize, menuView.rectSize * rectsOnButton);
		RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(buttonLenght * menuView.rectSize, menuView.rectSize * rectsOnButton);
		int f = rectsOnEmptyArea * menuView.rectSize + 12 * menuView.rectSize;
		layoutParams1.setMargins(0, f, 0, 0);
		tvNewGame.setLayoutParams(layoutParams1);
		f += menuView.rectSize * rectsOnButton + (menuView.rectSize * rectsOnEmptyArea) * 2;
		layoutParams2.setMargins(0, f, 0, 0);
		tvSettings.setLayoutParams(layoutParams2);
		f += menuView.rectSize * rectsOnButton + (menuView.rectSize * rectsOnEmptyArea) * 2;
		layoutParams3.setMargins(0, f, 0, 0);
		tvHowToPlay.setLayoutParams(layoutParams3);
		f += menuView.rectSize * rectsOnButton + (menuView.rectSize * rectsOnEmptyArea) * 2;
		layoutParams4.setMargins((int) GameSettings.Instance().width - (buttonLenght * menuView.rectSize), f, 0, 0);
		tvRateApp.setLayoutParams(layoutParams4);
		mediaPlayer = MediaPlayer.create(this, R.raw.click4);
		mediaPlayerNewGame = MediaPlayer.create(this, R.raw.new_game_click);
	}

	private void initComponents() {
		menuView = (MenuView) findViewById(R.id.menuView);
		menuView.setBackgroundColor(getResources().getColor(R.color.color_background));
		menuView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
			}

		});

	}
	
	public void onNewGameClick(View view) {
		mediaPlayerNewGame.start();
		Intent gameActivityIntent = new Intent(getBaseContext(), GameActivity.class);
		startActivity(gameActivityIntent);

	}
	
	public void onSettingsClick(View view) {
		
	}
	
	public void onHowToPlayClick(View view) {
		
	}
	
	
	public void onRateAppClick(View view) {
		mediaPlayer.start();
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "gtv.andrew.crossedlines")));
	}	


}