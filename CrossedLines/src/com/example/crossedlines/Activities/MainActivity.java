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
import com.example.crossedlines.Game.Way;
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
		//setContentView(R.layout.activity_main);
		setGameSettings();
		gameView = new GameView(getBaseContext());
		setContentView(gameView);
		toast = new Toast(getBaseContext());
		gameView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {    

			    switch (event.getAction()) {

			    case MotionEvent.ACTION_DOWN:
			        Log.i("test","Down");

			        touchActionDownX = (int)event.getX();
			        touchActionDownY = (int)event.getY();
			        touchActionMoveStatus = true;
			        Game.Instance().clearPreviousInfo();
			        Game.Instance().selectStartRect(event.getX(), event.getY());
			        break;

			    case MotionEvent.ACTION_POINTER_UP:

			        touchActionMoveStatus = false;
			        Game.Instance().clearPreviousInfo();
			        break;

			    case MotionEvent.ACTION_MOVE:
			    	
			        if(touchActionMoveStatus) {
			        	touchActionMoveX = (int)event.getX();
			        	touchActionMoveY = (int)event.getY();
			        	Game.Instance().setWay(touchActionDownX, touchActionDownY, touchActionMoveX, touchActionMoveY);
			        	if (Game.Instance().way == null)
			        		showWay();
			        }
			        break;
			    }

			    return true;
			}
		});
	}
	

	
	private void showWay() {
    	toast = Toast.makeText(getBaseContext(), Game.Instance().way == Way.UP 
    			? "Move Up" : Game.Instance().way == Way.DOWN 
    				? "Move Down" : Game.Instance().way == Way.LEFT 
    					? "Move Left" : "Move Right", Toast.LENGTH_SHORT);
    	toast.show();
        touchActionMoveStatus = false;
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

}
