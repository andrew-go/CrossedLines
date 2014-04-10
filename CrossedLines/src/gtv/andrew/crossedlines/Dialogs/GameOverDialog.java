package gtv.andrew.crossedlines.Dialogs;

import gtv.andrew.crossedlines.Game;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import gtv.andrew.crossedlines.R;

public class GameOverDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_game_over, null);
		builder.setView(view)
				.setTitle("Game Over");
		TextView tvScore = (TextView) view.findViewById(R.id.tvScore);
		tvScore.setText(Html.fromHtml(getActivity().getString(R.string.time_is_up) + " " + getActivity().getString(R.string.your_score_is) + " " +  "<b>" + Game.Instance().score + "</b>" + " / " + "<b>" + Game.Instance().highScore + "</b>" + "." ));
		return builder.create();
	}

}