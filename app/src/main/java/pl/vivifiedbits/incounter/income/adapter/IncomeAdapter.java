package pl.vivifiedbits.incounter.income.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.utils.PreferencesHelper;

/**
 * Created by Artur Kasprzak on 13.12.2017.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

	private ArrayList<Income> mIncomes;
	private OnIncomeClickListener mOnIncomeClickListener;

	public IncomeAdapter() {
		mIncomes = new ArrayList<>();
	}

	public void setOnIncomeClickListener(OnIncomeClickListener onQuoteClickListener) {
		mOnIncomeClickListener = onQuoteClickListener;
	}

	public void setIncomeList(List<Income> list) {
		mIncomes.clear();
		mIncomes.addAll(list);
		sortList();
	}

	private void sortList() {
		Collections.sort(mIncomes, Income.getComparator(false));
		notifyDataSetChanged();
	}

	@Override
	public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_income, parent, false);
		return new IncomeViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(IncomeViewHolder holder, int position) {
		holder.bindData(mIncomes.get(position));
	}

	@Override
	public int getItemCount() {
		return mIncomes.size();
	}

	public class IncomeViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_item_date)
		TextView mTvDate;
		@BindView(R.id.tv_item_value)
		TextView mTvValue;
		@BindView(R.id.tv_item_percent)
		TextView mTvSubValue;

		@BindView(R.id.ibtn_item_remove)
		ImageButton mIbRemove;

		View mView;

		public IncomeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			mView = itemView;
		}

		private void bindData(final Income income) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(income.dateUnix);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String date = dateFormat.format(calendar.getTime());

			mTvDate.setText(date);
			mTvValue.setText(String.format("%.2f", income.getValue()));

			float percent = PreferencesHelper.getPercent(mView.getContext());
			float subValues = income.getValue() * percent / 100f;

			mTvSubValue.setText(String.format("%.2f", subValues));

			mIbRemove.setVisibility(View.VISIBLE);

			mIbRemove.setOnClickListener(v -> {
				if (mOnIncomeClickListener != null) {
					mOnIncomeClickListener.onRemoveClick(income);
				}
			});

			mView.setOnClickListener(v -> {
				if (mOnIncomeClickListener != null) {
					mOnIncomeClickListener.onEditClick(income);
				}
			});
		}
	}
}
