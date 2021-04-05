package xyhj.knkiss;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isWeekendToday(){
		Date date = new Date( );
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
}
