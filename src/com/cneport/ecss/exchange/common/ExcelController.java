package com.cneport.ecss.exchange.common;

import it.sauronsoftware.base64.Base64;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.cneport.ecss.exchange.service.goods.service.GoodsService;
import com.cneport.ecss.exchange.service.logisticsStatus.service.LogisticsStatusService;
import com.cneport.ecss.exchange.service.order.service.OrderService;
import com.cneport.ecss.exchange.service.payInfo.service.PayInfoService;
import com.cneport.ecss.exchange.service.perDec.service.PerDecService;
import com.cneport.ecss.exchange.service.perInfo.service.PerInfoService;
import com.cneport.ecss.exchange.service.trans.service.TransService;
import com.cneport.ecss.user.User;
import com.cneport.tophare.util.FileCommonUtil;
import com.cneport.tophare.web.controller.ResponseDataHolder;

@Controller
@RequestMapping("/upload")
public class ExcelController {

    private static final Logger log = Logger.getLogger(ExcelController.class);

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

    @Autowired
    private LogisticsStatusService logisticsStatusService;

    @RequestMapping(value = "/excel")
    public @ResponseBody
    Map<String, Object> impExcel(HttpServletResponse response,
	    HttpServletRequest request, @RequestParam("file") MultipartFile file) {
	Map<String, Object> responseMap = new HashMap<String, Object>();
	String successFlag = "false";
	Map<String, String> map = new HashMap<String, String>();
	String filepath = null;
	Workbook workBook = null;
	ResponseObject resObj = new ResponseObject();
	String fileName = file.getOriginalFilename();
	String cardId = request.getParameter("cardId");
	User user = (User) request.getSession().getAttribute("user");
	String icNo = user.getIcNo();
	String sum = fileName + icNo + cardId;
	
	
	try {
		String signature = new String(Base64.encode(sum.getBytes("utf-8")));
		String signatureAfter = URLDecoder.decode(request.getParameter("cipherText"), "iso-8859-1");
		
		log.info("filename == [" + fileName + "]");
		log.info("cardId ==" + cardId);
		log.info("icNo ==" + icNo);
		log.info("cipherText ==" + signatureAfter);
		log.info("signature ==" + signature);
		log.info(signature.length() + " sdfsdf " + signatureAfter.length());
		log.info("signatureAfter.equals(signature) == " + signature.equals(signatureAfter));
		
	    filepath = resources.getString("inputPath").trim();
	    InputStream is = file.getInputStream();
	    if (file.getOriginalFilename().toLowerCase().endsWith(".xls")) {
		workBook = new HSSFWorkbook(is);
	    } else if (file.getOriginalFilename().toLowerCase()
		    .endsWith(".xlsx")) {
		workBook = new XSSFWorkbook(is);
	    } else {
		// map.put("error", "文件类型不符合（必须为xls或xlsx）！");
		throw new Exception("文件类型不符合（必须为xls或xlsx）！");
	    }
	    
	    if(!signature.equals(signatureAfter))
		{
			throw new Exception("文件不是按正确方式上传的");
		}
	    
	    if (workBook != null) {
		importExcel(workBook, map,user);
		successFlag = map.get("success");
	    }
	    responseMap.put("success", true);
	    responseMap.put("error", map.get("error"));

	    resObj.setError(map.get("error"));
	    resObj.setSuccess(true);
	    // 新增 记录上传成功的记录

	    String message = new StringBuffer(user.getIcNo() == null ? "--"
		    : user.getIcNo())
		    .append("|")
		    .append(user.getLoginName())
		    .append("|")
		    .append(file.getOriginalFilename())
		    .append("|")
		    .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			    .format(new Date())).toString();
	    // String path = request.getSession().getServletContext()
	    // .getRealPath("commons" + File.separator + "record.txt");
	    String path = resources.getString("recordPath");
	    File bkPath = new File(path + File.separator
		    + new SimpleDateFormat("yyyyMMdd").format(new Date()));

	    if (!bkPath.exists()) {
		bkPath.mkdirs();
	    }
	    PrintWriter out = new PrintWriter(new FileWriter(bkPath
		    + File.separator + "record.txt", true));

	    out.println(message);
	    out.close();

	} catch (Exception e) {
	    map.put("error", (null == map.get("error") ? "" : map.get("error"))
	    	+ "error--------------------->" + e.getMessage());
	    responseMap.put("success", false);
	    responseMap.put("error", map.get("error"));

	    resObj.setSuccess(false);
	    resObj.setError(map.get("error"));

	    /*
	     * if(e.getLocalizedMessage().indexOf("无效数字") >- 1){
	     * responseMap.put("error", map.get("error") +
	     * "请检查表格中的价格数量等字段是否正确"); resObj.setError(map.get("error") +
	     * ",请检查表格中的价格数量等字段是否正确"); }
	     */
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
	} finally {
	    filepath = filepath + file.getOriginalFilename();
	    ExcelFileCopy.excelFileCopy(filepath, workBook, successFlag, user);
	}
	response.setCharacterEncoding("UTF-8");
	response.setContentType("application/json;charset=utf-8");

	try {
	    response.getWriter().write(JSON.toJSONString(resObj));
	    log.debug(JSON.toJSONString(resObj));
	} catch (IOException e) {
	    log.error(e);
	}
	return null;
    }

    private void importExcel(Workbook workBook, Map<String, String> map,User user)
	    throws Exception {
	String inputOrder = resources.getString("inputOrder").trim();
	String inputTrans = resources.getString("inputTrans").trim();
	String inputGoods = resources.getString("inputGoods").trim();
	String inputCleared = resources.getString("inputCleared").trim();
	// String inputPerDec = resources.getString("inputPerDec").trim();
	// String inputPayInfo = resources.getString("inputPayInfo").trim();
	// String inputPerInfo = resources.getString("inputPerInfo").trim();
	boolean isImport = false;
	for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
	    Sheet sheet = workBook.getSheetAt(numSheet);
	    if ((sheet.getSheetName().toLowerCase().equals("order"))
		    && ("true".equals(inputOrder))) {
		orderService.impOrder(sheet, map);
		isImport = true;
	    } else if ((sheet.getSheetName().toLowerCase().equals("trans"))
		    && ("true".equals(inputTrans))) {
		transService.impTrans(sheet, map);
		isImport = true;
	    } else if ((sheet.getSheetName().toLowerCase().equals("goods"))
		    && ("true".equals(inputGoods))) {
		goodsService.impGoods(sheet, map);
		isImport = true;
	    } else if ((sheet.getSheetName().toLowerCase().equals("transbatch"))
		    && ("true".equals(inputTrans))) {
		transService.impTransBatch(sheet, map,user);
		isImport = true;
	    } else if ((sheet.getSheetName().toLowerCase().equals("transbatch1.1"))/*hczhu*/
			    && ("true".equals(inputTrans))) {
			transService.impCebTransBatch(sheet, map,user);
			isImport = true;
		    } 
	    else if ((sheet.getSheetName().toLowerCase().equals("orderbatch"))
		    && ("true".equals(inputOrder))) {
		orderService.impOrderBatch(sheet, map,user);
		isImport = true;
	    } else if ((sheet.getSheetName().toLowerCase().equals("goodsbatch"))
		    && ("true".equals(inputGoods))) {
		goodsService.impGoodsBatch(sheet, map,user);
		isImport = true;
	    } else if ("true".equals(inputCleared)
		    && ("cleared".equals(sheet.getSheetName().toLowerCase()))) {
		logisticsStatusService.impCleared(sheet, map,user);
		isImport = true;
	    } else if ("true".equals(inputCleared)
		    && ("cleared1.1".equals(sheet.getSheetName().toLowerCase()))) {/*hczhu*/
		logisticsStatusService.impCebCleared(sheet, map,user);
		isImport = true;
	    }
	    // throw new Exception("没有相关的单据信息！");
	    // else if
	    // ((sheet.getSheetName().toLowerCase().indexOf("personaldeclare")
	    // >= 0) && ("true".equals(inputPerDec)))
	    // perDecService.impPerDec(sheet,map);
	    // else if ((sheet.getSheetName().toLowerCase().indexOf("payinfo")
	    // >= 0) && ("true".equals(inputPayInfo)))
	    // payInfoService.impPayInfo(sheet,map);
	    // else if ((sheet.getSheetName().toLowerCase().indexOf("perinfo")
	    // >= 0) && ("true".equals(inputPerInfo)))
	    // perInfoService.impPerInfo(sheet,map);
	}
	if (!isImport) {
	    throw new Exception("sheet命名不规范！");
	}

    }

    @RequestMapping(value = "/down")
    public @ResponseBody
    Map<String, Object> downLoad(HttpServletResponse response,
	    HttpServletRequest request) {
	ResponseDataHolder responseDataHolder = new ResponseDataHolder();
	String separator = File.separator;
	String filePath = request.getSession().getServletContext()
		.getRealPath("commons" + separator + "plugin");
	String fileName = "template.xls";
	boolean flag = FileCommonUtil.downFile(filePath + separator + fileName,
		response, fileName);
	responseDataHolder.put("success", flag);
	return responseDataHolder;
    }

}
