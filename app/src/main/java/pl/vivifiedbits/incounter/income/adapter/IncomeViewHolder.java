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
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.utils.PreferencesHelper;

/**
 * Created by Artur Kasprzak on 23.04.2018.
 */
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

	protected void bindData(final Income income, OnIncomeClickListener listener) {

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
			if (listener != null) {
				listener.onRemoveClick(income);
			}
		});

		mView.setOnClickListener(v -> {
			if (listener != null) {
				listener.onEditClick(income);
			}
		});
	}
}
