package pl.vivifiedbits.incounter.income;

import android.os.Bundle;

import pl.vivifiedbits.incounter.parent.BaseActivity;
import timber.log.Timber;

public class IncomeActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Timber.d("onCreate");
		super.onCreate(savedInstanceState);
		
		IncomeFragment incomeFragment = new IncomeFragment();
		setContent(incomeFragment);
	}
}
