package pl.vivifiedbits.incounter.income;

import android.os.Bundle;

import pl.vivifiedbits.incounter.parent.BaseActivity;
import timber.log.Timber;

public class IncomeActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Timber.d("onCreate");
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			Timber.d("savedInstanceState is NULL");
			IncomeFragment incomeFragment = new IncomeFragment();
			setContent(incomeFragment);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Timber.d("onSaveInstanceState");
	}
}
