package pl.vivifiedbits.incounter;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import pl.vivifiedbits.incounter.income.IncomeActivity;
import pl.vivifiedbits.incounter.income.IncomePresenter;

/**
 * Created by Artur Kasprzak on 16.01.2018.
 */
@Singleton
@Component(modules = {IncomeModule.class})
public interface IncomeComponent extends AndroidInjector<MainApplication> {

	void inject(IncomeActivity activity);

	void inject(IncomePresenter presenter);
}

