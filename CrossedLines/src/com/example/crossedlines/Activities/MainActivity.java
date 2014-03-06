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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setGameSettings();
		GameView gameView = new GameView(getBaseContext());
		setContentView(gameView);
		toast = new Toast(getBaseContext());
		gameView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					selectStartRec(event.getX(), event.getY());
//					startX = event.getX();;
//					startY = event.getY();
//					x = event.getX();
//					y = event.getY();
//					count = 0;
//				}
//				if (event.getAction() == MotionEvent.ACTION_MOVE) {			
//					System.out.println(x > event.getX() ? "Left" : "Right");
//					System.out.println(y > event.getY() ? "Up" : "Down");
//					x = event.getX();
//					y = event.getY();
//				}
//				return true;
//			}
				 //You may have to play with the value and make it density dependant.
			    int threshold = 10;

			    switch (event.getAction()) {

			    case MotionEvent.ACTION_DOWN:
			        Log.i("test","Down");

			        touchActionDownX = (int)event.getX();
			        touchActionDownY = (int)event.getY();
			        touchActionMoveStatus = true;

//			        gameLoop.touchX = (int)event.getX();
//			        gameLoop.touchY = (int)event.getY();
//			        gameLoop.touchActionDown = true;
			        break;

			    case MotionEvent.ACTION_POINTER_UP:

			        touchActionMoveStatus = false;

			        break;

			    case MotionEvent.ACTION_MOVE:
			        //Log.i("test","Move");
//			        gameLoop.touchActionMove = true;

			        if(touchActionMoveStatus) {

			        touchActionMoveX = (int)event.getX();
			        touchActionMoveY = (int)event.getY();

			        if(touchActionMoveX < (touchActionDownX - threshold) && (touchActionMoveY > (touchActionDownY - threshold)) && (touchActionMoveY < (touchActionDownY + threshold))){
			        	toast = Toast.makeText(getBaseContext(), "Move Left", Toast.LENGTH_SHORT);
			        	toast.show();//If the move left was greater than the threshold and not greater than the threshold up or down
			            touchActionMoveStatus = false;
			        }
			        else if(touchActionMoveX > (touchActionDownX + threshold) && (touchActionMoveY > (touchActionDownY - threshold)) && (touchActionMoveY < (touchActionDownY + threshold))){
			        	toast = Toast.makeText(getBaseContext(), "Move Right", Toast.LENGTH_SHORT);
			        	toast.show();//If the move right was greater than the threshold and not greater than the threshold up or 
			            touchActionMoveStatus = false;
			       }
			        else if(touchActionMoveY < (touchActionDownY - threshold) && (touchActionMoveX > (touchActionDownX - threshold)) && (touchActionMoveX < (touchActionDownX + threshold))){
			        	toast = Toast.makeText(getBaseContext(), "Move Up", Toast.LENGTH_SHORT);
			        	toast.show();//If the move up was greater than the threshold and not greater than the threshold left or right
			            touchActionMoveStatus = false;
			        }
			        else if(touchActionMoveY > (touchActionDownY + threshold) && (touchActionMoveX > (touchActionDownX - threshold)) && (touchActionMoveX < (touchActionDownX + threshold))){
			        	toast = Toast.makeText(getBaseContext(), "Move Down", Toast.LENGTH_SHORT);
			        	toast.show();//If the move down was greater than the threshold and not greater than the threshold left or right
			            touchActionMoveStatus = false;
			        }
			        }

			        break;
			    }

			    // return false;
			    return true; // This gets the coordinates all the time
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
	
	private void selectStartRec(float x, float y) {
		int i = (int) (x/(GameSettings.Instance().width/8));
		int j = (int) (y/(GameSettings.Instance().width/8));
	}

}
