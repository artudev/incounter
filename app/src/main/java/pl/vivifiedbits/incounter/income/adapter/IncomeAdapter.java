package pl.vivifiedbits.incounter.income.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.income.model.Income;

/**
 * Created by Artur Kasprzak on 13.12.2017.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeViewHolder> {

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
		holder.bindData(mIncomes.get(position), mOnIncomeClickListener);
	}

	@Override
	public int getItemCount() {
		return mIncomes.size();
	}
}
