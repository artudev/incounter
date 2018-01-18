package pl.vivifiedbits.incounter.income.model;

import android.content.Context;

import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.vivifiedbits.utils.fileio.FileIO;
import pl.vivifiedbits.utils.lib.filestorage.FileStorage;
import pl.vivifiedbits.utils.lib.filestorage.FileStorageInternal;
import timber.log.Timber;

/**
 * Created by Artur Kasprzak on 15.01.2018.
 */

public class FileHelperImpl<T> implements FileHelperInterface<T> {

	public Completable getSaveLocalObservable(final Context context, String fileName, T t) {

		return Completable.create(emitter -> {
			FileStorage fileStorage = new FileStorageInternal(context, fileName);
			FileIO.writeToFile(fileStorage, t);
			emitter.onComplete();
		});
	}

	@Override
	public void save(Context context, String fileName, T t) {
		getSaveLocalObservable(context, fileName, t).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {

			@Override
			public void onSubscribe(@NonNull Disposable d) {
				Timber.d("SAVE SUBSCRIBED");
			}

			@Override
			public void onComplete() {
				Timber.d("SAVE ENDED " + Thread.currentThread().getName());
			}

			@Override
			public void onError(@NonNull Throwable e) {
				Timber.d("SAVE ERROR " + e.getLocalizedMessage());
				Timber.e(e);
			}
		});
	}

	public Single<T> getLoadFromLocalObservable(Context context, String fileName, Type type) {
		return Single.create(emitter -> {
			FileStorage fileStorage = new FileStorageInternal(context, fileName);

			T t = FileIO.readFromFile(fileStorage, type);

			if (t != null) {
				emitter.onSuccess(t);
			}
		});
	}

	@Override
	public void load(Context context, String fileName, Type type,
			OnLoadCallback<T> onLoadCallback) {
		getLoadFromLocalObservable(context, fileName, type).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(incomes -> onLoadCallback.onLoadCompleted(incomes));
	}
}
