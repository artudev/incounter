package pl.vivifiedbits.incounter.income.adapter;

import pl.vivifiedbits.incounter.income.model.Income;

/**
 * Created by Artur Kasprzak on 13.12.2017.
 */

public interface OnIncomeClickListener {
	 void onRemoveClick(Income income);
	 void onEditClick(Income income);

}
