package pl.vivifiedbits.incounter.income;

import pl.vivifiedbits.incounter.income.adapter.IncomeAdapter;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.income.model.IncomeContainer;
import pl.vivifiedbits.incounter.parent.BaseFragmentView;
import pl.vivifiedbits.incounter.parent.BasePresenter;

/**
 * Created by Artur Kasprzak on 29.11.2017.
 */

public interface IncomeContract {

	interface View extends BaseFragmentView<Presenter> {
		void showRemoveDialog(Income income);

		void showEditDialog(Income income);

		void displaySummary(float sum, float subSum);

		void displayMonthlySummary(float sum, float subSum);
	}

	interface Presenter extends BasePresenter {

		IncomeAdapter getIncomeAdapter();

		IncomeContainer getIncomeContainer();

		void restoreIncome(Income income);

		void onResume();

		void onPause();
	}
}
