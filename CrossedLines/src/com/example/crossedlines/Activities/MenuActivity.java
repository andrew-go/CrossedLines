package com.example.crossedlines.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.crossedlines.GameSettings;
import com.example.crossedlines.R;
import com.example.crossedlines.Views.MenuView;

public class MenuActivity extends Activity {

	MenuView menuView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		setGameSettings();
		initComponents();
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
				Intent gameActivityIntent = new Intent(getBaseContext(), GameActivity.class);
				startActivity(gameActivityIntent);
				return false;
			}
			
		});
	}

}
