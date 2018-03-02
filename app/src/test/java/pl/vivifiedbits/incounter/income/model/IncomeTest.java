package pl.vivifiedbits.incounter.income.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */
public class IncomeTest {

	private Income mIncome1;
	private Income mIncome2;
	private ArrayList<Income> mIncomes;

	@Before
	public void setup() {

		mIncome1 = new Income(0, 1);
		mIncome2 = new Income(1, 1);

		mIncomes = new ArrayList<>();
		mIncomes.add(mIncome1);
		mIncomes.add(mIncome2);
	}

	@Test
	public void comparatorTest() throws Exception {

		Assert.assertEquals(mIncome1, mIncomes.get(0));

		Collections.sort(mIncomes, Income.getComparator(false));
		Assert.assertEquals(mIncome2, mIncomes.get(0));

		Collections.sort(mIncomes, Income.getComparator(true));
		Assert.assertEquals(mIncome1, mIncomes.get(0));
	}
}
