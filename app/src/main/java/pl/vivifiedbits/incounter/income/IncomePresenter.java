package pl.vivifiedbits.incounter.income;

/**
 * Created by Artur Kasprzak on 29.11.2017.
 */

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import pl.vivifiedbits.incounter.MainApplication;
import pl.vivifiedbits.incounter.income.adapter.IncomeAdapter;
import pl.vivifiedbits.incounter.income.adapter.OnIncomeClickListener;
import pl.vivifiedbits.incounter.income.dialog.EditIncomeListener;
import pl.vivifiedbits.incounter.income.dialog.SetPercentListener;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.income.model.IncomeContainer;
import pl.vivifiedbits.incounter.income.model.IncomeSummaryHelperInterface;
import pl.vivifiedbits.incounter.income.model.UnixHelper;
import pl.vivifiedbits.incounter.utils.PreferencesHelper;
import timber.log.Timber;

public class IncomePresenter
		implements IncomeContract.Presenter, OnIncomeClickListener, EditIncomeListener,
		SetPercentListener {

	private final IncomeContract.View mView;

	@Inject
	IncomeContainer mIncomeContainer;

	private IncomeSummaryHelperInterface mIncomeSummaryHelper;

	private IncomeAdapter mIncomeAdapter;
	private Disposable mSubjectDisposable;

	public IncomePresenter(@NonNull IncomeContract.View view,
			IncomeSummaryHelperInterface incomeSummaryHelper) {
		mView = view;

		((MainApplication) view.getActivity().getApplication()).getIncomeComponent().inject(this);

		mIncomeSummaryHelper = incomeSummaryHelper;
	}

	@Override
	public IncomeContainer getIncomeContainer() {
		return mIncomeContainer;
	}

	@Override
	public IncomeAdapter getIncomeAdapter() {
		if (mIncomeAdapter == null) {
			mIncomeAdapter = mIncomeContainer.getIncomeAdapter();
		}
		mIncomeAdapter.setOnIncomeClickListener(this);

		return mIncomeAdapter;
	}

	@Override
	public void onResume() {
		if (mSubjectDisposable != null) {
			mSubjectDisposable.dispose();
			mSubjectDisposable = null;
		}
		mSubjectDisposable = mIncomeContainer.getSubject().subscribe(incomes -> {
			if (mIncomeAdapter != null) {
				mIncomeAdapter.setIncomeList(incomes);
				Timber.d("subject update " + incomes.size());
				computeSummaries();
			}
		});

		loadIncomes();
	}

	private void loadIncomes() {
		mIncomeContainer.load(mView.getContext());
	}

	@Override
	public void onPause() {
		if (mSubjectDisposable != null) {
			mSubjectDisposable.dispose();
			mSubjectDisposable = null;
		}
	}

	@Override
	public void restoreIncome(Income income) {
		mIncomeContainer.addIncome(mView.getContext(), income);
	}

	@Override
	public void onRemoveClick(Income income) {
		mIncomeContainer.removeIncome(mView.getContext(), income);
		mView.showRemoveDialog(income);
	}

	@Override
	public void onEditClick(Income income) {
		mView.showEditDialog(income);
	}

	private void computeSummaries() {
		float percent = PreferencesHelper.getPercent(mView.getContext());

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		long min = UnixHelper.getUnixFirstDayOfMonth(year, month);
		long max = UnixHelper.getUnixLastDayOfMonth(year, month);

		ArrayList<Income> incomes = mIncomeContainer.getIncomes();

		Income totalSummary = mIncomeSummaryHelper.getSummary(incomes);
		Income monthlySummary = mIncomeSummaryHelper.getPeriodSummary(min, max, incomes);

		mView.displaySummary(totalSummary.getValue(), totalSummary.getSubValue(percent));
		mView.displayMonthlySummary(monthlySummary.getValue(), monthlySummary.getSubValue(percent));
	}

	@Override
	public void onPercentSet(float percent) {
		PreferencesHelper.setPercent(mView.getContext(), percent);
		mIncomeContainer.publishUpdate();
	}

	@Override
	public void onAddIncome(Income income) {
		mIncomeContainer.addIncome(mView.getContext(), income);
	}

	@Override
	public void onEditIncome(Income income) {
		mIncomeContainer.publishUpdate();
	}
}
