package com.cneport.tophare.util;

import java.math.BigDecimal;

public class CompareUtils {
	/**
	 * 比较两个obj 如果obj1，obj2同时为null则返回true; 如果obj1，obj2类型不同返回false；
	 * 如果obj1，obj2为String，值分别为null和空串，返回true；
	 * 如果obj1，obj2为BigDecimal，绝对值在0.0001范围内，返回true； 其他条件调用equals方法；
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static final boolean compare(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 == null && obj2 != null && String.class.equals(obj2.getClass()) && obj2.toString().equals(""))
			return true;
		if (obj2 == null && obj1 != null && String.class.equals(obj1.getClass()) && obj1.toString().equals(""))
			return true;
		if (obj2 != null && obj1 != null && String.class.equals(obj1.getClass()) && obj1.toString().trim().equals(obj2.toString().trim()))
			return true;
		if ((obj1 != null && obj2 != null) && Integer.class.equals(obj1.getClass()) && ((Integer) obj1).equals((Integer) obj2))
			return true;

		BigDecimal con = new BigDecimal(0.0001);// 差距标准
		if ((obj1 != null && obj2 != null) && BigDecimal.class.equals(obj1.getClass()) && aboutEqual(((BigDecimal) obj1), ((BigDecimal) obj2), con))
			return true;

		return false;
	}

	/**
	 * 比较两个bigDecimal相减的差的绝对值是否小于参数con；
	 * 
	 * @param a
	 * @param b
	 * @param con
	 * @return
	 */
	public static final boolean aboutEqual(BigDecimal a, BigDecimal b, BigDecimal con) {
		BigDecimal result = new BigDecimal(0);
		result = a.subtract(b).abs();// 绝对值
		return result.compareTo(con) <= 0;// 基本相等
	}
	
	/**
	 * 比较三个对象
	 * @param obj1
	 * @param obj2
	 * @param obj3
	 * @return
	 */
	public static final boolean compare(Object obj1, Object obj2, Object obj3) {
		if(compare(obj1, obj2)){
			return compare(obj1, obj3);
		} else {
			return false;
		}
	}
}
