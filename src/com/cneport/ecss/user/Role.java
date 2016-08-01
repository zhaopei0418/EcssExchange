/*
 * Copyright (c) 2013, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：Role.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：wudi
 * 创建日期：2013-2-22
 * 
 */

package com.cneport.ecss.user;

import java.util.Date;
import java.util.List;

/**
 * @author wudi
 *
 */
public class Role extends ParameterDomain{
    
    private String roleId;
    private String roleCode;
    private String roleName;
    private Date operTime;
    private String operName;
    //private List<Authority> authoritys;
    private String note;
    
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getRoleCode() {
        return roleCode;
    }
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    /*public List<Authority> getAuthoritys() {
        return authoritys;
    }
    public void setAuthoritys(List<Authority> authoritys) {
        this.authoritys = authoritys;
    }*/

}
