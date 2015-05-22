package com.alu.omc.oam.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeUtils {
    
    private static final Log log = LogFactory.getLog(TimeUtils.class);
    // if time elapsed 75%
    public static boolean elapsedOfPercent(Date startDate, Date endDate, double percent) {
        log.debug("The date range is " + startDate + " - " + endDate);
        long expires = endDate.getTime() - startDate.getTime();
        long elapses = new Date().getTime() - startDate.getTime();
        if( elapses > percent * expires) {
            return true;
        }
        return false;
    }
    
    public static Date Str8601FormatToDate(String str) 
    {   	  
	    if(str == null)
	    	return null;
	    
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    format.setTimeZone(TimeZone.getTimeZone("Zulu"));
	    Date date = null;
	    String strDate = str.replace("T", " ").replace("Z", "");
	    try {
	    	date = format.parse(strDate);
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    return date;
	}
    
    /**
     * get current time
     * 
     * @return
     */
    public static String getNowTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    /**
     * get current time
     * 
     * @return
     */
    public static String getNowTime2() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
    
    public static Date stringToDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * get current time
     * 
     * @return
     */
    public static String getTodayBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //TODO should can be config Resources.getInstance().getStringProperty("HISTORY_THRESHOLD",
        int historyThreshold = Integer.parseInt("24");
        calendar.add(Calendar.HOUR, -historyThreshold);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        return time;
    }
    
    public static String longToString(long time) {
        if (time == 0) {
            return "";
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
