package pl.vivifiedbits.incounter.income.model;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * Created by Artur Kasprzak on 15.01.2018.
 */

public interface FileHelperInterface<T> {

	void save(Context context, String fileName, T t);

	void load(Context context, String fileName, Type type, OnLoadCallback<T> onLoadCallback);

	interface OnLoadCallback<T> {
		void onLoadCompleted(T t);
	}
}
