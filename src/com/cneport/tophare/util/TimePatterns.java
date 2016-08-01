package com.cneport.tophare.util;

public class TimePatterns {
	/**
	 * yyyyMMdd
	 * 
	 * 示例： 20121105
	 */
	public static final String D8 = "yyyyMMdd";
	/**
	 * yyyyMMddHH
	 * 
	 * 示例： 2012110516
	 */
	public static final String D10 = "yyyyMMddHH";
	/**
	 * yyyyMMddHHmm
	 * 
	 * 示例： 201211051641
	 */
	public static final String D12 = "yyyyMMddHHmm";
	/**
	 * yyyyMMddHHmmss
	 * 
	 * 示例： 20121105164149
	 */
	public static final String D14 = "yyyyMMddHHmmss";
	/**
	 * yyyyMMddHHmmssSSS
	 * 
	 * 示例： 20121105164149678
	 */
	public static final String D17 = "yyyyMMddHHmmssSSS";
	/**
	 * yyyy-MM-dd
	 * 
	 * 示例： 2012-11-05
	 */
	public static final String D8SP = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * 示例： 2012-11-05 16:41:49
	 */
	public static final String D14SP = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * 示例： 2012-11-05 16:41:49.678
	 */
	public static final String D17SP = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 时间段格式：d天
	 * 
	 * 示例： 1天 20天 381天
	 */
	public static final String PDAY = "d'天'";
	/**
	 * 时间段格式：y年M个月d天
	 * 
	 * 示例： 1天3个月23天
	 */
	public static final String PYEAR_MONTH_DAY = "y'年'M'个月'd'天'";
}
