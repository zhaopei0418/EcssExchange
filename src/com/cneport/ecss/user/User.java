/*
 * Copyright (c) 2013, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：User.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：wudi
 * 创建日期：2013-2-21
 * 
 */

package com.cneport.ecss.user;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wudi
 * 
 */
public class User extends ParameterDomain implements Serializable {

    private String userId;
    private String loginName;
    private String userName;
    private String orgId;
    private String userType;
    private String passWord;
    private String officePhone;
    private String mobilePhone;
    private String email;
    private String status;
    private Date operTime;
    private String operName;
    private String icNo;
    private Date startDate;
    private Date endDate;
    private String note;
    
	private List<Role> roles = new ArrayList<Role>();
    // private Org org;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    @Override
    public String getLoginName() {
	return loginName;
    }

    @Override
    public void setLoginName(String loginName) {
	this.loginName = loginName;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getOrgId() {
	return orgId;
    }

    public void setOrgId(String orgId) {
	this.orgId = orgId;
    }

    public String getUserType() {
	return userType;
    }

    public void setUserType(String userType) {
	this.userType = userType;
    }

    @Override
    public String getPassWord() {
	return passWord;
    }

    @Override
    public void setPassWord(String passWord) {
	this.passWord = passWord;
    }

    public String getOfficePhone() {
	return officePhone;
    }

    public void setOfficePhone(String officePhone) {
	this.officePhone = officePhone;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public Date getOperTime() {
	return operTime;
    }

    public void setOperTime(Date operTime) {
	this.operTime = operTime;
    }

    public String getOperName() {
	return operName;
    }

    public void setOperName(String operName) {
	this.operName = operName;
    }

    public String getIcNo() {
	return icNo;
    }

    public void setIcNo(String icNo) {
	this.icNo = icNo;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

    public List<Role> getRoles() {
    	 return roles;
    }
    
    public void setRoles(List<Role> roles) {
    	this.roles = roles;
    }
    
    // public Org getOrg() {
    // return org;
    // }
    //
    // public void setOrg(Org org) {
    // this.org = org;
    // }

    /**
     * 入驻企业与运营企业的查询权限控制规则
     * 
     * @param condition
     * @throws Exception
     */
    public void setUserCode(Object condition) throws Exception {
	Method setDeclareCodeMethod = condition.getClass().getDeclaredMethod(
		"setDeclareCode", String.class);
	Method setMasterEntCodeMethod = condition.getClass().getDeclaredMethod(
		"setMasterEntCode", String.class);

	// 入驻企业用户查询时，用户企业编码和单证上申报单位海关编码匹配
	// // if
	// (GlobalConstEnum.ENT_TYPE_PRODUCE.compareValue(this.getOrg().getEntType()))
	// {
	// setDeclareCodeMethod.invoke(condition, this.getOrg().getEntCode());
	// }
	// // 运营企业用户查询时，用户企业编码和单证上运营企业代码匹配
	// else if
	// (GlobalConstEnum.ENT_TYPE_MANAGE.compareValue(this.getOrg().getEntType()))
	// {
	// setMasterEntCodeMethod.invoke(condition, this.getOrg().getEntCode());
	// } else {
	// //throw new Exception("企业类型非法");
	// }
    }

    /**
     * 单据上“企业编号”、“主管海关编码”字段的写入规则
     * 
     * @param condition
     * @throws Exception
     */
    public void setMasterEntInfo(Object billHeadObject) throws Exception {
	// Method setMasterEntCodeMethod =
	// billHeadObject.getClass().getDeclaredMethod("setCompanyCode",
	// String.class);
	Method setMasterEntNameMethod = billHeadObject.getClass()
		.getDeclaredMethod("setCustomsCode", String.class);

	// if (billHeadObject != null && org != null) {
	// // setMasterEntCodeMethod.invoke(billHeadObject,
	// // org.getMasterEntCode());
	// setMasterEntNameMethod.invoke(billHeadObject,
	// org.getMasterCustomsCode());
	// }
    }
}
