package com.cneport.tophare.util;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DbCommonUtil {

	/**
	 * 获取sequence名称
	 * 
	 * @param sequenceName
	 *            sequence名称
	 * @return
	 */
	public static Integer getSequenceNextval(String sequenceName) {
		return SpringApplicationContextSupport.getDbCommonMapper()
				.getSequenceNextval(sequenceName);
	}

	/**
	 * 获取数据库当前时间
	 * 
	 * @return
	 */
	public static Date getSysdateFromDB() {
		return SpringApplicationContextSupport.getDbCommonMapper()
				.getSysdateFromDB();
	}
}
