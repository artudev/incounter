package pl.vivifiedbits.incounter.income;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.income.dialog.EditIncomeListener;
import pl.vivifiedbits.incounter.income.dialog.IncomeDialog;
import pl.vivifiedbits.incounter.income.dialog.PercentDialog;
import pl.vivifiedbits.incounter.income.dialog.SetPercentListener;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.income.model.IncomeSummaryHelperImpl;
import pl.vivifiedbits.incounter.parent.BaseFragment;
import timber.log.Timber;

import static pl.vivifiedbits.incounter.R.id.rv_income;
import static pl.vivifiedbits.incounter.R.id.tv_sub_sum;
import static pl.vivifiedbits.incounter.R.id.tv_sub_sum2;
import static pl.vivifiedbits.incounter.R.id.tv_sum;
import static pl.vivifiedbits.incounter.R.id.tv_sum2;

/**
 * Created by Artur Kasprzak on 29.11.2017.
 */

public class IncomeFragment extends BaseFragment implements IncomeContract.View {

	private IncomeContract.Presenter mPresenter;

	@BindView(rv_income)
	RecyclerView mRecyclerView;

	@BindView(tv_sum)
	TextView mTvSum;
	@BindView(tv_sub_sum)
	TextView mTvSubSum;
	@BindView(tv_sum2)
	TextView mTvSum2;
	@BindView(tv_sub_sum2)
	TextView mTvSubSum2;
	@BindView(R.id.fab)
	FloatingActionButton mFloatingActionButton;

	private Unbinder mUnbinder;

	public IncomeFragment() {
	}

	@Override
	public void showRemoveDialog(Income income) {
		showFragmentSnackbar(R.string.snackbar_item_removed, R.string.restore,
				v -> mPresenter.restoreIncome(income));
	}

	@Override
	public void showEditDialog(Income income) {
		IncomeDialog dialog = IncomeDialog.newInstance((EditIncomeListener) mPresenter, income);
		dialog.show(getChildFragmentManager(), null);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Timber.d("onCreateView");

		View view = inflater.inflate(R.layout.fragment_income, container, false);
		mUnbinder = ButterKnife.bind(this, view);

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Timber.d("onActivityCreated");
		setupFloatingActionButton();
		mPresenter = new IncomePresenter(this, new IncomeSummaryHelperImpl());

		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setAdapter(mPresenter.getIncomeAdapter());
	}

	@Override
	public void onDestroyView() {
		Timber.d("onDestroyView");
		super.onDestroyView();
		mUnbinder.unbind();
	}

	@Override
	public void onStart() {
		super.onStart();
		Timber.d("onResume");
		mPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		Timber.d("onResume");
		mPresenter.onResume();
	}

	@Override
	public void onPause() {
		Timber.d("onPause");
		mPresenter.onPause();
		super.onPause();
	}

	private void setupFloatingActionButton() {

		mFloatingActionButton.setImageResource(R.drawable.ic_add);

		mFloatingActionButton.setOnClickListener(v -> {
			IncomeDialog dialog = IncomeDialog.newInstance((EditIncomeListener) mPresenter, null);
			dialog.show(getChildFragmentManager(), null);
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_set_percent:
				showPercentDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void showPercentDialog() {
		PercentDialog percentDialog = PercentDialog.newInstance((SetPercentListener) mPresenter);
		percentDialog.show(getChildFragmentManager(), null);
	}

	@Override
	public void displaySummary(float sum, float subSum) {
		mTvSum.setText(String.format("%.2f", sum));
		mTvSubSum.setText(String.format("%.2f", subSum));
	}

	@Override
	public void displayMonthlySummary(float sum, float subSum) {
		mTvSum2.setText(String.format("%.2f", sum));
		mTvSubSum2.setText(String.format("%.2f", subSum));
	}
}
