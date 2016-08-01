/*
 * Copyright (c) 2013, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：ParameterDomain.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：wudi
 * 创建日期：2013-3-6
 * 
 */

package com.cneport.ecss.user;

import java.io.Serializable;

/**
 * @author wudi
 * 
 */
public class ParameterDomain implements Serializable {

    private int start;
    private int limit;
    private String loginName;
    private String passWord;

    public int getLimit() {
	return limit;
    }

    public void setLimit(int limit) {
	this.limit = limit;
    }

    public int getStart() {
	return start;
    }

    public void setStart(int start) {
	this.start = start;
    }

    public String getLoginName() {
	return loginName;
    }

    public void setLoginName(String loginName) {
	this.loginName = loginName;
    }

    public String getPassWord() {
	return passWord;
    }

    public void setPassWord(String passWord) {
	this.passWord = passWord;
    }

}
