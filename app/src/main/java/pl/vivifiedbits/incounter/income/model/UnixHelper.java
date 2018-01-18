package pl.vivifiedbits.incounter.income.model;

import java.util.Calendar;

/**
 * Created by Artur Kasprzak on 17.01.2018.
 */

public class UnixHelper {

	public static long getUnixFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1, 0, 0, 0);
		return calendar.getTimeInMillis();
	}

	public static long getUnixLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month+1, 1, 0, 0, 0);
		return calendar.getTimeInMillis();
	}
}
