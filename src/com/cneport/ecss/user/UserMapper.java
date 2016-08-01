/*
 * Copyright (c) 2013, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：UserMapper.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：wudi
 * 创建日期：2013-2-21
 * 
 */

package com.cneport.ecss.user;


/**
 * @author wudi
 * 
 */
public interface UserMapper {

    /**
     * 增加用户
     * 
     * @param user
     */
    // void addUser(User user);

    /**
     * 更新用户
     * 
     * @param user
     */
    // void updateUser(User user);

    /**
     * 查询用户
     * 
     * @param user
     * @return
     */
    User queryUser(ParameterDomain parameterDomain);

    /**
     * 删除用户
     * 
     * @param user
     */
    // void deleteUser(String userId);

    /**
     * 查询用户列表
     * 
     * @param user
     * @return
     */
    // List<User> queryUserList(User user);

    /**
     * 查询用户列表数量
     * 
     * @param user
     * @return
     */
    // int countUserList(User user);

    // void deleteAssignRoles(String userId);

    // void insertAssignRoles(UserRelUserRole userRelUserRole);

    /**
     * 根据IC卡号查询用户
     * 
     * @param icNo
     * @return
     */
    User queryUserByIcNo(String icNo);

}
