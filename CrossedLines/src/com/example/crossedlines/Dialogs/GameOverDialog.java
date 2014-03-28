package com.example.crossedlines.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.crossedlines.R;

public class GameOverDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_game_over, null);
		builder.setView(view)
				.setTitle("Game Over");
		int score = getArguments().getInt("score");
		TextView tvScore = (TextView) view.findViewById(R.id.tvScore);
		tvScore.setText(String.format("%s %s %d.", getActivity().getString(R.string.time_is_up), getActivity().getString(R.string.your_score_is), score));
		return builder.create();
	}

}
