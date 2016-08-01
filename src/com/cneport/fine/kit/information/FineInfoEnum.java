/*****************************************
 * Copyright (c) 2010 东方口岸科技有限公司(北京)
 * All rights reserved
 *
 * 文 件 名 : 
 * 摘    要 : 
 * 版    本 : 
 * 作    者 : fine
 * 创建日期 :  2012-12
 *****************************************/

package com.cneport.fine.kit.information;

/**
 * 
 * @author xionghui
 *
 */
public interface FineInfoEnum {
 
	final FineInfoEnumSupport SUPPORT = new FineInfoEnumSupport();
 
	/**
	 * 返回定义信息
	 */
	public String getInformation();
	
	/**
	 * 返回填充信息
	 */
	public String getInformation(String... placeholders);
	
	

}
