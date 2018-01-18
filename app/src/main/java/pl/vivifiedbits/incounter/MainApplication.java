package pl.vivifiedbits.incounter;

import android.app.Application;

/**
 * Created by Artur Kasprzak on 04.07.2017.
 */

public class MainApplication extends Application {

	private IncomeComponent mIncomeComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		mIncomeComponent = DaggerIncomeComponent.builder().incomeModule(new IncomeModule()).build();
	}

	public IncomeComponent getIncomeComponent() {
		return mIncomeComponent;
	}
}
