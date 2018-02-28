package pl.vivifiedbits.incounter.income.dialog;

import pl.vivifiedbits.incounter.income.model.Income;

/**
 * Created by Artur Kasprzak on 28.02.2018.
 */

public interface EditIncomeListener {

	void onAddIncome(Income income);

	void onEditIncome(Income income);
}
