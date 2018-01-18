package pl.vivifiedbits.incounter.parent;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Artur Kasprzak on 27.11.2017.
 */

public interface BaseFragmentView<T> {
	Activity getActivity();

	Context getContext();

	void showToast(int msgResId);

	void showToast(String message);

	void showFragmentSnackbar(int msgResId);

	void showFragmentSnackbar(String msg);

	void showSnackbar(int msgResId);

	void showSnackbar(String msg);
}
