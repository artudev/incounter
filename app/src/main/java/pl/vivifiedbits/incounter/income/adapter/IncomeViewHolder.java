package pl.vivifiedbits.incounter.income.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.customview.DoubleTextView;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.utils.PreferencesHelper;

/**
 * Created by Artur Kasprzak on 23.04.2018.
 */
public class IncomeViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.tv_item_date)
	TextView mTvDate;
	@BindView(R.id.dtv_item_value)
	DoubleTextView mTvValue;

	@BindView(R.id.ibtn_item_remove)
	ImageButton mIbRemove;

	private View mView;
	private Income mIncome;
	private Calendar mCalendar;

	public IncomeViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		mView = itemView;
		mCalendar = Calendar.getInstance();
	}

	protected void bindData(final Income income, OnIncomeClickListener listener) {

		mIncome = income;

		mCalendar.setTimeInMillis(income.dateUnix);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(mCalendar.getTime());

		mTvDate.setText(date);

		float percent = PreferencesHelper.getPercent(mView.getContext());
		float subValues = income.getValue() * percent / 100f;

		mTvValue.setText(String.format("%.2f", income.getValue()),
				String.format("%.2f", subValues));

		mIbRemove.setVisibility(View.VISIBLE);

		mIbRemove.setOnClickListener(v -> {
			if (listener != null) {
				listener.onRemoveClick(mIncome);
			}
		});

		mView.setOnClickListener(v -> {
			if (listener != null) {
				listener.onEditClick(mIncome);
			}
		});
	}
}
