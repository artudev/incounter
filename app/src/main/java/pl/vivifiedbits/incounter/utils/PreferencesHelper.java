package pl.vivifiedbits.incounter.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Artur Kasprzak on 27.10.2017.
 */

@SuppressLint("CommitPrefEdits")
public abstract class PreferencesHelper {

	public static final int NOT_SET = -1;

	public static final String PREF_NAME = "pl.vivifiedbits.incounter.data";

	private static final String PREF_PERCENT = "percent";

	private PreferencesHelper() {

	}

	public static SharedPreferences.Editor getPrefEditor(Context context) {
		SharedPreferences pref = getPref(context);
		return pref.edit();
	}

	public static SharedPreferences getPref(Context context) {
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	//============================================

	public static float getPercent(Context context) {
		return getPref(context).getFloat(PREF_PERCENT, 100f);
	}

	public static void setPercent(Context context, float percent) {
		getPrefEditor(context).putFloat(PREF_PERCENT, percent).commit();
	}

	//============================================
}
