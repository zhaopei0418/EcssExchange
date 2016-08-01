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

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cneport.fine.kit.exception.utils.PropertyUtils;

/**
 * 
 * 
 * @author fine
 * 
 */
public class FineInfoEnumSupport {

	/**
	 * 日志输出
	 */
	private final Log log = LogFactory.getLog(FineInfoEnumSupport.class);

	/**
	 * 格式化information填充placeholders字符数组
	 * 
	 * @param value
	 * @param placeholders
	 * @return
	 */
	public String fillFineInformation(String message, String... placeholders) {
		String result = message;
		try {
			if ((placeholders == null) || (placeholders.length == 0)) {
				return result;
			}
			Properties properties = new Properties();
			for (int i = 0; i < placeholders.length; i++) {
				properties.put((i + 1) + "", placeholders[i]);
			}
			result = PropertyUtils.resolvePlaceholders(message, properties);

		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

}