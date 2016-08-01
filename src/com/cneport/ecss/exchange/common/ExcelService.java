package com.cneport.ecss.exchange.common;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cneport.ecss.exchange.service.goods.service.GoodsService;
import com.cneport.ecss.exchange.service.order.service.OrderService;
import com.cneport.ecss.exchange.service.payInfo.service.PayInfoService;
import com.cneport.ecss.exchange.service.perDec.service.PerDecService;
import com.cneport.ecss.exchange.service.perInfo.service.PerInfoService;
import com.cneport.ecss.exchange.service.trans.service.TransService;

@Service
public class ExcelService {
    private static final Logger log = Logger.getLogger(ExcelService.class);

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

    public void impExcelFiles(MultipartFile file, Map<String, String> map)
	    throws Exception {
	log.info("getExcelFiles解析开始");

	String successFlag = "false";
	String filepath = resources.getString("inputPath").trim();
	File f = new File(filepath);
	String[] s = f.list();

	filepath = resources.getString("inputPath").trim();
	Workbook workBook = null;
	try {
	    InputStream is = file.getInputStream();
	    if (file.getOriginalFilename().toLowerCase().endsWith(".xls"))
		workBook = new HSSFWorkbook(is);
	    else if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
		workBook = new XSSFWorkbook(is);
	    }
	    if (workBook != null) {
		importExcel(workBook, map);
		successFlag = map.get("success");
	    }
	    if ("false".equals(successFlag)) {
		log.error("错误文件名:" + file.getName());
		log.error(map.get("error"));
	    }
	} catch (Exception e) {
	    log.error("==============错误开始=================");
	    log.error("错误文件名:" + file.getOriginalFilename());
	    log.error(e);
	    log.error(e.getLocalizedMessage());
	    log.error("==============错误结束=================");
	    if ("数据重复".equals(e.getLocalizedMessage())) {
		successFlag = "duplicateData";
	    } else {
		successFlag = "false";
	    }
	    System.out.println(e.getMessage() + "========================");
	    if ("无效数字".indexOf(e.getLocalizedMessage().toString()) >= 0) {
		throw new Exception("请检查表格中含有数字的列是否正确");
	    } else {
		throw e;
	    }

	} finally {
	    filepath = filepath + file.getOriginalFilename();
	    // ExcelFileCopy.excelFileCopy(filepath, workBook, successFlag);
	}
    }

    private void importExcel(Workbook workBook, Map<String, String> map)
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