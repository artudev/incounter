package pl.vivifiedbits.incounter.income.model;

import java.util.Comparator;

/**
 * Created by Artur Kasprzak on 13.12.2017.
 */

public class Income {

	public final long dateUnix;
	private float mValue;

	public Income(long dateUnix, float value) {
		this.dateUnix = dateUnix;
		mValue = value;
	}

	public void addToValue(float add) {
		mValue += add;
	}

	public float getSubValue(float percent) {
		return mValue * percent / 100f;
	}

	public float getValue() {
		return mValue;
	}

	public void setValue(float value) {
		mValue = value;
	}

	public static Comparator<Income> getComparator(boolean isAsc) {
		if (isAsc) {
			return (Income o1, Income o2) -> o1.dateUnix < o2.dateUnix ? -1
					: o1.dateUnix > o2.dateUnix ? 1 : 0;
		} else {
			return (Income o1, Income o2) -> o1.dateUnix < o2.dateUnix ? 1
					: o1.dateUnix > o2.dateUnix ? -1 : 0;
		}
	}
}
