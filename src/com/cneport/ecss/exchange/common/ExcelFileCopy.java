package com.cneport.ecss.exchange.common;

/*
 * Copyright (c) 2011, 东方口岸科技有限公司
 * All rights reserved.
 * 文件名称：XmlFileCopy.java
 * �?   要：
 * 版本�?.0
 * �?   者：wudi
 * 创建日期�?011-12-10
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.cneport.ecss.user.User;

/**
 * @author wudi
 */

public class ExcelFileCopy {

    /** 读取配置文件 */
    public static final ResourceBundle resources = ResourceBundle
	    .getBundle("filePath");
    /** 日志 */
    private static final Logger log = Logger.getLogger(ExcelFileCopy.class);

    /**
     * @param infile
     *            报文路径
     * @param errorFlg
     *            错误类型
     * @throws IOException
     *             IO异常
     */
    public static void excelFileCopy(String infile, Workbook workBook,
	    String errorFlg, User user) {
	FileOutputStream fileWriter = null;

	String bkToPath = null;

	try {
	    bkToPath = resources.getString("backupPath").trim();

	    // 系统日时
	    String sysDate = new SimpleDateFormat("yyyyMMdd")
		    .format(new Date());

	    if ("true".equals(errorFlg)) {
		// 正常备份
		bkToPath = bkToPath + sysDate;
	    } else if ("false".equals(errorFlg)) {
		// 异常备份
		bkToPath = bkToPath + sysDate + "\\" + "error";
	    } else {
		bkToPath = bkToPath + sysDate + "\\" + "duplicateData";
	    }

	    // 备份路径
	    File bkPath = new File(bkToPath);

	    // 出力路径是否存在
	    if (!bkPath.exists()) {
		// 创建目录
		bkPath.mkdirs();
	    }
	    // 解析入力文件�?
	    String fileName = infile.substring(infile.lastIndexOf("\\") + 1);
	    fileName = new SimpleDateFormat("HHmmssSSS").format(new Date())
		    + "_" + user.getLoginName() + "_" + fileName;
	    fileWriter = new FileOutputStream(bkToPath + "\\" + fileName);
	    if (workBook != null) {
		workBook.write(fileWriter);
	    }
	    fileWriter.flush();
	    fileWriter.close();

	    if (new File(infile).isFile()) {
		// 入力文件删除
		new File(infile).delete();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
}
