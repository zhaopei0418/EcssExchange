package com.cneport.tophare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

/**
 * 通用工具类
 * 
 * @author muguobin
 */
public class FileCommonUtil {

    /**
     * 上传文件
     * 
     * @param path
     * @param stream
     * @author muguobin 2012-12-15
     */
    public static boolean fileUp(String path, InputStream stream) {
	try {
	    OutputStream bos = new FileOutputStream(path);
	    int bytesRead = 0;
	    byte[] buffer = new byte[8192];
	    while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
		bos.write(buffer, 0, bytesRead);
	    }
	    bos.close();
	    stream.close();
	    return true;
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    return false;
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * 获取Excle文件名称 规则： 原始文件名称+以当前时间
     * 
     * @param orginalname
     *            文件命名均以这个参数开始
     * @param postfix
     *            文件格式
     * @return 文件名称
     * @author muguobin 2012-12-15
     */
    public static String getFileName(String orginalname) {
	StringBuffer sb = new StringBuffer(orginalname);
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	String mDateTime = formatter.format(cal.getTime());
	sb.insert(orginalname.indexOf("."), "[" + mDateTime + "]");
	return sb.toString();
    }

    /**
     * 检查文件夹是否存在，不存在则创建一个
     * 
     * @param path
     * @author muguobin 2012-12-15
     */
    public static void checkDir(String path) {
	File filePath = new File(path);
	if (!filePath.exists()) {
	    try {
		filePath.mkdirs();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * 下载文件
     * 
     * @return success(true) or failed(false)
     * @author muguobin 2012-12-15
     */
    public static boolean downFile(String path, HttpServletResponse response,
	    String fileName) {
	try {
	    File file = new File(path);
	    response.setCharacterEncoding("UTF-8");
	    // fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	    fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1"); // 火狐下载的文件名为中文时会乱码，按此方式转
	    response.addHeader("Content-Disposition", "attachment;filename="
		    + fileName);
	    response.setContentLength((int) file.length());
	    // response.setContentType(getContentType(fileName));
	    // response.setContentType("application/x-download");
	    response.setContentType("application/file");
	    FileInputStream fis = new FileInputStream(file);
	    OutputStream os = response.getOutputStream();
	    int bytesRead = 0;
	    byte[] buffer = new byte[1024];
	    while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
		os.write(buffer, 0, bytesRead);
	    }
	    os.close();
	    fis.close();
	} catch (FileNotFoundException e) {
	    return false;
	} catch (IOException e) {
	    return false;
	}
	return true;
    }

    /**
     * 设置文件类型
     * 
     * @param fileName
     * @return
     */
    public static String getContentType(String fileName) {
	String fineNameTmp = fileName.toLowerCase();
	String ret = "";
	if (fineNameTmp.endsWith("txt")) {
	    ret = "text/plain";
	}
	if (fineNameTmp.endsWith("gif")) {
	    ret = "image/gif";
	}
	if (fineNameTmp.endsWith("jpg") || fineNameTmp.endsWith("jpeg")
		|| fineNameTmp.endsWith("jpe")) {
	    ret = "image/jpeg";
	}
	if (fineNameTmp.endsWith("zip")) {
	    ret = "application/zip";
	}
	if (fineNameTmp.endsWith("rar")) {
	    ret = "application/rar";
	}
	if (fineNameTmp.endsWith("doc")) {
	    ret = "application/msword";
	}
	if (fineNameTmp.endsWith("ppt")) {
	    ret = "application/vnd.ms-powerpoint";
	}
	if (fineNameTmp.endsWith("html") || fineNameTmp.endsWith("htm")) {
	    ret = "text/html";
	}
	if (fineNameTmp.endsWith("tif") || fineNameTmp.endsWith("tiff")) {
	    ret = "image/tiff";
	}
	if (fineNameTmp.endsWith("pdf")) {
	    ret = "application/pdf";
	}
	if (fineNameTmp.endsWith("xls") || fineNameTmp.endsWith("xlsx")) {
	    // ret = "application/vnd.ms-excel";
	    ret = "application/x-msdownload";
	}
	return ret;
    }

}