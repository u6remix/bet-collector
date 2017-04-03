package by.blogobet.util;

import java.util.Date;

public class DateUtil {

	//test 123
	//test12345678
	//ololo
	//test 123 in test branch
	public static Date getDate2HoursBefore(){
		long time2HourBefore = new Date().getTime() - 2*60*60*1000;
		Date date = new Date(time2HourBefore);
		return date;
	}
	//new in test
    //add in master
}
