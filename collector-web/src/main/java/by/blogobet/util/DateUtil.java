package by.blogobet.util;

import java.util.Date;

public class DateUtil {

	//test 123
	//test12345678
	public static Date getDate2HoursBefore(){
		long time2HourBefore = new Date().getTime() - 2*60*60*1000;
		Date date = new Date(time2HourBefore);
		return date;
	}
}
