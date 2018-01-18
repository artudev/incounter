package pl.vivifiedbits.incounter.income.model;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import pl.vivifiedbits.incounter.income.adapter.IncomeAdapter;

/**
 * Created by Artur Kasprzak on 24.10.2017.
 */

public class IncomeContainer {

	public static final String FILE_NAME = "income";

	private ArrayList<Income> mIncomes;
	private Subject<ArrayList<Income>> mSubject;
	private FileHelperInterface<List<Income>> mFileHelper;

	public IncomeContainer(FileHelperInterface<List<Income>> fileHelper) {
		mFileHelper = fileHelper;

		mIncomes = new ArrayList<>();

		mSubject = BehaviorSubject.create();
		mSubject = mSubject.toSerialized();
	}

	public Subject<ArrayList<Income>> getSubject() {
		return mSubject;
	}

	public IncomeAdapter getIncomeAdapter() {

		IncomeAdapter incomeAdapter = new IncomeAdapter();
		incomeAdapter.setIncomeList(mIncomes);

		return incomeAdapter;
	}

	public ArrayList<Income> getIncomes() {
		return mIncomes;
	}

	public synchronized void setIncomes(List<Income> incomes) {
		mIncomes.clear();
		mIncomes.addAll(incomes);
		publishUpdate();
	}

	public void publishUpdate() {
		mSubject.onNext(mIncomes);
	}

	//-----------------------UTILITY FUNCTIONS

	public void addIncome(Context context, Income income) {
		mIncomes.add(income);
		publishUpdate();
		save(context);
	}

	public void removeIncome(Context context, Income income) {
		mIncomes.remove(income);
		publishUpdate();
		save(context);
	}

	//-----------------------SAVE

	public void save(Context context) {
		mFileHelper.save(context, FILE_NAME, mIncomes);
	}

	//-----------------------LOAD

	public void load(final Context context) {
		Type listType = new TypeToken<ArrayList<Income>>() {
		}.getType();

		mFileHelper.load(context, FILE_NAME, listType, incomes -> setIncomes(incomes));
	}
}
