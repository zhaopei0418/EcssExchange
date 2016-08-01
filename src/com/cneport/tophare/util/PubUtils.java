package com.cneport.tophare.util;

import java.math.BigDecimal;
import java.util.UUID;



/**
 * 公共功能
 */
public class PubUtils {
	
	public static final String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 数据求和 
	 * @param x
	 * @param y
	 */
	public static final BigDecimal addData(BigDecimal x, BigDecimal y){
		if(x == null){
			return y;
		}
		if(y == null){
			return x;
		}
		return x.add(y);
	}
	
	public static final String wrapCompare(boolean isEqual){
		if(isEqual){
			return "一致";
		} else {
			return "不一致";
		}
	}
	
	public static final String convertNull(Object obj){
		if(null == obj){
			return "";
		} else {
			return obj.toString();
		}
	}
}