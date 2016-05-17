package com.du.wx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {
	
	private static String formmatStr = "yyyy-MM-dd HH:mm:ss" ;
	
	public static Date stringToDate(String datetime){
		SimpleDateFormat dateFormat = new SimpleDateFormat(formmatStr) ;
		
		Date date = null ;
		try {
			date = dateFormat.parse(datetime) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date ;
	}
	
	public static String dateToStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat(formmatStr) ;
		
		String datetime = null ;
		datetime = format.format(date) ;
		
		return datetime ;
	}
	
	/**
	 * 最近days天的判断
	 * @param date 要判断的date
	 * @param days	距离今天的天数
	 * @return
	 */
	public static boolean isDaysInOfToday(Date date,int days){
		boolean flag = false ;
		//今天
		Calendar nowcalendar =  Calendar.getInstance() ;
		//比较的天
		Calendar compaCalendar = Calendar.getInstance();
		compaCalendar.setTime(date);
		
		if(nowcalendar.get(Calendar.YEAR)==compaCalendar.get(Calendar.YEAR)
				&&(nowcalendar.get(Calendar.DAY_OF_YEAR)-compaCalendar.get(Calendar.DAY_OF_YEAR))<=days){
			flag = true ;
		}
		return flag ;
	}
		/**
		 *days天后的判断
		 * @param date 要判断的date
		 * @param days	距离今天的天数
		 * @return
		 */
		public static boolean isDaysOutOfToday(Date date,int days){
			boolean flag = false ;
			//今天
			Calendar nowcalendar =  Calendar.getInstance() ;
			//比较的天
			Calendar compaCalendar = Calendar.getInstance();
			compaCalendar.setTime(date);
			
			if(nowcalendar.get(Calendar.YEAR)==compaCalendar.get(Calendar.YEAR)
					&&(compaCalendar.get(Calendar.DAY_OF_YEAR)-nowcalendar.get(Calendar.DAY_OF_YEAR))>=days){
				flag = true ;
			}
		
		return flag ;
	}
	
	public static void main(String[] args){
//		Date date = new Date() ;
//		System.out.println(StringUtil.dateToStr(date)) ;
//		System.err.println(StringUtil.class.getSimpleName());
		String datetime = "2016-05-13 18:00:01" ;
		System.out.println(StringUtil.stringToDate(datetime));
//		System.out.println(StringUtil.isDaysOutOfToday(StringUtil.stringToDate("2016-05-10 12:00:21"), 3)) ;
	}

}
