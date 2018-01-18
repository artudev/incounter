package pl.vivifiedbits.incounter;

import timber.log.Timber;

/**
 * Created by Artur Kasprzak on 04.07.2017.
 */

public class DebugApplication extends MainApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		Timber.plant(new Timber.DebugTree());
		Timber.d("DebugApplication onCreate");
	}
}
