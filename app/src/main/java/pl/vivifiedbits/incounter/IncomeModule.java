package pl.vivifiedbits.incounter;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.vivifiedbits.incounter.income.model.FileHelperImpl;
import pl.vivifiedbits.incounter.income.model.FileHelperInterface;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.income.model.IncomeContainer;

/**
 * Created by Artur Kasprzak on 16.01.2018.
 */

@Module
public class IncomeModule {

	@Provides
	@Singleton
	static IncomeContainer provideIncomeContainer(FileHelperInterface<List<Income>> fileHelper) {
		return new IncomeContainer(fileHelper);
	}

	@Provides
	public FileHelperInterface<List<Income>> provideFileHelper() {
		return new FileHelperImpl<>();
	}
}
