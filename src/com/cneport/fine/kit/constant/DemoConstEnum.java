/*****************************************
 * Copyright (c) 2010 东方口岸科技有限公司(北京)
 * All rights reserved
 *
 * 文 件 名 : 
 * 摘    要 : 
 * 版    本 : 
 * 作    者 : fine
 * 创建日期 :  2012-3
 *****************************************/

package com.cneport.fine.kit.constant;

/**
 * Demo模块信息枚举定义
 * 
 * @author fine
 * 
 */
public enum DemoConstEnum implements FineConstEnum {

	/**
	 * A
	 */
	DemoConstA("A"),

	/**
	 * B
	 */
	DemoConstB("B"),

	/**
	 * ""(空串)
	 */
	Default("");

	/**
	 * 常量值
	 */
	private String value;

	DemoConstEnum(String value) {
		this.value = value;
	}

	/**
	 * 返回常量值
	 * 
	 * @return
	 */
	public String getConstValue() {
		return this.value;
	}

	/**
	 * 比较字符值
	 */
	public boolean compareValue(String compare) {
		if (compare == null) {
			return false;
		}
		return this.getConstValue().equals(compare);
	}
}
