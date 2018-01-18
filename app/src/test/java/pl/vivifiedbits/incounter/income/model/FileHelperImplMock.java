package pl.vivifiedbits.incounter.income.model;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */

public class FileHelperImplMock<T> implements FileHelperInterface<T> {

	private T mT;

	@Override
	public void save(Context context, String fileName, T t) {
		mT = t;
	}

	@Override
	public void load(Context context, String fileName, Type type, OnLoadCallback onLoadCallback) {
		onLoadCallback.onLoadCompleted(mT);
	}
}
