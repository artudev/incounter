package pl.vivifiedbits.incounter.income.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.parent.BaseActivity;
import pl.vivifiedbits.incounter.parent.BaseFragment;
import pl.vivifiedbits.incounter.utils.PreferencesHelper;
import timber.log.Timber;

/**
 * Created by Artur Kasprzak on 28.02.2018.
 */

public class PercentDialog extends DialogFragment {

	@BindView(R.id.acet_percent)
	AppCompatEditText mAcetPercent;

	private Unbinder mUnbinder;
	private SetPercentListener mSetPercentListener;

	private void setSetPercentListener(@NonNull SetPercentListener setPercentListener) {
		mSetPercentListener = setPercentListener;
	}

	public static PercentDialog newInstance(@NonNull SetPercentListener listener) {
		PercentDialog percentDialog = new PercentDialog();
		percentDialog.setSetPercentListener(listener);
		return percentDialog;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Timber.d("onCreateDialog");

		View view =
				LayoutInflater.from(getContext()).inflate(R.layout.dialog_set_percent, null, false);
		mUnbinder = ButterKnife.bind(this, view);

		BaseActivity baseActivity = ((BaseFragment) getParentFragment()).getBaseActivity();

		float percent = PreferencesHelper.getPercent(getContext());
		mAcetPercent.setText(String.format("%.2f", percent));

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setView(view);
		builder.setPositiveButton(R.string.ok, (dialog, which) -> {
			float value = Float.valueOf(mAcetPercent.getText().toString().replaceAll(",", "."));
			mSetPercentListener.onPercentSet(value);
			mAcetPercent.clearFocus();
			baseActivity.hideKeyboard();
		});
		builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
			mAcetPercent.clearFocus();
			baseActivity.hideKeyboard();
		});

		return builder.create();
	}

	@Override
	public void onDestroyView() {
		Timber.d("onDestroyView");
		super.onDestroyView();
		mUnbinder.unbind();
	}
}
