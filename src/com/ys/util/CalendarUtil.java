package com.ys.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	
	Calendar calendar_instance = Calendar.getInstance();
	
	/* ���ڻ�
	 * @param date	������������:20140527 or 2014-05-27 or 2014/05/27
	 */
	public CalendarUtil(String date) {
		initDate(date);
	}
	
	public CalendarUtil() {
		initDate("");
	}
	
	private void initDate(String date) {
		//�趨һ�ܴ���һ��ʼ
		calendar_instance.setFirstDayOfWeek(2);
		
		int year;
		int month;
		int day;
		
		if (date.length() > 0) {
			if (date.length() == 8) {
				year = Integer.parseInt(date.substring(0,4));
				month = Integer.parseInt(date.substring(4,6));
				day = Integer.parseInt(date.substring(6, 8));
			} else {
				year = Integer.parseInt(date.substring(0,4));
				month = Integer.parseInt(date.substring(5,7));
				day = Integer.parseInt(date.substring(8, 10));
			}
			calendar_instance.set(year, month - 1, day);
		}
	}
	
	/**
	 * ȡ�������͵Ĵ����������
	 *
	 * @return	Date	�����͵Ĵ����������
	 */	
	public Date getDate() {
		return calendar_instance.getTime();
	}
	
	/**
	 * ����·�
	 *
	 * @return	String		�·�
	 */	
	public String getMonthOfYear() {
		return String.valueOf(calendar_instance.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * �������
	 *
	 * @return	String		����
	 */	
	public String getDayOfYear() {
		return String.valueOf(calendar_instance.get(Calendar.DAY_OF_YEAR));
	}
	
	/**
	 * �������
	 *
	 * @return	String		����
	 */	
	public String getWeekOfYear() {
		return String.valueOf(calendar_instance.get(Calendar.WEEK_OF_YEAR));
	}
    
    public static String getWeekOfSunday(){
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return CalendarUtil.fmtDate(c.getTime(), "yyyy-MM-dd");	
	}

    public static String getlastDayOfMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,1);
		c.roll(Calendar.DAY_OF_MONTH, -1);
		return CalendarUtil.fmtDate(c.getTime(), "yyyy-MM-dd");
	}

    public static String getWeekOfSunday(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return CalendarUtil.fmtDate(c.getTime(), "yyyy-MM-dd");	
	}

    public static String getlastDayOfMonth(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.roll(Calendar.DAY_OF_MONTH, -1);
		return CalendarUtil.fmtDate(c.getTime(), "yyyy-MM-dd");
	}
    
    public static String getToDay(){
		Calendar c = Calendar.getInstance();
		return CalendarUtil.fmtDate(c.getTime(), "yyyy-MM-dd");
	}
    public static String getToDay(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return CalendarUtil.fmtDate(c.getTime(), "yyyy-MM-dd");
	}
	/**
	 * ���ϵͳ����
	 *
	 * @param	
	 * @return	Date	ϵͳ����
	 */	
    public static Date getSystemDate() {
    	return Calendar.getInstance().getTime();
    }
    
	/**
	 * ���ڸ�ʽ��
	 *
	 * @param	srcDate		��������
	 * @param	format		��ʽ
	 * @return	String		��ʽ���������
	 */
	public static String fmtDate(Date srcDate, String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.format(srcDate); 
	}
	
	/**
	 * ���ڸ�ʽ��
	 *
	 * @param	srcDate		��������
	 * @param	format		��ʽ
	 * @return	String		��ʽ���������
	 */
	public static String fmtDate() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(getSystemDate()); 
	}	

	/**
	 * ���ڸ�ʽ��
	 *
	 * @param	无		systemDate
	 * @return	String		yyyy-MM-dd
	 */
	public static String fmtYmdDate() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		return sd.format(getSystemDate()); 
	}
	
	/**
	 * �����ж�
	 *
	 * @param	date		�����������
	 * @return	boolean		�ж����
	 */
	public static boolean isDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		formatter.setLenient(false);
		try {
			formatter.format(formatter.parse(date));
		} catch (Exception e) {
			return false;
		}
		  return true;
	}
	
	/**
	 * �����ͱȽϴ�С
	 *
	 * @param	date		�����������
	 * @param	date		T-n
	 * @return	boolean		�ж����
	 * @throws ParseException 
	 */
	public static int compareDate(String date1, String date2) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (date1.equals("")) {
			return 1;
		}
		if (date2.equals("")) {
			return 0;
		}
		
		Date d1 = formatter.parse(date1);
		Date d2 = formatter.parse(date2);
		if (d1.compareTo(d2) < 0) {
			//date1 < date2
			return 1;
		}

		//date1 > date2
		return 0;
	}
	
	public static int compareDate(Date d1, Date d2) throws ParseException {
		if (d1.compareTo(d2) < 0) {
			//date1 < date2
			return 1;
		}

		//date1 > date2
		return 0;
	}	
	
	public static int daysBetween(Date smdate,Date bdate) throws ParseException  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    smdate = sdf.parse(sdf.format(smdate));  
	    bdate = sdf.parse(sdf.format(bdate));  
	    Calendar cal = Calendar.getInstance();    
	    cal.setTime(smdate);    
	    long time1 = cal.getTimeInMillis();                 
	    cal.setTime(bdate);
	    long time2 = cal.getTimeInMillis();         
	    long between_days=(time2-time1)/(1000*3600*24);
	    
	    return Integer.parseInt(String.valueOf(between_days));
	}
}