package pl.vivifiedbits.incounter.income.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.vivifiedbits.incounter.R;
import pl.vivifiedbits.incounter.income.model.Income;
import pl.vivifiedbits.incounter.parent.BaseActivity;
import pl.vivifiedbits.incounter.parent.BaseFragment;
import timber.log.Timber;

public class IncomeDialog extends DialogFragment {

	@BindView(R.id.acet_percent)
	AppCompatEditText mAcetIncome;
	private Unbinder mUnbinder;
	private Income mIncome;
	private EditIncomeListener mEditIncomeListener;
	private void setIncome(Income income) {
		mIncome = income;
	}
	public void setEditIncomeListener(@NonNull EditIncomeListener editIncomeListener) {
		mEditIncomeListener = editIncomeListener;
	}
	public static IncomeDialog newInstance(@NonNull EditIncomeListener listener, Income income) {
		IncomeDialog incomeDialog = new IncomeDialog();
		incomeDialog.setIncome(income);
		incomeDialog.setEditIncomeListener(listener);
		return incomeDialog;
	}
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Timber.d("onCreateDialog");
		View view =
				LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_income, null, false);
		mUnbinder = ButterKnife.bind(this, view);
		BaseActivity baseActivity = ((BaseFragment) getParentFragment()).getBaseActivity();
		if (mIncome != null) {
			mAcetIncome.setText(String.valueOf(mIncome.getValue()));
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setView(view);
		builder.setPositiveButton(R.string.set, (dialog, which) -> {
			if (mAcetIncome.getText().toString().isEmpty()) {
				dialog.dismiss();
				baseActivity.hideKeyboard();
				return;
			}
			float value = Float.parseFloat(mAcetIncome.getText().toString().replaceAll(",", "."));
			if (mIncome != null) {
				mIncome.setValue(value);
				mEditIncomeListener.onEditIncome(mIncome);
			} else {
				mIncome = new Income(System.currentTimeMillis(), value);
				mEditIncomeListener.onAddIncome(mIncome);
			}
			mAcetIncome.clearFocus();
			baseActivity.hideKeyboard();
		});
		builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
			mAcetIncome.clearFocus();
			baseActivity.hideKeyboard();
		});
		return builder.create();
	}

	@Override
	public void onDestroyView() {
		Timber.d("onDestroyView");
		super.onDestroyView();
		mUnbinder.unbind();
	}
}
