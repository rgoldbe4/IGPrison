package us.ignitiongaming.util.convert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;



public class DateConverter {
	/**
	 * Retrieve a Date object from a String
	 * @param str
	 * @return
	 */
	public static Date convertStringDateTimeToDate(String str) {
		SimpleDateFormat formatter = getDateFormatter();
        try {
        	Date date = formatter.parse(str);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * DateTime format (for SQL)
	 * @return
	 */
	public static SimpleDateFormat getDateFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public static String convertContextToString(String context) {
		if (context.contains("d") || context.contains("D")) {
			context = context.replace("D", "").replace("d", "");
			//Add days.
			return context + " day(s)";
		} else if (context.contains("m")) {
			context = context.replace("m", "");
			return context + " minute(s)";
		} else if (context.contains("M")) {
			context = context.replace("M", "");
			return context + " month(s)";
		} else if (context.contains("s") || context.contains("S")) {
			context = context.replace("s", "").replace("S", "");
			return context + " second(s)";
		} else if (context.contains("h") || context.contains("H")) {
			context = context.replace("h", "").replace("H", "");
			return context + " hour(s)";
		}
		return "Invalid context";
	}
	
	public static Date convertSingleArgumentContextToDate(String context) {
		Date currentDate = getCurrentTime();
		String[] contexts = context.split("\\|");
		for (String con : contexts) {
			currentDate = addTimeFromString(currentDate, con);
		}
		return currentDate;
	}
	
	public static boolean isValidContext(String context) {
		try {
			if (context.contains("d") || context.contains("D")) {
				context = context.replace("D", "").replace("d", "");
				Integer.parseInt(context);
				//Add days.
				return true;
			} else if (context.contains("m")) {
				context = context.replace("m", "");
				Integer.parseInt(context);
				return true;
			} else if (context.contains("M")) {
				context = context.replace("M", "");
				Integer.parseInt(context);
				return true;
			} else if (context.contains("s") || context.contains("S")) {
				context = context.replace("s", "").replace("S", "");
				Integer.parseInt(context);
				return true;
			} else if (context.contains("h") || context.contains("H")) {
				context = context.replace("h", "").replace("H", "");
				Integer.parseInt(context);
				return true;
			}
			
			return false;
		} catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	public static Date addTimeFromString(Date currentDate, String context) {
		if (context.contains("d") || context.contains("D")) {
			context = context.replace("D", "").replace("d", "");
			//Add days.
			return addTime(currentDate, Calendar.DATE, Integer.parseInt(context));
		} else if (context.contains("m")) {
			context = context.replace("m", "");
			return addTime(currentDate, Calendar.MINUTE, Integer.parseInt(context));
		} else if (context.contains("M")) {
			context = context.replace("M", "");
			return addTime(currentDate, Calendar.MONTH, Integer.parseInt(context));
		} else if (context.contains("s") || context.contains("S")) {
			context = context.replace("s", "").replace("S", "");
			return addTime(currentDate, Calendar.SECOND, Integer.parseInt(context));
		} else if (context.contains("h") || context.contains("H")) {
			context = context.replace("h", "").replace("H", "");
			return addTime(currentDate, Calendar.HOUR, Integer.parseInt(context));
		}
		return currentDate;
	}
	/**
	 * Example:
	 * addTime(date, Calendar.DATE, 1); -> Adds 1 day.
	 * addTime(date, Calender.DATE, -1); -> Removes 1 day;
	 * @param currentDate
	 * @param addType
	 * @param amount
	 * @return
	 */
	public static Date addTime(Date currentDate, int addType, int amount) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(addType, amount);
			return convertStringDateTimeToDate(getDateFormatter().format(cal.getTime()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return currentDate;
		}
	}
	
	public static Date getCurrentTime() {
		return new Date();
	}
	
	public static String getCurrentTimeString() {
		return getDateFormatter().format(new Date());
	}
	
	/**
	 * Convert an unfriendly database datetime format into a friendly format!
	 * @param dateTime (EXAMPLE -> 2017-01-21 23:03:18)
	 * @return FRIENDLY -> 01-21-2017 at <time>
	 */
	public static String toFriendlyDate(String dateTime) {
		//EXAMPLE -> 2017-01-21 23:03:18
		String date = dateTime.split(" ")[0];
		String time = dateTime.split(" ")[1];
		//FRIENDLY -> 01-21-2017 at <time>
		date = date.split("-")[1] + "/" + date.split("-")[2] + "/" + date.split("-")[0];
		int hour = Integer.parseInt(time.split(":")[0]);
		boolean isAfternoon = false;		
		if (hour >= 12) {
			if(hour > 12) hour = hour - 12;
			isAfternoon = true;
		}
		if (hour == 0) hour = 12;
		//UPDATE: 2/19 -> Removed seconds to make the date nicer.
		time =  hour + ":" + time.split(":")[1]; // + ":" + time.split(":")[2];
		return date + " at " + time + " " + (isAfternoon ? "PM" : "AM") + " EST";
	}
	
	/**
	 * Convert an unfriendly database datetime format into a friendly format!
	 * @param dateTime (EXAMPLE -> 2017-01-21 23:03:18)
	 * @return FRIENDLY -> 01-21-2017 at <time>
	 */
	public static String toFriendlyDate(Date dateTime) {
		return toFriendlyDate(getDateFormatter().format(dateTime));
	}
	
	public static String convertDateToString(Date date) {
		return getDateFormatter().format(date);
	}
	
	public static String compareDatesToSeconds(Date start, Date expires) {
		long seconds = (expires.getTime()-start.getTime())/1000;
		return seconds + " second(s)";
	}
	
	public static long compareDates(Date start, Date expires) {
		return (expires.getTime() - start.getTime());
	}
	
	public static String compareDatesFriendly(Date start, Date expires) {
		String format = "";
		long duration = compareDates(start, expires);
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		duration -= TimeUnit.DAYS.toMillis(days);

		long hours = TimeUnit.MILLISECONDS.toHours(duration);
		duration -= TimeUnit.HOURS.toMillis(hours);

		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		duration -= TimeUnit.MINUTES.toMillis(minutes);

		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		if (days != 0) {
			format += days + " day(s) ";
		}
		if (hours != 0) {
			format += hours + " hour(s) ";
		}
		if (minutes != 0) {
			format += minutes + " minute(s) ";
		}
		if (seconds != 0) {
			format += seconds + " second(s).";
		}
		
		return format;
	}
	
	public static String compareDatesToNowFriendly(Date expires) {
		return compareDatesFriendly(getCurrentTime(), expires);
	}
}
