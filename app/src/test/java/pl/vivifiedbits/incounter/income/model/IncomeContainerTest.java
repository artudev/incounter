package pl.vivifiedbits.incounter.income.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */
public class IncomeContainerTest {

	private ArrayList<Income> mIncomes;

	private IncomeContainer mIncomeContainer;
	private FileHelperInterface mFileHelper;

	private Income mIncome;

	@Before
	public void setup() {
		mIncomes = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 12, 0, 0, 0);

		Income income1_1 = new Income(0, 2000);
		Income income1_2 = new Income(0, 200);
		mIncome = new Income(0, 20);

		mIncomes.add(income1_1);
		mIncomes.add(income1_2);
		mIncomes.add(mIncome);

		mFileHelper = new FileHelperImplMock();
		mIncomeContainer = new IncomeContainer(mFileHelper);
		mIncomeContainer.setIncomes(mIncomes);
	}

	@Test
	public void addIncome() throws Exception {
		Assert.assertEquals(3, mIncomeContainer.getIncomes().size());
		mIncomeContainer.addIncome(null, new Income(0, 1));
		Assert.assertEquals(4, mIncomeContainer.getIncomes().size());
	}

	@Test
	public void removeIncome() throws Exception {
		Assert.assertEquals(3, mIncomeContainer.getIncomes().size());
		mIncomeContainer.removeIncome(null, mIncome);
		Assert.assertEquals(2, mIncomeContainer.getIncomes().size());
	}
}
