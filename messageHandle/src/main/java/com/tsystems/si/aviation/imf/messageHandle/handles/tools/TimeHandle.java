/** 
 * Project Name:imgTelexHandle 
 * File Name:TimeHandle.java 
 * Package Name:com.tsystems.si.aviation.img.imgTelexHandle.utils 
 * Date:2015年12月16日上午11:23:45
 * version:v1.0
 * Copyright (c) 2015, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TimeHandle
{
  private static final Logger logger = LoggerFactory.getLogger(TimeHandle.class);
  static SimpleDateFormat fmtDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
  static SimpleDateFormat fmtDateTimewithT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  static SimpleDateFormat fmtDateTimeReadable = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  static SimpleDateFormat showFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  static SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
  static SimpleDateFormat fmtDateNoZX = new SimpleDateFormat("yyyyMMdd");
  static TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

  public static Calendar getMessageTimeByName(String fileName)
  {
    String[] nameSpilt = fileName.split("_");
    int length = nameSpilt.length;
    String timeString = nameSpilt[(length - 2)];
    Date date = null;
    try {
      date = fmtDateTime.parse(timeString);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);
    return calendar;
  }
  public static Date getMessageDateTimeByName(String fileName) {
    String[] nameSpilt = fileName.split("_");
    int length = nameSpilt.length;
    String timeString = nameSpilt[(length - 2)];
    Date date = null;
    try {
      date = fmtDateTime.parse(timeString);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
  public static Calendar getMessageTimeByFile(File file) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(file.lastModified());

    return calendar;
  }

  public static Calendar getMessageUTCTimeByName(String fileName) {
    String[] nameSpilt = fileName.split("_");
    int length = nameSpilt.length;
    String timeString = nameSpilt[(length - 2)];
    Date date = null;
    try {
      date = fmtDateTime.parse(timeString);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);
    calendar.setTimeZone(utcTimeZone);
    return calendar;
  }

  public static Calendar getMessageUTCTimeByFile(File file) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(file.lastModified());
    return calendar;
  }

  public static String getCalendarString(Calendar ca) {
    String dateString = showFmt.format(ca.getTime());
    return dateString;
  }

  public static Calendar getCalendarByString(String timeString)
  {
    Date date = null;
    try {
      date = fmtDateTimeReadable.parse(timeString);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);
    return calendar;
  }
  public static String getDateString(Date date) {
    String dateString = fmtDate.format(Long.valueOf(date.getTime()));
    return dateString;
  }

  public static String getDateStringNoZX(Date date) {
    String dateString = fmtDateNoZX.format(Long.valueOf(date.getTime()));
    return dateString;
  }

  public static String getDateTimeString(Date date) {
    String dateString = null;
    if (date != null) {
      dateString = showFmt.format(Long.valueOf(date.getTime()));
    }

    return dateString;
  }

  public static String getDateTimeWithTString(Date date)
  {
    String dateString = null;
    if (date != null) {
      dateString = fmtDateTimewithT.format(Long.valueOf(date.getTime()));
    }
    return dateString;
  }
  public static Date getDateTimeByTFormatString(String dateTString)
  {
	  
	  
	  Date date = null;
	    try {
	      date = fmtDateTimewithT.parse(dateTString);
	    }
	    catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return date;
  }
  
  public static Date getDateTimeByDateFormatString(String dateTString)
  {
	  
	  
	  Date date = null;
	    try {
	      date = fmtDate.parse(dateTString);
	    }
	    catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return date;
  }
  public static Map<String, Date> getFlightDateTimePeriod(Date dateTime, int beforeMin, int afterMin)
  {
    Map dateTimeHashMap = new HashMap();
    Calendar calendarBefore = Calendar.getInstance();
    calendarBefore.setTime(dateTime);
    Calendar calendarAfter = Calendar.getInstance();
    calendarAfter.setTime(dateTime);
    int tmp = 0 - beforeMin;
    calendarBefore.add(12, tmp);
    calendarAfter.add(12, afterMin);
    Date before = calendarBefore.getTime();
    Date after = calendarAfter.getTime();

    dateTimeHashMap.put("beforeDateTime", before);
    dateTimeHashMap.put("afterDateTime", after);
    return dateTimeHashMap;
  }

  public static Date dateAddMinutes(Date date, int minutes)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(12, minutes);
    return calendar.getTime();
  }

  public static Date dateSubMinutes(Date date, int minutes)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(12, -minutes);
    return calendar.getTime();
  }

  public static Date getActualFlightDateTime(Date telexDate, String flightTime)
  {
    Calendar calendarLocalTimeZone = Calendar.getInstance();
    calendarLocalTimeZone.setTime(telexDate);

    Calendar calendarUTCTimeZone = Calendar.getInstance();
    calendarUTCTimeZone.setTime(telexDate);
    calendarUTCTimeZone.setTimeZone(utcTimeZone);

    String flightHour = flightTime.substring(0, 2);
    String flightMin = flightTime.substring(2, 4);

    int localDate = calendarLocalTimeZone.get(5);
    int utcDate = calendarUTCTimeZone.get(5);

    if ((!String.valueOf(localDate).equals(String.valueOf(utcDate))) && (Integer.parseInt(flightHour) < 8)) {
      calendarUTCTimeZone.add(5, 1);
    }

    calendarUTCTimeZone.set(11, Integer.parseInt(flightHour));
    calendarUTCTimeZone.set(12, Integer.parseInt(flightMin));
    calendarUTCTimeZone.set(13, 0);
    Date ActualDateTime = calendarUTCTimeZone.getTime();

    return ActualDateTime;
  }

  public static int getFlyMinitesByString(String flyTime)
  {
    int minutes = 0;
    if (flyTime != null) {
      String hourS = flyTime.substring(0, 2);
      int hour = Integer.parseInt(hourS);
      String minuteS = flyTime.substring(2, 4);
      int minute = Integer.parseInt(minuteS);

      minutes = hour * 60 + minute;
    }

    return minutes;
  }

  public static Date getActualFlightDateTimeByDof(String dof, String orignalTime)
  {
    String year = "20" + dof.substring(0, 2);
    String month = dof.substring(2, 4);
    String day = dof.substring(4, 6);
    String hour = orignalTime.substring(0, 2);
    String minute = orignalTime.substring(2, 4);
    Calendar calendarUTCTimeZone = Calendar.getInstance();
    calendarUTCTimeZone.setTimeZone(utcTimeZone);
    calendarUTCTimeZone.set(1, Integer.parseInt(year));

    calendarUTCTimeZone.set(2, Integer.parseInt(month) - 1);
    calendarUTCTimeZone.set(5, Integer.parseInt(day));
    calendarUTCTimeZone.set(11, Integer.parseInt(hour));
    calendarUTCTimeZone.set(12, Integer.parseInt(minute));
    calendarUTCTimeZone.set(13, 0);

    return calendarUTCTimeZone.getTime();
  }

  public static Date getActualFlightDateTimeByNumberWithShortMonth(Date fileDate, String shortMonth, String orignalTime) {
    if (orignalTime == null) {
      return null;
    }
    String day = shortMonth.substring(0, 2);
    String monthString = shortMonth.substring(2, 5);
    int month = getMounthIntByShortMonth(monthString);
    String hour = orignalTime.substring(0, 2);
    String minute = orignalTime.substring(2, 4);

    if (orignalTime.length() == 6) {
      day = orignalTime.substring(0, 2);
      hour = orignalTime.substring(2, 4);
      minute = orignalTime.substring(4, 6);
    }
    Calendar calendarUTCTimeZone = Calendar.getInstance();
    calendarUTCTimeZone.setTimeZone(utcTimeZone);
    calendarUTCTimeZone.setTime(fileDate);
    if (calendarUTCTimeZone.get(2) > month) {
      calendarUTCTimeZone.add(1, 1);
    }

    calendarUTCTimeZone.set(2, month);
    calendarUTCTimeZone.set(5, Integer.parseInt(day));
    calendarUTCTimeZone.set(11, Integer.parseInt(hour));
    calendarUTCTimeZone.set(12, Integer.parseInt(minute));
    calendarUTCTimeZone.set(13, 0);
    return calendarUTCTimeZone.getTime();
  }

  public static int getMounthIntByShortMonth(String shortMonth)
  {
    int n = 0;
    if (shortMonth.equalsIgnoreCase("JAN"))
      n = 0;
    else if (shortMonth.equalsIgnoreCase("FEB"))
      n = 1;
    else if (shortMonth.equalsIgnoreCase("MAR"))
      n = 2;
    else if (shortMonth.equalsIgnoreCase("APR"))
      n = 3;
    else if (shortMonth.equalsIgnoreCase("MAY"))
      n = 4;
    else if (shortMonth.equalsIgnoreCase("JUN"))
      n = 5;
    else if (shortMonth.equalsIgnoreCase("JUL"))
      n = 6;
    else if (shortMonth.equalsIgnoreCase("AUG"))
      n = 7;
    else if (shortMonth.equalsIgnoreCase("SEP"))
      n = 8;
    else if (shortMonth.equalsIgnoreCase("OCT"))
      n = 9;
    else if (shortMonth.equalsIgnoreCase("NOV"))
      n = 10;
    else if (shortMonth.equalsIgnoreCase("DEC")) {
      n = 11;
    }
    return n;
  }

  public static String getShortMonthByMounthInt(String monthInt)
  {
    String month = null;
    if (monthInt.equalsIgnoreCase("01"))
      month = "JAN";
    else if (monthInt.equalsIgnoreCase("02"))
      month = "FEB";
    else if (monthInt.equalsIgnoreCase("03"))
      month = "MAR";
    else if (monthInt.equalsIgnoreCase("04"))
      month = "APR";
    else if (monthInt.equalsIgnoreCase("05"))
      month = "MAY";
    else if (monthInt.equalsIgnoreCase("06"))
      month = "JUN";
    else if (monthInt.equalsIgnoreCase("07"))
      month = "JUL";
    else if (monthInt.equalsIgnoreCase("08"))
      month = "AUG";
    else if (monthInt.equalsIgnoreCase("09"))
      month = "SEP";
    else if (monthInt.equalsIgnoreCase("10"))
      month = "OCT";
    else if (monthInt.equalsIgnoreCase("11"))
      month = "NOV";
    else if (monthInt.equalsIgnoreCase("12")) {
      month = "DEC";
    }
    return month;
  }

  public static Date dateAddDays(Date date, int days)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, days);
    return calendar.getTime();
  }
  public static boolean greatThan(Date date1,Date date2,int minutes){
	     Calendar cnow = Calendar.getInstance();
		cnow.setTime(date1);
		Calendar cnow2 = Calendar.getInstance();
		cnow2.setTime(date2);
		cnow2.add(12, minutes);
		if(cnow.compareTo(cnow2)>0){
			return true;
		}else{
			return false;
		}
	  
  }
  
  public static int getSendFlyTime(int efltFromTelx,int efltFromDB){
	  int efltSend = efltFromDB;
	  int i = efltFromDB +15;
	  if(efltFromTelx < i){
		  efltSend = efltFromTelx;
	  }
	  return efltSend;
  }
  
  public static String getQueryDateTime(String timeMark){
		String[] tm = timeMark.split(",");
		int dayint = Integer.parseInt(tm[0]);
		int hourint = Integer.parseInt(tm[1]);
		int minuteint=Integer.parseInt(tm[2]);
		int secondint=Integer.parseInt(tm[3]);
		Date now = new Date();		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(now);
	    calendar.add(calendar.DATE, dayint);
	    calendar.set(calendar.HOUR_OF_DAY, hourint);
	    calendar.set(calendar.MINUTE, minuteint);
	    calendar.set(calendar.SECOND, secondint);
	    String res = JSON.toJSONString(calendar, SerializerFeature.WriteDateUseDateFormat).replace("\"", "");
	    
	    return res;
		
	}
  


}