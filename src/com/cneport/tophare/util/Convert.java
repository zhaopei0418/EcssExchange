package com.cneport.tophare.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * 类型转换工具类
 * 
 * @author liuhongyang
 * 
 */
public class Convert {

	/**
	 * Integer 转换成BigDecimal：如果integer==null,返回null;否则返回转换后的BigDecimal.
	 * 
	 * @param integer
	 * @return
	 */
	public static final BigDecimal toBigDecimal(Integer integer) {
		return (integer == null ? null : BigDecimal.valueOf(integer));
	}

	/**
	 * Long 转换成BigDecimal：如果Long==null,返回null;否则返回转换后的BigDecimal.
	 * 
	 * @param Long
	 * @return
	 */
	public static final BigDecimal toBigDecimal(Long para) {
		return (para == null ? null : BigDecimal.valueOf(para));
	}

	/**
	 * 将bigDecimal转换为BigInteger类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static final BigInteger toBigInteger(BigDecimal bigDecimal) {
		Integer intTemp = toInteger(bigDecimal);
		BigInteger bigIntegerTemp = toBigInteger(intTemp);
		return bigIntegerTemp;
	}

	/**
	 * Integer 转换成BigInteger：如果integer==null,返回null;否则返回转换后的BigInteger.
	 * 
	 * @param integer
	 * @return
	 */
	public static final BigInteger toBigInteger(Integer integer) {
		return (integer == null ? null : BigInteger.valueOf(integer));
	}

	/**
	 * 按照默认格式yyyyMMddHHmmss转换String到Date类型
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String str) throws ParseException {
		return toDate(str, TimePatterns.D14);
	}

	/**
	 * 按照datePattern格式转换String到Date类型
	 * 
	 * @param str
	 * @param datePattern
	 * @see TimePattens
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String str, String datePattern) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		if (datePattern != null && !datePattern.equals("") && str != null && !str.equals("")) {
			df = new SimpleDateFormat(datePattern);
			date = df.parse(str);
		}
		return date;
	}

	/**
	 * 从XMLGregorianCalendar实例中取得Date
	 * 
	 * @param xgc
	 * @return
	 */
	public static Date toDate(XMLGregorianCalendar xgc) {
		if (xgc == null) {
			return null;
		}
		return xgc.toGregorianCalendar().getTime();
	}

	/**
	 * 字符串转换为BigDecimal，转换失败返回null.
	 * 
	 * @param num
	 * @return
	 */
	public static final BigDecimal toDecimal(String num) {
		BigDecimal bigDecimal = null;
		try {
			bigDecimal = new BigDecimal(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bigDecimal;
	}

//	/**
//	 * 获取GregorianCalendar实例，并设置其Time字段为参数d
//	 * 
//	 * @param d
//	 * @return 如果d为null，则返回null
//	 */
//	public static GregorianCalendar toGregorianCalendar(Date d) {
//		if (d == null) {
//			return null;
//		}
//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//		gregorianCalendar.setTime(d);
//		return gregorianCalendar; 
//	}

	/**
	 * 取bigDecimal的intValue，如果bigDecimal为null，则返回null；
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static final Integer toInteger(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return null;
		}
		return bigDecimal.intValue();
	}

	/**
	 * BigInteger 转换成Integer：如果bigInteger==null,返回null;否则返回转换后的Integer.
	 * 
	 * @param bigInteger
	 * @return
	 */
	public static final Integer toInteger(BigInteger bigInteger) {
		return (bigInteger == null ? null : bigInteger.intValue());
	}

	/**
	 * 取Long实例value的intValue，如果value为null，则返回null；
	 * 
	 * @param para
	 * @return
	 */
	public static final Integer toInteger(Long value) {
		return (value == null ? null : value.intValue());
	}

	/**
	 * 字符串转换为Integer，转换失败返回null.
	 * 
	 * @param str
	 * @return
	 */
	public static final Integer toInteger(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将bigDecimal转换为Long
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static final Long toLong(BigDecimal bigDecimal) {
		String strTemp = toString(bigDecimal);
		Long longTemp = toLong(strTemp);
		return longTemp;
	}

	/**
	 * 将String转换为long，如果截获异常返回null
	 * 
	 * @param str
	 * @return
	 */
	public static final Long toLong(String str) {
		Long longTemp = null;
		try {
			longTemp = new Long(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return longTemp;
	}

	/**
	 * BigDecimal转换为String,如果bigDecimal==null则返回null
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static final String toString(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return null;
		}
		return bigDecimal.toString();
	}

	/**
	 * 按照默认格式转换Date类型到String
	 * 
	 * @param date
	 * @param pattern
	 * @see TimePatterns
	 * @return
	 */
	public static String toString(Date date) {
		return toString(date, TimePatterns.D14);
	}

	/**
	 * 按照pattern格式转换Date类型到String
	 * 
	 * @param date
	 * @param pattern
	 * @see TimePatterns
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (date != null && pattern != null && !pattern.equals("")) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 将integer转为String类型，如果integer为null，则返回null
	 * 
	 * @param integer
	 * @return
	 */
	public static final String toString(Integer integer) {
		if (integer == null) {
			return null;
		}
		return integer.toString();
	}

	/**
	 * Short转换为String,如果value为null，则返回null
	 * 
	 * @param value
	 * @return
	 */
	public static final String toString(Short value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

//	/**
//	 * 获取XMLGregorianCalendar实例，并设置其Time字段为参数d;
//	 * 
//	 * @param d
//	 * @return 如果d为null，则返回null
//	 */
//	public static XMLGregorianCalendar toXMLGregorianCalendar(Date d) {
//		if (d == null) {
//			return null;
//		}
//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//		gregorianCalendar.setTime(d);
//		return new XMLGregorianCalendarImpl(gregorianCalendar);
//	}
//
//	/**
//	 * 将XMLGregorianCalendar类型按默认格式（TimePatterns.D14）转换为字符串
//	 * 
//	 * @see TimePatterns
//	 * @param calender
//	 * @param pattern
//	 * @return
//	 */
//	public static String toString(XMLGregorianCalendar calender) {
//		return toString(toDate(calender), TimePatterns.D14);
//	} 
//
//	/**
//	 * 将XMLGregorianCalendar类型按pattern格式转换为字符串
//	 * 
//	 * @param calender
//	 * @param pattern
//	 * @return
//	 */
//	public static String toString(XMLGregorianCalendar calender, String pattern) {
//		return toString(toDate(calender), pattern);
//	}
}
