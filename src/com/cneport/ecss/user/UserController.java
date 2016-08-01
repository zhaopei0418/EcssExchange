/*
 * Copyright (c) 2013, 东方口岸科技有限公司
 * All rights reserved.
 * 
 * 文件名称：UserController.java
 * 摘    要：
 * 
 * 版本：1.0
 * 作    者：wudi
 * 创建日期：2013-2-21
 * 
 */

package com.cneport.ecss.user;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cneport.fine.kit.exception.FineException;
import com.cneport.tophare.util.MD5;
import com.cneport.tophare.web.controller.ResponseDataHolder;

/**
 * @author wudi
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log Log = LogFactory.getLog(UserController.class);

    public static final ResourceBundle resources = ResourceBundle
	    .getBundle("filePath");

    @Autowired
    private UserService userService;

    /** subSysInfoParser 获取所有的资源类 */
    // @Autowired
    // private SubSysInfoParser subSysInfoParser;

    /** menuFilter 菜单过滤类 */
    // @Autowired
    // private AuthorityMenuFilter menuFilter;
    // /** menuConverter 将菜单转换为json字符串 */
    // @Autowired
    // private ExtJsonMenuConverter menuConverter;
    //
    // private final ConfigUrl configUrl = null;

    // @RequestMapping(value = "/addUser")
    // public @ResponseBody
    // Map<String, Object> addUser(@ModelAttribute("user") User user,
    // HttpServletResponse response) {
    // ResponseDataHolder responseDataHolder = new ResponseDataHolder();
    // try {
    // user.setPassWord(MD5.getCryptUserPwd(user.getLoginName(),
    // user.getLoginName()));
    // userService.addUser(user);
    // responseDataHolder.put("success", true);
    // } catch (Exception e) {
    // responseDataHolder.put("success", false);
    // responseDataHolder.setError(FineException.getInstance(e)
    // .getErrorName());
    // }
    // return responseDataHolder;
    // }

    // @RequestMapping(value = "/updateUser")
    // public @ResponseBody
    // Map<String, Object> updateUser(@ModelAttribute("user") User user,
    // HttpServletRequest request, HttpServletResponse response) {
    // ResponseDataHolder responseDataHolder = new ResponseDataHolder();
    // try {
    // User sessionUser = (User) request.getSession().getAttribute("user");
    // user.setOperName(sessionUser.getUserName());
    // userService.updateUser(user);
    // responseDataHolder.put("success", true);
    // } catch (Exception e) {
    // responseDataHolder.put("succexss", false);
    // responseDataHolder.setError(FineException.getInstance(e)
    // .getErrorName());
    // }
    // return responseDataHolder;
    // }

    // @Transactional
    // @RequestMapping(value = "/deleteUser")
    // public @ResponseBody
    // Map<String, Object> deleteUser(@RequestParam("userId") String userId,
    // HttpServletResponse response) {
    // ResponseDataHolder responseDataHolder = new ResponseDataHolder();
    // try {
    // userService.deleteUser(userId);
    // userService.deleteAssignRoles(userId);
    // responseDataHolder.put("success", true);
    // } catch (Exception e) {
    // responseDataHolder.put("success", false);
    // responseDataHolder.setError(FineException.getInstance(e)
    // .getErrorName());
    // }
    // return responseDataHolder;
    // }

    // @RequestMapping(value = "/queryUserList")
    // public @ResponseBody
    // Map<String, Object> queryUserList(@ModelAttribute("user") User user,
    // HttpServletResponse response) {
    // ResponseDataHolder responseDataHolder = new ResponseDataHolder();
    // List<User> userList = new ArrayList<User>();
    // try {
    // userList = userService.queryUserList(user);
    // responseDataHolder.put("result", userList);
    // responseDataHolder.put("totalProperty",
    // userService.countUserList(user));
    // responseDataHolder.put("success", true);
    // } catch (Exception e) {
    // responseDataHolder.put("success", false);
    // responseDataHolder.setError(FineException.getInstance(e)
    // .getErrorName());
    // }
    // return responseDataHolder;
    // }

    @RequestMapping(value = "/login")
    public @ResponseBody
    Map<String, Object> userLogin(HttpServletRequest request,
	    HttpServletResponse response) {
	ResponseDataHolder responseDataHolder = new ResponseDataHolder();
	boolean isExit = true;
	try {
	    ParameterDomain parameterDomain = new ParameterDomain();
	    parameterDomain.setLoginName(request.getParameter("loginName"));
	    parameterDomain.setPassWord(MD5.getCryptUserPwd(
		    parameterDomain.getLoginName(),
		    request.getParameter("passWord")));
	    User user = userService.queryUser(parameterDomain);
	    
	    if (user == null) {
			responseDataHolder.put("success", false);
			responseDataHolder.put("info", "用户名密码错误或用户过期");
	    } else {
	    	
	    	for (Role r : user.getRoles()) {
	    	    Log.info("=== userLogin == roleId = [" + r.getRoleId() + " ]"
	    		    + " roleName = [" + r.getRoleName() + "]"
	    		    + " roleCode = [" + r.getRoleCode() + "]");
	    	    
		    	if (UserConstant.IMPORT_ROLE_CODE.equals(r.getRoleCode())) {
		    		isExit = false;
		    		break;
		    	}
		    }
		    
		    if (isExit) {
		    	responseDataHolder.put("success", false);
				responseDataHolder.put("info", "没有导入权限");
				return responseDataHolder;
		    }
	    	
			request.getSession().setAttribute("user", user);
			responseDataHolder.put("result", user);
			responseDataHolder.put("success", true);
	    }
	} catch (Exception e) {
	    responseDataHolder.put("success", false);
	    responseDataHolder.setError(FineException.getInstance(e)
		    .getErrorName());
	}
	return responseDataHolder;
    }

    @RequestMapping(value = "/loginByIcNo")
    public @ResponseBody
    Map<String, Object> queryUserByIcNo(HttpServletRequest request,
	    HttpServletResponse response) {
	ResponseDataHolder responseDataHolder = new ResponseDataHolder();
	boolean isExit = true;
	
	try {
	    String icNo = request.getParameter("icNo");
	    User user = userService.queryUserByIcNo(icNo);
	    
	    
	    if (user == null) {
			responseDataHolder.put("success", false);
			responseDataHolder.put("info", "无关联卡号的用户");
	    } else {
	    	
	    	for (Role r : user.getRoles()) {
	    	    Log.info("=== queryUserByIcNo == roleId = [" + r.getRoleId() + " ]"
	    		    + " roleName = [" + r.getRoleName() + "]"
	    		    + " roleCode = [" + r.getRoleCode() + "]");
	    	    
		    	if (UserConstant.IMPORT_ROLE_CODE.equals(r.getRoleCode())) {
		    		isExit = false;
		    		break;
		    	}
		    }
		    
		    if (isExit) {
		    	responseDataHolder.put("success", false);
				responseDataHolder.put("info", "没有导入权限");
				return responseDataHolder;
		    }
	    	
			request.getSession().setAttribute("user", user);
			responseDataHolder.put("result", user);
			responseDataHolder.put("success", true);
	    }
	} catch (Exception e) {
	    responseDataHolder.put("success", false);
	    responseDataHolder.put("info", FineException.getInstance(e)
		    .getErrorName()
		    + "\n"
		    + FineException.getInstance(e).getErrorMessage());
	}
	return responseDataHolder;
    }

    // @Transactional
    // @RequestMapping(value = "/assignRoles")
    // public @ResponseBody
    // Map<String, Object> assignRoles(HttpServletRequest request,
    // HttpServletResponse response) {
    // ResponseDataHolder responseDataHolder = new ResponseDataHolder();
    // try {
    // String userId = request.getParameter("userId");
    // String roleIds = request.getParameter("roleIds");
    // userService.assignRoles(userId, roleIds);
    // responseDataHolder.put("success", true);
    // } catch (Exception e) {
    // responseDataHolder.put("success", false);
    // responseDataHolder.setError(FineException.getInstance(e)
    // .getErrorName());
    // }
    // return responseDataHolder;
    // }

    // @RequestMapping(value = "/getMenu")
    // public void getMenu(HttpServletRequest request, HttpServletResponse
    // response) {
    // List<SubSysInfo> infolist = GlobalContext.SUBSYS_LIST;
    // List<MenuNode> menu = null;
    // String records = "{success:true}";
    // User userBean = (User) request.getSession().getAttribute(USER);
    // if (infolist.get(0).getId().equals("ECSS")) {
    // menu = infolist.get(0).getMenu();
    // if (this.menuFilter != null) {
    // menu = this.menuFilter.filter(menu, userBean);
    // }
    // records = this.menuConverter.convert(menu);
    // }
    //
    // // //加载菜单
    // // List<MenuNode> menuList = new ArrayList<MenuNode>();
    // // if (infolist.get(0).getId().equals("Syis")) {
    // // menuList = infolist.get(0).getMenu();
    // // if (this.menuFilter != null) {
    // // menuList = this.menuFilter.filter(menuList, userBean);
    // // infolist.get(0).setMenu(menuList);
    // // }
    // // }
    // // GlobalContext.SUBSYS_LIST = infolist;
    //
    // try {
    // response.setContentType("text/json;charset=utf-8;");
    // response.getWriter().write(records.toString());
    // response.getWriter().flush();
    // response.getWriter().close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    //
    /**
     * 加载用户信息
     * 
     * @param request
     *            户端的请求
     * @param response
     *            响应
     * @return ModelAndView 处理
     */
    @RequestMapping(value = "/changePassword")
    public @ResponseBody
    Map<String, Object> changePassword(HttpServletRequest request,
	    HttpServletResponse response) {
	ResponseDataHolder responseDataHolder = new ResponseDataHolder();
	try {
	    User sessionUser = (User) request.getSession().getAttribute("user");
	    String newPassword = request.getParameter("newPassword");
	    String icNo = sessionUser.getIcNo();
	    String path = resources.getString("passwordPath");
	    File bkPath = new File(path);

	    if (!bkPath.exists()) {
		bkPath.mkdirs();
	    }
	    PrintWriter out = new PrintWriter(new FileWriter(path
		    + File.separator + "password.txt", true));

	    out.println(icNo + "=" + newPassword);
	    out.close();

	    // ParameterDomain parameterDomain = new ParameterDomain();
	    // parameterDomain.setLoginName(sessionUser.getLoginName());
	    // parameterDomain.setPassWord(MD5.getCryptUserPwd(
	    // parameterDomain.getLoginName(),
	    // request.getParameter("oldPassword")));
	    // User user = userService.queryUser(parameterDomain);
	    // if (user != null) {
	    // user.setPassWord(MD5.getCryptUserPwd(user.getLoginName(),
	    // request.getParameter("oldPassword")));
	    // // userService.updateUser(user);
	    responseDataHolder.put("success", true);
	    responseDataHolder.put("info", "修改成功");
	    // } else {
	    // responseDataHolder.put("success", false);
	    // responseDataHolder.put("info", "原始密码错误");
	    // }

	} catch (Exception e) {
	    responseDataHolder.put("success", false);
	    responseDataHolder.setError(FineException.getInstance(e)
		    .getErrorName());
	}
	// return responseDataHolder;
	return null;
    }

    public static void main(String[] args) {
	System.out.println(MD5.getCryptUserPwd("ecss", "ecss"));
	System.out.println(MD5.getCryptUserPwd("admin", "admin"));
	System.out.println(MD5.getCryptUserPwd("ent", "ent"));
	System.out.println(MD5.getCryptUserPwd("cus", "cus"));
    }
}
