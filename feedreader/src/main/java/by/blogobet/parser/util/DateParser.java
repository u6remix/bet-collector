package by.blogobet.parser.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateParser {
	private static final Logger logger = Logger.getLogger(DateParser.class);
	private static List<String> baseFormatList = new ArrayList<String>();
	private static List<String> dayFormatList = new ArrayList<String>();
	private static final TimeZone DEFAULT_PAGE_TZ = TimeZone.getTimeZone("EST");
	static {
		baseFormatList.add("MMM dd, yyyy, HH:mm");
		baseFormatList.add("EEEE, MMMM dd'th', yyyy HH:mm");
		baseFormatList.add("EEEE, MMMM dd'st', yyyy HH:mm");
		baseFormatList.add("EEEE, MMMM dd'nd', yyyy HH:mm");

		dayFormatList.add("'Yesterday', HH:mm");
		dayFormatList.add("'Today', HH:mm");
		dayFormatList.add("'Tomorrow', HH:mm");
	}

	/**
	 * Handle values like: 
	 * 
	 * Yesterday, 22:16 
	 * Jul 19, 2015, 11:04 
	 * Yesterday, 01:17
	 * Tomorrow, 20:00 Tuesday, 
	 * July 14th, 2015 22:15 
	 * Wednesday, April 01st,2015 11:30
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseTimeLine(String str) {
		Date date = null;
		for (String format : baseFormatList) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(format,
						Locale.US);
				formatter.setTimeZone(DEFAULT_PAGE_TZ);
				date = formatter.parse(str);
				if (date != null) {
					break;
				}
			} catch (ParseException e) {
				continue;
			}
		}
		if (date == null) {
			for (String format : dayFormatList) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(format,
							Locale.US);
					date = formatter.parse(str);
					if (date != null) {
						break;
					}
				} catch (ParseException e) {
					continue;
				}
			}
		}
		if (str.startsWith("Yesterday")) {
			date = handleByToday(date, -1);
		} else if (str.startsWith("Today")) {
			date = handleByToday(date, 0);
		} else if (str.startsWith("Tomorrow")) {
			date = handleByToday(date, 1);
		}

		return date;
	}

	@SuppressWarnings("deprecation")
	public static Date handleByToday(Date date, int addingCount) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(currentDate.getYear() + 1900, currentDate.getMonth(),
				currentDate.getDate());
		int timezonesDiff = TimeZone.getDefault().getRawOffset()- DEFAULT_PAGE_TZ.getRawOffset();
		if(timezonesDiff>0 && timezonesDiff>getCurrentTimeOnly()){
			addingCount--;
		}
		cal.add(Calendar.DAY_OF_MONTH, addingCount);
		cal.add(Calendar.MILLISECOND,timezonesDiff);

		return cal.getTime();
	}
	
	private static int getCurrentTimeOnly(){
		Date currentDate = new Date();
		return (currentDate.getHours()*60*60*1000 + currentDate.getMinutes()*60*1000);
		
	}
}
