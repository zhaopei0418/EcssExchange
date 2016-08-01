/*
 * Copyright (c) 2011, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：ParameterMapper.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：mayujian
 * 创建日期：Dec 15, 2011
 * 
 */

package com.cneport.tophare.para.support.jdbc.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cneport.tophare.para.ParameterEntry;

/**
 * @author mayujian
 *
 */
public interface ParameterMapper {
	/**
	 * 
	 * @param condition
	 * @return
	 */
	List<String> unitByCode(String code);
	/**
	 * 国籍
	 * @param condition
	 * @return
	 */
	List<String> countryByCode(String code);
	/**
	 * 币值
	 * @param condition
	 * @return
	 */
	List<String> currByCode(String code);
	/**
	 * 征减免税方式
	 * @param condition
	 * @return
	 */
	List<String> dutyModeByCode(String code);
	/**
	 * 运输方式代码表
	 * @param condition
	 * @return
	 */
	List<String> trafMode(String code);
	/**
	 * 监管方式代码表
	 * @param condition
	 * @return
	 */
	List<String> tradeMode(String code);
	/**
	 * 征免方式代码表
	 * @param condition
	 * @return
	 */
	List<String> cutMode(String code);
	/**
	 * 装运港代码表
	 * @param condition
	 * @return
	 */
	List<String> port(String code);
	/**
	 * 成交方式代码表
	 * @param condition
	 * @return
	 */
	List<String> transMode(String code);
	/**
	 * 包装种类代码表
	 * @param condition
	 * @return
	 */
	List<String> wrapType(String code);
	/**
	 * 用途代码表
	 * @param condition
	 * @return
	 */
	List<String> useTo(String code);
	/**
	 * 关区代码表
	 * @param condition
	 * @return
	 */
	List<String> customs(String code);
	/**
	 * 监管方式
	 * @param condition
	 * @return
	 */
	List<String> trade(String code);
	/**
	 * @param tableName
	 * @param tableCodeName
	 * @param tableCodeDescriptionName
	 * @return
	 */
	List<ParameterEntry> queryParameterEntry(@Param("tableName")String tableName, @Param("tableCodeName")String tableCodeName,@Param("tableCodeDescriptionName")String tableCodeDescriptionName);
	/**
	 * @param tableName
	 * @param tableCodeName
	 * @param tableCodeDescriptionName
	 * @return
	 */
	List<ParameterEntry> queryParameterNameByCode(@Param("tableName")String tableName, @Param("tableCodeName")String tableCodeName,@Param("tableCodeDescriptionName")String tableCodeDescriptionName,@Param("codeValue")String codeValue);
}
