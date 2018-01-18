package pl.vivifiedbits.incounter.income;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.vivifiedbits.incounter.MainApplication;
import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.income.model.IncomeContainer;
import pl.vivifiedbits.incounter.parent.BaseActivity;
import pl.vivifiedbits.incounter.utils.PreferencesHelper;
import timber.log.Timber;

public class IncomeActivity extends BaseActivity {

	@Inject
	IncomeContainer mIncomeContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Timber.d("onCreate");
		super.onCreate(savedInstanceState);
		((MainApplication) getApplication()).getIncomeComponent().inject(this);

		IncomeFragment incomeFragment = new IncomeFragment();
		setContent(incomeFragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_set_percent:
				showPercentDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void showPercentDialog() {
		PercentDialog percentDialog = new PercentDialog();
		percentDialog.getPercentDialog().show();
	}

	class PercentDialog {
		@BindView(R.id.acet_percent)
		AppCompatEditText mAcetPercent;

		public AlertDialog getPercentDialog() {
			Timber.d("onCreateDialog");

			View view = LayoutInflater.from(IncomeActivity.this)
					.inflate(R.layout.dialog_set_percent, null, false);
			ButterKnife.bind(this, view);

			float percent = PreferencesHelper.getPercent(IncomeActivity.this);
			mAcetPercent.setText(String.format("%.2f", percent));

			AlertDialog.Builder builder = new AlertDialog.Builder(IncomeActivity.this);
			builder.setView(view);
			builder.setPositiveButton(R.string.ok, (dialog, which) -> {

				float value = Float.valueOf(mAcetPercent.getText().toString().replaceAll(",", "."));
				PreferencesHelper.setPercent(IncomeActivity.this, value);
				mIncomeContainer.publishUpdate();
				hideKeyboard();
			});
			builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
				dialog.dismiss();
				hideKeyboard();
			});

			return builder.create();
		}
	}
}
