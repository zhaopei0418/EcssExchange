package com.cneport.tophare.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

public class TimeUtils {

	public static void main(String args[]) {
		try {
			Date date = new Date();
			// Date转String
			System.out.println("Convert.toString: " + Convert.toString(date));
			// String转Date
			System.out.println("Convert.toDate: " + Convert.toString(Convert.toDate("20121106", TimePatterns.D8)));
			// 日期增加1天
			System.out.println("DateUtil.addDays: " + Convert.toString(DateUtils.addDays(date, -1)));
			// 日期增加33天
			System.out.println("DateUtil.addDays: " + Convert.toString(DateUtils.addDays(date, 33)));
			// 日期增加1个月
			System.out.println("DateUtil.addMonths: " + Convert.toString(DateUtils.addMonths(date, 1)));
			// 日期增加12个月
			System.out.println("DateUtil.addMonths: " + Convert.toString(DateUtils.addMonths(date, 12)));
			// 截取日期到“日”；
			System.out.println("DateUtil.truncate: " + Convert.toString(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));
			// 截取日期到“月”；
			System.out.println("DateUtil.truncate: " + Convert.toString(DateUtils.truncate(date, Calendar.MONTH)));
			// 计算两个日期之间的时间段，并格式化为String
			Date date1 = DateUtils.addDays(date, 91);
			System.out.println("DurationFormatUtils.formatPeriod: " + DurationFormatUtils.formatPeriod(date.getTime(), date1.getTime(), TimePatterns.PDAY));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成yyyyMMdd今天的日期
	 * @return
	 */
	public static Date generateToday(){
		try {
			return Convert.toDate(Convert.toString(new Date(), "yyyyMMdd"), "yyyyMMdd");
		} catch (ParseException e) {
			return null;
		}
	}
}
