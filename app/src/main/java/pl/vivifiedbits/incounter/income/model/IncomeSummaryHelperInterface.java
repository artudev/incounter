package pl.vivifiedbits.incounter.income.model;

import java.util.List;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */

public interface IncomeSummaryHelperInterface {

	Income getSummary(List<Income> incomes);

	Income getPeriodSummary(long unixMin, long unixMax, List<Income> incomes);
}
