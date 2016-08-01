package com.cneport.tophare.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectUtil {
	/*
	 * 复制对象
	 */
	public final static Object cloneObject(Object obj) throws Exception {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(obj);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(
				byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		return in.readObject();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param strValue
	 * @return
	 */
	public final static boolean IsStringNullOrEmpty(Object obj) {
		if (null == obj)
			return true;
		String str;
		if (obj instanceof Date) {
			str = DateUtil.convertDateToString((Date) obj);
		} else {
			str = String.valueOf(obj);
		}

		if ("" == str || str.length() == 0 || str.trim() == "")
			return true;
		else
			return false;
	}
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public final static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
