package com.cneport.ecss.exchange.service.perDec.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.saaj.util.IDGenerator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cneport.ecss.exchange.common.ExcelUtil;
import com.cneport.ecss.exchange.service.perDec.entity.PerDecHead;
import com.cneport.ecss.exchange.service.perDec.entity.PerDecList;
import com.cneport.ecss.exchange.service.perDec.persistence.PerDecMapper;

@Service
public class PerDecService {

    // @Autowired
    private PerDecMapper perDecMapper;

    private static final Logger log = Logger.getLogger(PerDecService.class);

    @Transactional
    public void impPerDec(Sheet sheet, Map<String, String> map)
	    throws Exception {
	sheet = ExcelUtil.cellTypeTOString(sheet);
	String uuid = IDGenerator.generateID();
	String H_ECP_CODE = "";
	String H_CBE_CODE = "";
	String L_GOODS_NO = "";
	int countPerDecHead = 0;
	map.put("error", "===========个人物品申报单===========,");
	for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
	    Row row = sheet.getRow(rowNum);
	    if (rowNum == 1) {
		PerDecHead perDecHead = new PerDecHead();
		perDecHead.setSeqNo(uuid);
		perDecHead.setHOperator(ExcelUtil.replaceValue(row.getCell(1),
			60, "运营人名称", map));
		perDecHead.setHIEPort(ExcelUtil.replaceValue(row.getCell(2), 5,
			"进/出口岸", map));
		perDecHead.setHVoy(ExcelUtil.replaceValue(row.getCell(3), 50,
			"运输工具", map));
		perDecHead.setHIEDate(ExcelUtil.replaceValue(row.getCell(4), 8,
			"进/出口日期", map));
		perDecHead.setHTotalShippedNumber(ExcelUtil.replaceValue(
			row.getCell(5), 24, "总运单号", map));
		perDecHead.setHDeclarant(ExcelUtil.replaceValue(row.getCell(6),
			60, "报关员", map));
		perDecHead.setHOrderNo(ExcelUtil.replaceValue(row.getCell(7),
			30, "订单编号", map));
		perDecHead.setHEcpCode(ExcelUtil.replaceValue(row.getCell(8),
			40, "电商平台代码", map));
		perDecHead.setHEcpName(ExcelUtil.replaceValue(row.getCell(9),
			100, "电商平台名称", map));
		perDecHead.setHTaxAddress(ExcelUtil.replaceValue(
			row.getCell(10), 255, "税单地址", map));
		perDecHead.setHIdNumber(ExcelUtil.replaceValue(row.getCell(11),
			60, "身份证号", map));
		perDecHead.setHConsignee(ExcelUtil.replaceValue(
			row.getCell(12), 60, "收货人姓名", map));
		perDecHead.setHLogisticsCode(ExcelUtil.replaceValue(
			row.getCell(13), 10, "物流企业代码", map));
		perDecHead.setHLogisticsName(ExcelUtil.replaceValue(
			row.getCell(14), 100, "物流企业名称", map));
		perDecHead.setHLogisticsNo(ExcelUtil.replaceValue(
			row.getCell(15), 20, "物流运单号", map));
		perDecHead.setHCountry(ExcelUtil.replaceValue(row.getCell(16),
			3, "国别/地区", map));
		perDecHead.setHCbeCode(ExcelUtil.replaceValue(row.getCell(17)));

		H_CBE_CODE = ExcelUtil.replaceValue(row.getCell(17));
		H_ECP_CODE = ExcelUtil.replaceValue(row.getCell(8));

		Map param = new HashMap();
		param.put("ecpCode", ExcelUtil.replaceValue(row.getCell(8)));
		param.put("orderNo", ExcelUtil.replaceValue(row.getCell(7)));
		// countPerDecHead = this.perDecMapper.countPerDecHead(param);
		if (countPerDecHead > 0) {
		    map.put("error", map.get("error" + "个人物品申报单已经存在,"));
		    return;
		}

		this.perDecMapper.insertPerDecHead(perDecHead);
	    } else if (rowNum >= 3) {

		PerDecList perDecList = new PerDecList();
		perDecList.setLGoodsNo(ExcelUtil.replaceValue(row.getCell(0),
			20, "商品货号", map));
		perDecList.setLTaxCode(ExcelUtil.replaceValue(row.getCell(1),
			8, "税则号", map));
		perDecList.setLGoodsName(ExcelUtil.replaceValue(row.getCell(2),
			250, "物品名称", map));
		perDecList.setLPrice(ExcelUtil.replaceValue(row.getCell(3), 10,
			"单价（元）", map));
		perDecList.setLQuantity(ExcelUtil.replaceValue(row.getCell(4),
			10, "数量", map));
		perDecList.setLUnit(ExcelUtil.replaceValue(row.getCell(5), 10,
			"单位", map));
		perDecList.setLCodeTs(ExcelUtil.replaceValue(row.getCell(8),
			10, "HS编码", map));
		perDecList.setLGoodsModel(ExcelUtil.replaceValue(
			row.getCell(9), 250, "规格型号", map));
		perDecList.setLItemNo(H_ECP_CODE + H_CBE_CODE
			+ ExcelUtil.replaceValue(row.getCell(0)));

		if (ExcelUtil.rowIsNull(perDecList)) {
		    return;
		}

		int listNo = this.perDecMapper.countPerDecListNo(uuid) + 1;
		perDecList.setSeqNo(uuid);
		perDecList.setListNo(String.valueOf(listNo));
		this.perDecMapper.insertPerDecList(perDecList);
	    }
	}
    }
}