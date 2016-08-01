package com.cneport.ecss.exchange.service.logisticsStatus.service;

import java.util.Date;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cneport.ecss.exchange.common.ExcelUtil;
import com.cneport.ecss.exchange.service.entity.LogisticsStatus;
import com.cneport.ecss.integration.XmlMessageSender;
import com.cneport.ecss.user.Role;
import com.cneport.ecss.user.User;
import com.cneport.ecss.user.UserConstant;
import com.cneport.tophare.util.DateUtil;

@Service
public class LogisticsStatusService {

    @Autowired
    private XmlMessageSender xmlMessageSender;

    public void impCleared(Sheet sheet, Map<String, String> map,User user)
	    throws Exception {
		sheet = ExcelUtil.cellTypeTOString(sheet);
		LogisticsStatus logisticsStatus = null;
		int count = 0;
		boolean isExit=true;
		String logisticsNo = null;
		for (Role r : user.getRoles()) {
	    	if (UserConstant.IMPORT_DECLEAR.equals(r.getRoleCode())) {
	    		isExit = false;
	    		break;
	    	}
	    }
		if(isExit){
			throw new Exception("没有导入结关信息的权限！！");
		}
		map.put("error", map.get("error") + "=========结关信息=========\n");
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
		    Row row = sheet.getRow(rowNum);
		    logisticsStatus = new LogisticsStatus();
		    logisticsNo = ExcelUtil.replaceValue(
				    row.getCell(1), 20, "运单号", map, true);
		    
		    try {
				logisticsStatus.setTotalLogisticsNo(ExcelUtil.replaceValue(
				    row.getCell(0), 24, "总运单号", map));
				logisticsStatus.setLogisticsNo(logisticsNo);
				logisticsStatus.setLogisticsCode(ExcelUtil.replaceValue(
				    row.getCell(2), 10, "物流企业代码", map, true));
				logisticsStatus.setLogisticsName(ExcelUtil.replaceValue(
				    row.getCell(3), 100, "物流企业名称", map, true));
				logisticsStatus.setLogisticsStatus(ExcelUtil.replaceValue(
				    row.getCell(4), 10, "运单状态", map, true));
				if ("1".equals(ExcelUtil.replaceValue(row.getCell(4)))) {
					logisticsStatus.setDeliverDate(ExcelUtil.replaceValue(
						row.getCell(5), 14, "妥投日期/运抵境外日期", map, true));
				} else {
					logisticsStatus.setDeliverDate(ExcelUtil.replaceValue(
						row.getCell(5), 14, "妥投日期/运抵境外日期", map));
				}

				logisticsStatus.setFailReason(ExcelUtil.replaceValue(
				    row.getCell(6), 100, "异常原因", map));
				logisticsStatus.setIeType(ExcelUtil.replaceValue(row.getCell(7), 1,
				    "进出口标志", map, true));
				logisticsStatus.setSubLogisticsNo(logisticsStatus.getLogisticsNo());
				logisticsStatus.setBillType("3");
				if (ExcelUtil.rowIsNull(logisticsStatus)) {
					return;
				}
				xmlMessageSender.sendMessage(logisticsStatus, "EcssCus");
				count++;
				map.put("error", "success--------------------->结关信息已成功导入" + count
				    + "条\n");
			} catch (Exception e) {
				throw new Exception("运单号：" + logisticsNo + " " + e.getMessage());
			}
		}

    }
    
    public void impCebCleared(Sheet sheet, Map<String, String> map,User user)
    throws Exception {
	sheet = ExcelUtil.cellTypeTOString(sheet);
	com.cneport.ecss.exchange.service.cebentity.LogisticsStatus cebLogisticsStatus = null;
	int count = 0;
	boolean isExit=true;
	String logisticsNo = null;
	for (Role r : user.getRoles()) {
    	if (UserConstant.IMPORT_DECLEAR.equals(r.getRoleCode())) {
    		isExit = false;
    		break;
    	}
    }
	if(isExit){
		throw new Exception("没有导入结关信息的权限！！");
	}
	map.put("error", map.get("error") + "=========结关信息=========\n");
	for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
	    Row row = sheet.getRow(rowNum);
	    cebLogisticsStatus = new com.cneport.ecss.exchange.service.cebentity.LogisticsStatus();
	    logisticsNo = ExcelUtil.replaceValue(
			    row.getCell(1), 20, "运单号", map, true);
	    
	    try {
			cebLogisticsStatus.setTotalLogisticsNo(ExcelUtil.replaceValue(
			    row.getCell(0), 24, "总运单号", map));
			cebLogisticsStatus.setLogisticsNo(logisticsNo);
			cebLogisticsStatus.setLogisticsCode(ExcelUtil.replaceValue(
			    row.getCell(2), 10, "物流企业代码", map, true));
			cebLogisticsStatus.setLogisticsName(ExcelUtil.replaceValue(
			    row.getCell(3), 100, "物流企业名称", map, true));
			cebLogisticsStatus.setLogisticsStatus(ExcelUtil.replaceValue(
			    row.getCell(4), 10, "运单状态", map, true));
			if ("1".equals(ExcelUtil.replaceValue(row.getCell(4)))) {
				cebLogisticsStatus.setDeliverDate(ExcelUtil.replaceValue(
					row.getCell(5), 14, "妥投日期/运抵境外日期", map, true));
			} else {
				cebLogisticsStatus.setDeliverDate(ExcelUtil.replaceValue(
					row.getCell(5), 14, "妥投日期/运抵境外日期", map));
			}

			cebLogisticsStatus.setFailReason(ExcelUtil.replaceValue(
			    row.getCell(6), 100, "异常原因", map));
			cebLogisticsStatus.setIeType(ExcelUtil.replaceValue(row.getCell(7), 1,
			    "进出口标志", map, true));
			cebLogisticsStatus.setSubLogisticsNo(cebLogisticsStatus.getLogisticsNo());
			cebLogisticsStatus.setBillType("3");
			
			/*add begin*/
			cebLogisticsStatus.setCustomsCode(ExcelUtil.replaceValue(row.getCell(8), 4,
				    "主管海关代码", map, true));
			cebLogisticsStatus.setTrafMode(ExcelUtil.replaceValue(row.getCell(9), 1,
				    "运输方式", map, true));
			cebLogisticsStatus.setShipName(ExcelUtil.replaceValue(row.getCell(10), 100,
				    "运输工具名称", map, true));
			
			cebLogisticsStatus.setVoyageNo(ExcelUtil.replaceValue(
				    row.getCell(11), 32, "航班航次号", map));
			cebLogisticsStatus.setBillNo(ExcelUtil.replaceValue(
				    row.getCell(12), 37, "提运单号", map));
			cebLogisticsStatus.setNote(ExcelUtil.replaceValue(
				    row.getCell(13), 1000, "备注", map));
			cebLogisticsStatus.setAppType(ExcelUtil.replaceValue(
				    row.getCell(14), 1, "操作类型", map));
			cebLogisticsStatus.setAccessType(ExcelUtil.replaceValue(
				    row.getCell(15), 1, "接入类型", map));
			cebLogisticsStatus.setAppUid(ExcelUtil.replaceValue(row
					.getCell(16), 20, "用户编号", map));
			cebLogisticsStatus.setAppUname(ExcelUtil.replaceValue(row
					.getCell(17), 60, "用户名称", map));
			
			cebLogisticsStatus.setAppTime(DateUtil.convertDateToYYYYMMddHHmmSS(new Date()));
			cebLogisticsStatus.setAppStatus("2");
			/*add end*/
			
			if (ExcelUtil.rowIsNull(cebLogisticsStatus)) {
				return;
			}
			//xmlMessageSender.sendMessage(cebLogisticsStatus, "ReceiveErp");
			xmlMessageSender.sendMessage(cebLogisticsStatus, cebLogisticsStatus.getLogisticsCode());
			count++;
			map.put("error", "success--------------------->结关信息已成功导入" + count
			    + "条\n");
		} catch (Exception e) {
			throw new Exception("运单号：" + logisticsNo + " " + e.getMessage());
		}
	}

}
}