package pl.vivifiedbits.incounter.income.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */
public class IncomeSummaryHelperImplTest {

	private ArrayList<Income> mIncomes;

	private IncomeSummaryHelperInterface mIncomeSummaryHelper;

	private long mMinUnix;
	private long mMaxUnix;

	@Before
	public void setup() {
		mIncomes = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 12, 0, 0, 0);

		Income income1_1 = new Income(calendar.getTimeInMillis(), 2000);
		Income income1_2 = new Income(calendar.getTimeInMillis(), 200);
		Income income1_3 = new Income(calendar.getTimeInMillis(), 20);

		calendar.set(2000, 1, 12, 0, 0, 0);

		Income income2_1 = new Income(calendar.getTimeInMillis(), 1000);

		calendar.set(2000, 0, 1, 0, 0, 0);
		mMinUnix = calendar.getTimeInMillis();
		calendar.set(2000, 1, 1, 0, 0, 0);
		mMaxUnix = calendar.getTimeInMillis();

		mIncomes.add(income1_1);
		mIncomes.add(income1_2);
		mIncomes.add(income1_3);
		mIncomes.add(income2_1);

		mIncomeSummaryHelper = new IncomeSummaryHelperImpl();
	}

	@Test
	public void getSummary() throws Exception {
		Income summary = mIncomeSummaryHelper.getSummary(mIncomes);
		Assert.assertEquals(3220f, summary.getValue(), 0);
	}

	@Test
	public void getPeriodSummary() throws Exception {
		Income summary = mIncomeSummaryHelper.getPeriodSummary(mMinUnix, mMaxUnix, mIncomes);
		Assert.assertEquals(2220f, summary.getValue(), 0);
	}
}
