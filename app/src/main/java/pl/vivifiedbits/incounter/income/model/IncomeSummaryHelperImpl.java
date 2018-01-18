package pl.vivifiedbits.incounter.income.model;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */

public class IncomeSummaryHelperImpl implements IncomeSummaryHelperInterface {

	@Override
	public Income getSummary(List<Income> incomes) {
		Income summary = new Income(0, 0);

		for (Income income : incomes) {
			summary.addToValue(income.getValue());
		}

		//incomes.stream().mapToDouble(value -> value.getValue()).sum(); api 24....

		return summary;
	}

	@Override
	public Income getPeriodSummary(long unixMin, long unixMax, List<Income> incomes) {
		Income summary = new Income(0, 0);

		for (Income income : incomes) {
			Timber.d("check: " + income.dateUnix + " min: " + unixMin + " max: " + unixMax + " " +
					 (income.dateUnix >= unixMin && income.dateUnix <= unixMax));
			if (income.dateUnix >= unixMin && income.dateUnix <= unixMax) {
				summary.addToValue(income.getValue());
			}
		}

		return summary;
	}
}
