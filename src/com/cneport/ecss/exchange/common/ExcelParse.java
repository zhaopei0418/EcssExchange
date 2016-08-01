package com.cneport.ecss.exchange.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cneport.ecss.exchange.service.goods.service.GoodsService;
import com.cneport.ecss.exchange.service.order.service.OrderService;
import com.cneport.ecss.exchange.service.payInfo.service.PayInfoService;
import com.cneport.ecss.exchange.service.perDec.service.PerDecService;
import com.cneport.ecss.exchange.service.perInfo.service.PerInfoService;
import com.cneport.ecss.exchange.service.trans.service.TransService;

@Service
public class ExcelParse {
    private static final Logger log = Logger.getLogger(ExcelParse.class);

    public static final ResourceBundle resources = ResourceBundle
	    .getBundle("filePath");

    @Autowired
    private OrderService orderService;

    @Autowired
    private PerDecService perDecService;

    @Autowired
    private TransService transService;

    @Autowired
    private PayInfoService payInfoService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PerInfoService perInfoService;

    public void getExcelFiles() throws Exception {
	log.info("getExcelFiles解析开始");

	Map<String, String> map = new HashMap();

	String successFlag = "false";
	String filepath = resources.getString("inputPath").trim();
	File f = new File(filepath);
	String[] s = f.list();
	if (s.length > 0)
	    for (int i = 0; i < s.length; i++) {
		map.put("success", "true");
		map.put("error", "");
		filepath = resources.getString("inputPath").trim();
		filepath = filepath + s[i];
		Workbook workBook = null;
		InputStream is = null;
		try {
		    File excelFile = new File(filepath);
		    is = new FileInputStream(excelFile);
		    if (excelFile.getName().toLowerCase().endsWith(".xls"))
			workBook = new HSSFWorkbook(is);
		    else if (excelFile.getName().toLowerCase()
			    .endsWith(".xlsx")) {
			workBook = new XSSFWorkbook(is);
		    }
		    if (workBook != null) {
			importExcel(workBook, map);
			successFlag = map.get("success");
		    }

		} catch (Exception e) {
		    log.error("==============错误开始=================");
		    log.error("错误文件名:" + s[i]);
		    log.error(e);
		    log.error(e.getLocalizedMessage());
		    log.error("==============错误结束=================");
		    System.gc();
		    if ("数据重复".equals(e.getLocalizedMessage())) {
			successFlag = "duplicateData";
		    } else {
			successFlag = "false";
		    }
		} finally {
		    // ExcelFileCopy.excelFileCopy(filepath,workBook,successFlag);
		}
	    }
    }

    public void importExcel(Workbook workBook, Map<String, String> map)
	    throws Exception {

	String inputOrder = resources.getString("inputOrder").trim();
	String inputPerDec = resources.getString("inputPerDec").trim();
	String inputTrans = resources.getString("inputTrans").trim();
	String inputPayInfo = resources.getString("inputPayInfo").trim();
	String inputGoods = resources.getString("inputGoods").trim();
	String inputPerInfo = resources.getString("inputPerInfo").trim();

	for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
	    Sheet sheet = workBook.getSheetAt(numSheet);
	    if ((sheet.getSheetName().toLowerCase().indexOf("order") >= 0)
		    && ("true".equals(inputOrder)))
		this.orderService.impOrder(sheet, map);
	    else if ((sheet.getSheetName().toLowerCase()   
		    .indexOf("personaldeclare") >= 0)
		    && ("true".equals(inputPerDec)))
		this.perDecService.impPerDec(sheet, map);
	    else if ((sheet.getSheetName().toLowerCase().indexOf("trans") >= 0)
		    && ("true".equals(inputTrans)))
		this.transService.impTrans(sheet, map);
	    else if ((sheet.getSheetName().toLowerCase().indexOf("payinfo") >= 0)
		    && ("true".equals(inputPayInfo)))
		this.payInfoService.impPayInfo(sheet, map);
	    else if ((sheet.getSheetName().toLowerCase().indexOf("goods") >= 0)
		    && ("true".equals(inputGoods)))
		this.goodsService.impGoods(sheet, map);
	    else if ((sheet.getSheetName().toLowerCase().indexOf("perinfo") >= 0)
		    && ("true".equals(inputPerInfo)))
		this.perInfoService.impPerInfo(sheet, map);
	}
    }
}