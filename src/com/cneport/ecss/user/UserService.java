/*
 * Copyright (c) 2013, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：UserService.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：wudi
 * 创建日期：2013-2-21
 * 
 */

package com.cneport.ecss.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 * 
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // public void addUser(User user) {
    // user.setOperTime(new Date());
    // userMapper.addUser(user);
    // }

    /**
     * 更新用户
     * 
     * @param user
     */
    // public void updateUser(User user) {
    // user.setOperTime(new Date());
    // userMapper.updateUser(user);
    // }

    /**
     * 查询用户
     * 
     * @param user
     * @return
     */
    public User queryUser(ParameterDomain parameterDomain) {
	return userMapper.queryUser(parameterDomain);
    }

    /**
     * 删除用户
     * 
     * @param user
     */
    // public void deleteUser(String userId) {
    // userMapper.deleteUser(userId);
    // }

    /**
     * 查询用户列表
     * 
     * @param user
     * @return
     */
    // public List<User> queryUserList(User user) {
    // return userMapper.queryUserList(user);
    // }

    /**
     * 查询用户列表数量
     * 
     * @param user
     * @return
     */
    // public int countUserList(User user) {
    // return userMapper.countUserList(user);
    // }

    /**
     * 角色授权
     * 
     * @param role
     */
    // @Transactional
    // public void assignRoles(String userId, String roleIds) {
    // deleteAssignRoles(userId);
    // String[] idsArray = roleIds.split(",");
    // UserRelUserRole userRelUserRole = new UserRelUserRole();
    // userRelUserRole.setUserId(userId);
    // for (int i = 0; i < idsArray.length; i++) {
    // userRelUserRole.setRoleId(idsArray[i]);
    // insertAssignRoles(userRelUserRole);
    // }
    // }

    // @Transactional
    // public void deleteAssignRoles(String userId) {
    // userMapper.deleteAssignRoles(userId);
    // }

    // @Transactional
    // public void insertAssignRoles(UserRelUserRole userRelUserRole) {
    // userMapper.insertAssignRoles(userRelUserRole);
    // }

    public User queryUserByIcNo(String icNo) {
	return userMapper.queryUserByIcNo(icNo);
    }

}
