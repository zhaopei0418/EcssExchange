package com.cneport.ecss.exchange.service.trans.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.axis2.saaj.util.IDGenerator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cneport.ecss.exchange.common.ExcelUtil;
import com.cneport.ecss.exchange.service.entity.LogisticsInformation;
import com.cneport.ecss.exchange.service.trans.persistence.TransMapper;
import com.cneport.ecss.integration.XmlMessageSender;
import com.cneport.ecss.user.User;
import com.cneport.tophare.util.DateUtil;

@Service
public class TransService {

	// @Autowired
	private TransMapper transMapper;

	@Autowired
	private XmlMessageSender xmlMessageSender;

	private static final Logger log = Logger.getLogger(TransService.class);

	@Transactional
	public void impTrans(Sheet sheet, Map<String, String> map) throws Exception {
		sheet = ExcelUtil.cellTypeTOString(sheet);
		
		String uuid = IDGenerator.generateID();
		LogisticsInformation logisticsInformation = new LogisticsInformation();
		map.put("error", map.get("error") + "=========导入运单信息=========\n");
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);

			if (rowNum == 1) {
				logisticsInformation.setSeqNo(uuid);
				logisticsInformation.setLogisticsCode(ExcelUtil.replaceValue(
						row.getCell(0), 10, "物流企业代码", map, true));
				logisticsInformation.setLogisticsName(ExcelUtil.replaceValue(
						row.getCell(1), 100, "物流企业名称", map, true));
				logisticsInformation.setLogisticsNo(ExcelUtil.replaceValue(row
						.getCell(2), 20, "物流运单号", map));
				logisticsInformation.setTrackNo(ExcelUtil.replaceValue(row
						.getCell(3), 20, "物流跟踪号", map));
				logisticsInformation.setTrackStatus(ExcelUtil.replaceValue(row
						.getCell(4), 1, "物流跟踪状态", map));

				logisticsInformation.setNote(ExcelUtil.replaceValue(row
						.getCell(5), 1000, "备注", map));
				logisticsInformation.setShipper(ExcelUtil.replaceValue(row
						.getCell(6), 60, "发货人姓名", map));
				logisticsInformation.setShipperAddress(ExcelUtil.replaceValue(
						row.getCell(7), 100, "发货人地址", map));
				logisticsInformation.setShipperTelephone(ExcelUtil
						.replaceValue(row.getCell(8), 20, "发货人电话", map));
				logisticsInformation.setShipperCountry(ExcelUtil.replaceValue(
						row.getCell(9), 3, "发货人所在国", map));

				logisticsInformation.setConsignee(ExcelUtil.replaceValue(row
						.getCell(10), 60, "收货人姓名", map, true));
				logisticsInformation
						.setConsigneeAddress(ExcelUtil.replaceValue(row
								.getCell(11), 100, "收货人地址", map, true));
				logisticsInformation.setConsigneeTelephone(ExcelUtil
						.replaceValue(row.getCell(12), 20, "收货人电话", map, true));
				logisticsInformation.setConsigneeCountry(ExcelUtil
						.replaceValue(row.getCell(13), 3, "收货人所在国", map, true));
				logisticsInformation.setIeType(ExcelUtil.replaceValue(row
						.getCell(14), 1, "进出口标志", map, true));

				// 新增加的字段
				logisticsInformation.setTotalLogisticsNo(ExcelUtil
						.replaceValue(row.getCell(15), 24, "总运单号", map));
				logisticsInformation.setSubLogisticsNo(ExcelUtil.replaceValue(
						row.getCell(16), 24, "分运单号", map));
				logisticsInformation.setOrderNo(ExcelUtil.replaceValue(row
						.getCell(17), 30, "订单编号", map, true));
				logisticsInformation.setEcpCode(ExcelUtil.replaceValue(row
						.getCell(18), 40, "电商平台代码", map, true));
				logisticsInformation.setEcpName(ExcelUtil.replaceValue(row
						.getCell(19), 100, "电商平台名称", map, true));

				if ("I".equals(ExcelUtil.replaceValue(row.getCell(14)))) {
					logisticsInformation.setIdType(ExcelUtil.replaceValue(row
							.getCell(20), 1, "证件类型", map, true));
					logisticsInformation.setCustomerId(ExcelUtil.replaceValue(
							row.getCell(21), 30, "证件号码", map, true));
				} else {
					logisticsInformation.setIdType(ExcelUtil.replaceValue(row
							.getCell(20), 1, "证件类型", map));
					logisticsInformation.setCustomerId(ExcelUtil.replaceValue(
							row.getCell(21), 30, "证件号码", map));
				}

				logisticsInformation.setDecTime(ExcelUtil.replaceValue(row
						.getCell(22), 14, "申报日期", map));
				logisticsInformation.setWrapType(ExcelUtil.replaceValue(row
						.getCell(23), 1, "包装种类", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(24)))) {
					// logisticsInformation.setPackNum(value)
					logisticsInformation.setPackNum(new BigDecimal(0));
				} else {
					logisticsInformation.setPackNum(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(24), 19, "件数", map)));
				}

				if ("".equals(ExcelUtil.replaceValue(row.getCell(25)))) {
					logisticsInformation.setNetWeight(new BigDecimal(0));
				} else {
					logisticsInformation.setNetWeight(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(25), 19, "净重", map)));
				}
				logisticsInformation.setStockFlag(ExcelUtil.replaceValue(row
						.getCell(26), 1, "集货/备货", map, true));
				logisticsInformation.setBatchNumbers(ExcelUtil.replaceValue(row
						.getCell(27), 100, "批次号", map));
				logisticsInformation.setTradeCountry(ExcelUtil.replaceValue(row
						.getCell(28), 3, "贸易国别", map));
				logisticsInformation.setAgentCode(ExcelUtil.replaceValue(row
						.getCell(29), 40, "代理企业", map, true));
				logisticsInformation.setAgentName(ExcelUtil.replaceValue(row
						.getCell(30), 100, "代理企业名称", map, true));
				logisticsInformation.setBillType(ExcelUtil.replaceValue(row
						.getCell(31), 1, "总运单分运单标志", map,true));
			} else if (rowNum == 3) {

				if ("".equals(ExcelUtil.replaceValue(row.getCell(0)))) {
					logisticsInformation.setFreight(new BigDecimal(0));
				} else {
					logisticsInformation.setFreight(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(0), 19, "境内运费", map)));
				}
				if ("".equals(ExcelUtil.replaceValue(row.getCell(1)))) {
					logisticsInformation.setSupportValue(new BigDecimal(0));
				} else {
					logisticsInformation.setSupportValue(new BigDecimal(
							ExcelUtil.replaceValue(row.getCell(1), 19, "保价金额",
									map)));
				}

				logisticsInformation.setWeight(new BigDecimal(ExcelUtil
						.replaceValue(row.getCell(2), 19, "毛重", map, true)));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(3)))) {
					logisticsInformation.setQuantity(new BigDecimal(0));
				} else {
					logisticsInformation.setQuantity(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(3), 19, "数量", map)));
				}
				logisticsInformation.setGoodsName(ExcelUtil.replaceValue(row
						.getCell(4), 100, "商品名称", map));
				logisticsInformation.setDeliveryWay(ExcelUtil.replaceValue(row
						.getCell(5), 60, "交货方式", map));
				logisticsInformation.setShipName(ExcelUtil.replaceValue(row
						.getCell(6), 100, "运输工具名称", map));
				logisticsInformation.setDestinationPort(ExcelUtil.replaceValue(
						row.getCell(7), 4, "装运港/指运港", map));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(8)))) {
					logisticsInformation.setCrossFreight(new BigDecimal(0));
				} else {
					logisticsInformation.setCrossFreight(new BigDecimal(
							ExcelUtil.replaceValue(row.getCell(8), 19, "跨境运费",
									map)));
				}
			}

			// 设置运单关联订单所需的字段
			// logisticsInformation.setOrderNo(map.get("orderNo"));
			// logisticsInformation.setEcpCode(map.get("ecpCode"));
			// logisticsInformation.setIdType(map.get("idType"));
			// logisticsInformation.setCustomerId(map.get("customerId"));

			/*
			 * logisticsInformation.setTotalLogisticsNo(ExcelUtil.replaceValue(row
			 * .getCell(0), 24, "总运单号", map));
			 * logisticsInformation.setOrderNo(ExcelUtil
			 * .replaceValue(row.getCell(1), 30, "订单编号", map));
			 * logisticsInformation
			 * .setEcpCode(ExcelUtil.replaceValue(row.getCell(2), 10, "电商平台代码",
			 * map));
			 * logisticsInformation.setEcpName(ExcelUtil.replaceValue(row.
			 * getCell(3), 200, "电商平台名称", map));
			 * logisticsInformation.setIdType(ExcelUtil
			 * .replaceValue(row.getCell(17), 4, "证件类型", map));
			 * logisticsInformation
			 * .setCustomerId(ExcelUtil.replaceValue(row.getCell(18), 60,
			 * "证件号码", map));
			 * if("".equals(ExcelUtil.replaceValue(row.getCell(23)))){
			 * logisticsInformation.setNetWeight(new BigDecimal(0)); }else{
			 * logisticsInformation.setNetWeight(new
			 * BigDecimal(ExcelUtil.replaceValue(row.getCell(23), 19, "净重",
			 * map))); } if("".equals(ExcelUtil.replaceValue(row.getCell(24)))){
			 * logisticsInformation.setPackNum(new BigDecimal(0)); }else{
			 * logisticsInformation.setPackNum(new
			 * BigDecimal(ExcelUtil.replaceValue(row.getCell(24), 19, "件数",
			 * map))); }
			 * logisticsInformation.setWrapType(ExcelUtil.replaceValue(
			 * row.getCell(25), 1, "包装种类", map));
			 * logisticsInformation.setStockFlag
			 * (ExcelUtil.replaceValue(row.getCell(32), 1, "集货标志", map));
			 */
		}

		xmlMessageSender.sendMessage(logisticsInformation, "EcssCus");
		map.put("error", map.get("error") + "success--------------------->运单:"
				+ logisticsInformation.getLogisticsNo() + "已成功导入\n");
	}

	// @Transactional
	public void impTransBatch(Sheet sheet, Map<String, String> map, User user)
			throws Exception {

		
		sheet = ExcelUtil.cellTypeTOString(sheet);
		
		String orgId = user.getOrgId();
		map.put("error", map.get("error") + "=========导入运单信息=========\n");
		String orderNo = null;
		// StringBuffer orderNoStr = new StringBuffer("");
		int count = 0;
		LogisticsInformation logisticsInformation = new LogisticsInformation();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			if (!orgId.equals(ExcelUtil.replaceValue(sheet.getRow(rowNum)
					.getCell(29), 40, "代理企业", map, true))
					&& !orgId.equals(ExcelUtil
							.replaceValue(sheet.getRow(rowNum).getCell(0), 10,
									"物流企业代码", map, true))) {
				throw new Exception("您导入的不是您代理企业的数据或者您自己企业的数据！！\n错误数据的行数："+rowNum);
			}
		}
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);

			try {

				String uuid = IDGenerator.generateID();

				orderNo = ExcelUtil.replaceValue(row.getCell(17), 24, "总运单号",
						map);

				// 拼接订单编号
				// orderNoStr.append(orderNo + ",");

				logisticsInformation.setSeqNo(uuid);
				logisticsInformation.setIeType(ExcelUtil.replaceValue(row
						.getCell(14), 1, "进出口标志", map, true));
				logisticsInformation.setLogisticsCode(ExcelUtil.replaceValue(
						row.getCell(0), 10, "物流企业代码", map, true));
				logisticsInformation.setLogisticsName(ExcelUtil.replaceValue(
						row.getCell(1), 100, "物流企业名称", map, true));
				logisticsInformation.setLogisticsNo(ExcelUtil.replaceValue(row
						.getCell(2), 20, "物流运单号", map, logisticsInformation.getIeType().equalsIgnoreCase("I")));
				logisticsInformation.setTrackNo(ExcelUtil.replaceValue(row
						.getCell(3), 20, "物流跟踪号", map));
				logisticsInformation.setTrackStatus(ExcelUtil.replaceValue(row
						.getCell(4), 1, "物流跟踪状态", map));
				logisticsInformation.setNote(ExcelUtil.replaceValue(row
						.getCell(5), 1000, "备注", map));
				logisticsInformation.setShipper(ExcelUtil.replaceValue(row
						.getCell(6), 60, "发货人姓名", map));
				logisticsInformation.setShipperAddress(ExcelUtil.replaceValue(
						row.getCell(7), 100, "发货人地址", map));
				logisticsInformation.setShipperTelephone(ExcelUtil
						.replaceValue(row.getCell(8), 20, "发货人电话", map));
				logisticsInformation.setShipperCountry(ExcelUtil.replaceValue(
						row.getCell(9), 3, "发货人所在国", map));
				logisticsInformation.setConsignee(ExcelUtil.replaceValue(row
						.getCell(10), 60, "收货人姓名", map, true));
				logisticsInformation
						.setConsigneeAddress(ExcelUtil.replaceValue(row
								.getCell(11), 100, "收货人地址", map, true));
				logisticsInformation.setConsigneeTelephone(ExcelUtil
						.replaceValue(row.getCell(12), 20, "收货人电话", map, true));
				logisticsInformation.setConsigneeCountry(ExcelUtil
						.replaceValue(row.getCell(13), 3, "收货人所在国", map, true));
				
				// 新增加的字段
				logisticsInformation.setTotalLogisticsNo(ExcelUtil.replaceValue(
						row.getCell(15), 24, "总运单号", map));
				logisticsInformation.setTotalLogisticsNo(orderNo);
				logisticsInformation.setSubLogisticsNo(ExcelUtil.replaceValue(
						row.getCell(16), 24, "分运单号", map));

				logisticsInformation.setOrderNo(ExcelUtil.replaceValue(row
						.getCell(17), 30, "订单编号", map, true));
				logisticsInformation.setEcpCode(ExcelUtil.replaceValue(row
						.getCell(18), 40, "电商平台代码", map, true));
				logisticsInformation.setEcpName(ExcelUtil.replaceValue(row
						.getCell(19), 100, "电商平台名称", map, true));

				if ("I".equals(ExcelUtil.replaceValue(row.getCell(14)))) {
					logisticsInformation.setIdType(ExcelUtil.replaceValue(row
							.getCell(20), 1, "证件类型", map, true));
					logisticsInformation.setCustomerId(ExcelUtil.replaceValue(
							row.getCell(21), 30, "证件号码", map, true));
				} else {
					logisticsInformation.setIdType(ExcelUtil.replaceValue(row
							.getCell(20), 1, "证件类型", map));
					logisticsInformation.setCustomerId(ExcelUtil.replaceValue(
							row.getCell(21), 30, "证件号码", map));
				}

				logisticsInformation.setDecTime(ExcelUtil.replaceValue(row
						.getCell(22), 14, "申报日期", map));
				logisticsInformation.setWrapType(ExcelUtil.replaceValue(row
						.getCell(23), 1, "包装种类", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(24)))) {
					logisticsInformation.setPackNum(new BigDecimal(0));
				} else {
					logisticsInformation.setPackNum(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(24), 19, "件数", map)));
				}

				if ("".equals(ExcelUtil.replaceValue(row.getCell(25)))) {
					logisticsInformation.setNetWeight(new BigDecimal(0));
				} else {
					logisticsInformation.setNetWeight(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(25), 19, "净重", map)));
				}
				logisticsInformation.setStockFlag(ExcelUtil.replaceValue(row
						.getCell(26), 1, "集货/备货", map, true));
				logisticsInformation.setBatchNumbers(ExcelUtil.replaceValue(row
						.getCell(27), 100, "批次号", map));
				logisticsInformation.setTradeCountry(ExcelUtil.replaceValue(row
						.getCell(28), 3, "贸易国别", map));
				logisticsInformation.setAgentCode(ExcelUtil.replaceValue(row
						.getCell(29), 40, "代理企业", map, true));
				logisticsInformation.setAgentName(ExcelUtil.replaceValue(row
						.getCell(30), 100, "代理企业名称", map, true));
				logisticsInformation.setBillType(ExcelUtil.replaceValue(row
						.getCell(31), 1, "总运单分运单标志", map,true));
				logisticsInformation.setModifyMark(ExcelUtil.replaceValue(row
						.getCell(32), 1, "操作类型", map,true));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(33)))) {
					logisticsInformation.setFreight(new BigDecimal(0));
				} else {
					logisticsInformation.setFreight(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(33), 19, "境内运费", map)));
				}
				if ("".equals(ExcelUtil.replaceValue(row.getCell(34)))) {
					logisticsInformation.setSupportValue(new BigDecimal(0));
				} else {
					logisticsInformation.setSupportValue(new BigDecimal(
							ExcelUtil.replaceValue(row.getCell(34), 19, "保价金额",
									map)));
				}
				logisticsInformation.setWeight(new BigDecimal(ExcelUtil
						.replaceValue(row.getCell(35), 19, "毛重", map, true)));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(36)))) {
					logisticsInformation.setQuantity(new BigDecimal(0));
				} else {
					logisticsInformation.setQuantity(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(36), 19, "数量", map)));
				}
				logisticsInformation.setGoodsName(ExcelUtil.replaceValue(row
						.getCell(37), 100, "商品名称", map));
				logisticsInformation.setDeliveryWay(ExcelUtil.replaceValue(row
						.getCell(38), 60, "交货方式", map));
				logisticsInformation.setShipName(ExcelUtil.replaceValue(row
						.getCell(39), 100, "运输工具名称", map));
				logisticsInformation.setDestinationPort(ExcelUtil.replaceValue(
						row.getCell(40), 4, "装运港/指运港", map));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(41)))) {
					logisticsInformation.setCrossFreight(new BigDecimal(0));
				} else {
					logisticsInformation.setCrossFreight(new BigDecimal(
							ExcelUtil.replaceValue(row.getCell(41), 19, "跨境运费",
									map)));
				}
				xmlMessageSender.sendMessage(logisticsInformation, "EcssCus");
				count++;
				map.put("error", "success--------------------->已成功导入" + count
						+ "条运单\n");
			} catch (Exception e) {
				throw new Exception("订单：" + orderNo + " " + e.getMessage());
			}
		}

	}
	
	// @Transactional
	public void impCebTransBatch(Sheet sheet, Map<String, String> map, User user)
			throws Exception {

		
		sheet = ExcelUtil.cellTypeTOString(sheet);
		
		String orgId = user.getOrgId();
		map.put("error", map.get("error") + "=========导入运单信息=========\n");
		String orderNo = null;
		// StringBuffer orderNoStr = new StringBuffer("");
		int count = 0;
		com.cneport.ecss.exchange.service.cebentity.LogisticsInformation cebLogisticsInformation = new com.cneport.ecss.exchange.service.cebentity.LogisticsInformation();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			if (!orgId.equals(ExcelUtil.replaceValue(sheet.getRow(rowNum)
					.getCell(29), 40, "代理企业", map, true))
					&& !orgId.equals(ExcelUtil
							.replaceValue(sheet.getRow(rowNum).getCell(0), 10,
									"物流企业代码", map, true))) {
				throw new Exception("您导入的不是您代理企业的数据或者您自己企业的数据！！\n错误数据的行数："+rowNum);
			}
		}
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			
			try {

				String uuid = IDGenerator.generateID();

				orderNo = ExcelUtil.replaceValue(row.getCell(17), 24, "总运单号",
						map);

				// 拼接订单编号
				// orderNoStr.append(orderNo + ",");

				cebLogisticsInformation.setSeqNo(uuid);
				
				cebLogisticsInformation.setIeType(ExcelUtil.replaceValue(row
						.getCell(14), 1, "进出口标志", map, true));
				
				cebLogisticsInformation.setLogisticsCode(ExcelUtil.replaceValue(
						row.getCell(0), 10, "物流企业代码", map, true));
				cebLogisticsInformation.setLogisticsName(ExcelUtil.replaceValue(
						row.getCell(1), 100, "物流企业名称", map, true));
				cebLogisticsInformation.setLogisticsNo(ExcelUtil.replaceValue(row
						.getCell(2), 20, "物流运单号", map, cebLogisticsInformation.getIeType().equalsIgnoreCase("I")));
				cebLogisticsInformation.setTrackNo(ExcelUtil.replaceValue(row
						.getCell(3), 20, "物流跟踪号", map));
				cebLogisticsInformation.setTrackStatus(ExcelUtil.replaceValue(row
						.getCell(4), 1, "物流跟踪状态", map));
				cebLogisticsInformation.setNote(ExcelUtil.replaceValue(row
						.getCell(5), 1000, "备注", map));
				cebLogisticsInformation.setShipper(ExcelUtil.replaceValue(row
						.getCell(6), 60, "发货人姓名", map));
				cebLogisticsInformation.setShipperAddress(ExcelUtil.replaceValue(
						row.getCell(7), 100, "发货人地址", map));
				cebLogisticsInformation.setShipperTelephone(ExcelUtil
						.replaceValue(row.getCell(8), 20, "发货人电话", map));
				cebLogisticsInformation.setShipperCountry(ExcelUtil.replaceValue(
						row.getCell(9), 3, "发货人所在国", map));
				cebLogisticsInformation.setConsignee(ExcelUtil.replaceValue(row
						.getCell(10), 60, "收货人姓名", map, true));
				cebLogisticsInformation
						.setConsigneeAddress(ExcelUtil.replaceValue(row
								.getCell(11), 100, "收货人地址", map, true));
				cebLogisticsInformation.setConsigneeTelephone(ExcelUtil
						.replaceValue(row.getCell(12), 20, "收货人电话", map, true));
				cebLogisticsInformation.setConsigneeCountry(ExcelUtil
						.replaceValue(row.getCell(13), 3, "收货人所在国", map, true));
				
				
				// 新增加的字段
				cebLogisticsInformation.setTotalLogisticsNo(ExcelUtil.replaceValue(
						row.getCell(15), 24, "总运单号", map));
				cebLogisticsInformation.setTotalLogisticsNo(orderNo);
				cebLogisticsInformation.setSubLogisticsNo(ExcelUtil.replaceValue(
						row.getCell(16), 24, "分运单号", map));

				cebLogisticsInformation.setOrderNo(ExcelUtil.replaceValue(row
						.getCell(17), 30, "订单编号", map, true));
				cebLogisticsInformation.setEcpCode(ExcelUtil.replaceValue(row
						.getCell(18), 40, "电商平台代码", map, true));
				cebLogisticsInformation.setEcpName(ExcelUtil.replaceValue(row
						.getCell(19), 100, "电商平台名称", map, true));

				if ("I".equals(ExcelUtil.replaceValue(row.getCell(14)))) {
					cebLogisticsInformation.setIdType(ExcelUtil.replaceValue(row
							.getCell(20), 1, "证件类型", map, true));
					cebLogisticsInformation.setCustomerId(ExcelUtil.replaceValue(
							row.getCell(21), 30, "证件号码", map, true));
				} else {
					cebLogisticsInformation.setIdType(ExcelUtil.replaceValue(row
							.getCell(20), 1, "证件类型", map));
					cebLogisticsInformation.setCustomerId(ExcelUtil.replaceValue(
							row.getCell(21), 30, "证件号码", map));
				}

				cebLogisticsInformation.setDecTime(ExcelUtil.replaceValue(row
						.getCell(22), 14, "申报日期", map));
				cebLogisticsInformation.setWrapType(ExcelUtil.replaceValue(row
						.getCell(23), 1, "包装种类", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(24)))) {
					cebLogisticsInformation.setPackNum(new BigDecimal(0));
				} else {
					cebLogisticsInformation.setPackNum(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(24), 19, "件数", map)));
				}
				if ("".equals(ExcelUtil.replaceValue(row.getCell(25)))) {
					cebLogisticsInformation.setNetWeight(new BigDecimal(0));
				} else {
					cebLogisticsInformation.setNetWeight(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(25), 19, "净重", map, 2, false)));
				}
				cebLogisticsInformation.setStockFlag(ExcelUtil.replaceValue(row
						.getCell(26), 1, "集货/备货", map, true));
				cebLogisticsInformation.setBatchNumbers(ExcelUtil.replaceValue(row
						.getCell(27), 100, "批次号", map));
				cebLogisticsInformation.setTradeCountry(ExcelUtil.replaceValue(row
						.getCell(28), 3, "贸易国别", map));
				cebLogisticsInformation.setAgentCode(ExcelUtil.replaceValue(row
						.getCell(29), 40, "代理企业", map, true));
				cebLogisticsInformation.setAgentName(ExcelUtil.replaceValue(row
						.getCell(30), 100, "代理企业名称", map, true));
				cebLogisticsInformation.setBillType(ExcelUtil.replaceValue(row
						.getCell(31), 1, "总运单分运单标志", map,true));
				cebLogisticsInformation.setModifyMark(ExcelUtil.replaceValue(row
						.getCell(32), 1, "操作类型", map,true));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(33)))) {
					cebLogisticsInformation.setFreight(new BigDecimal(0));
				} else {
					cebLogisticsInformation.setFreight(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(33), 19, "境内运费", map)));
				}
				if ("".equals(ExcelUtil.replaceValue(row.getCell(34)))) {
					cebLogisticsInformation.setSupportValue(new BigDecimal(0));
				} else {
					cebLogisticsInformation.setSupportValue(new BigDecimal(
							ExcelUtil.replaceValue(row.getCell(34), 19, "保价金额",
									map)));
				}
				cebLogisticsInformation.setWeight(new BigDecimal(ExcelUtil
						.replaceValue(row.getCell(35), 19, "毛重", map, 2, true)));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(36)))) {
					cebLogisticsInformation.setQuantity(new BigDecimal(0));
				} else {
					cebLogisticsInformation.setQuantity(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(36), 19, "数量", map)));
				}
				cebLogisticsInformation.setGoodsName(ExcelUtil.replaceValue(row
						.getCell(37), 100, "商品名称", map));
				cebLogisticsInformation.setDeliveryWay(ExcelUtil.replaceValue(row
						.getCell(38), 60, "交货方式", map));
				cebLogisticsInformation.setShipName(ExcelUtil.replaceValue(row
						.getCell(39), 100, "运输工具名称", map));
				cebLogisticsInformation.setDestinationPort(ExcelUtil.replaceValue(
						row.getCell(40), 4, "装运港/指运港", map));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(41)))) {
					cebLogisticsInformation.setCrossFreight(new BigDecimal(0));
				} else {
					cebLogisticsInformation.setCrossFreight(new BigDecimal(
							ExcelUtil.replaceValue(row.getCell(41), 19, "跨境运费",
									map)));
				}
				/* begin add */
				cebLogisticsInformation.setCustomsCode(ExcelUtil.replaceValue(row
						.getCell(42), 4, "主管海关代码", map, true));
				cebLogisticsInformation.setTrafMode(ExcelUtil.replaceValue(row
						.getCell(43), 1, "运输方式", map, true));
				cebLogisticsInformation.setVoyageNo(ExcelUtil.replaceValue(row
						.getCell(44), 32, "航班航次号", map));
				cebLogisticsInformation.setBillNo(ExcelUtil.replaceValue(row
						.getCell(45), 37, "提运单号", map));
				cebLogisticsInformation.setParcelInfo(ExcelUtil.replaceValue(row
						.getCell(46), 200, "包裹单信息", map));
				cebLogisticsInformation.setAccessType(ExcelUtil.replaceValue(row
						.getCell(47), 1, "接入类型", map, true));
				cebLogisticsInformation.setAppUid(ExcelUtil.replaceValue(row
						.getCell(48), 20, "用户编号", map));
				cebLogisticsInformation.setAppUname(ExcelUtil.replaceValue(row
						.getCell(49), 60, "用户名称", map));
				cebLogisticsInformation.setAppTime(DateUtil.convertDateToYYYYMMddHHmmSS(new Date()));
				cebLogisticsInformation.setAppStatus("2");
				/* begin end */
				//xmlMessageSender.sendMessage(cebLogisticsInformation, "ReceiveErp");
				xmlMessageSender.sendMessage(cebLogisticsInformation, cebLogisticsInformation.getLogisticsCode());
				count++;
				map.put("error", "success--------------------->已成功导入" + count
						+ "条运单\n");
			} catch (Exception e) {
				throw new Exception("订单：" + orderNo + " " + e.getMessage());
			}
		}

	}
}
