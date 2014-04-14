package gtv.andrew.crossedlines.Activities;

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

	private void initComponents() {
		menuView = (MenuView) findViewById(R.id.menuView);
		menuView.setBackgroundColor(getResources().getColor(R.color.color_background));
		menuView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
			}

		});
		tvNewGame = (TextView) findViewById(R.id.tvNewGame);
		tvSettings = (TextView) findViewById(R.id.tvSettings);
		tvHowToPlay = (TextView) findViewById(R.id.tvHowToPlay);
		tvRateApp = (TextView) findViewById(R.id.tvRateApp);
		RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int) (GameSettings.Instance().width/1.6), 62);
		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) (GameSettings.Instance().width/1.6), 62);
		RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((int) (GameSettings.Instance().width/1.6), 62);
		RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams((int) (GameSettings.Instance().width/1.6), 62);
		layoutParams1.setMargins(0, 320, 0, 0);
		tvNewGame.setLayoutParams(layoutParams1);
		layoutParams2.setMargins(0, 440, 0, 0);
		tvSettings.setLayoutParams(layoutParams2);
		layoutParams3.setMargins(0, 560, 0, 0);
		tvHowToPlay.setLayoutParams(layoutParams3);
		layoutParams4.setMargins((int) (GameSettings.Instance().width - GameSettings.Instance().width/1.6), 680, 0, 0);
		tvRateApp.setLayoutParams(layoutParams4);
		mediaPlayer = MediaPlayer.create(this, R.raw.click);
		mediaPlayerNewGame = MediaPlayer.create(this, R.raw.new_game_click);
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