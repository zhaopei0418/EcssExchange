package com.cneport.ecss.exchange.service.goods.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cneport.ecss.exchange.common.ExcelUtil;
import com.cneport.ecss.exchange.service.entity.Goods;
import com.cneport.ecss.exchange.service.entity.GoodsHead;
import com.cneport.ecss.exchange.service.entity.GoodsList;
import com.cneport.ecss.exchange.service.goods.persistence.GoodsMapper;
import com.cneport.ecss.integration.XmlMessageSender;
import com.cneport.ecss.user.User;

@Service
public class GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private XmlMessageSender xmlMessageSender;
	
	private static final Logger log = Logger.getLogger(GoodsService.class);

	@Transactional
	public void impGoods(Sheet sheet, Map<String, String> map) throws Exception {
		sheet = ExcelUtil.cellTypeTOString(sheet);
		Goods goods = new Goods();
		GoodsHead goodsHead = new GoodsHead();
		List<GoodsList> list = new ArrayList<GoodsList>();
		int count = 1;

		map.put("error", map.get("error") + "=========导入备案商品信息=========\n");

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (rowNum == 1) {
				map.put("error", map.get("error") + "备案商品表头:\n");

				goodsHead.setCbeCode(ExcelUtil.replaceValue(row.getCell(0), 10,
						"仓储代码", map, true));
				goodsHead.setCbeName(ExcelUtil.replaceValue(row.getCell(1),
						100, "仓储名称", map, true));
				goodsHead.setApplyCode(ExcelUtil.replaceValue(row.getCell(2),
						10, "代理申报企业代码", map, true));
				goodsHead.setApplyName(ExcelUtil.replaceValue(row.getCell(3),
						100, "代理申报企业名称", map, true));

				goodsHead.setIeType(ExcelUtil.replaceValue(row.getCell(4), 1,
						"进出口标志", map, true));
				goodsHead.setApplyUser(ExcelUtil.replaceValue(row.getCell(5),
						60, "申请人", map));
			} else if (rowNum >= 3) {

				map.put("error", map.get("error") + "备案商品表体[" + count + "]:\n");
				GoodsList goodsList = new GoodsList();

				goodsList.setGoodsNo(ExcelUtil.replaceValue(row.getCell(0), 20,
						"商品货号", map, true));

				goodsList.setShelfGoodsName(ExcelUtil.replaceValue(row
						.getCell(1), 250, "商品上架品名", map, true));
				goodsList.setDescribe(ExcelUtil.replaceValue(row.getCell(2),
						250, "商品描述", map));
				goodsList.setCodeTs(ExcelUtil.replaceValue(row.getCell(3), 14,
						"HS编码", map));
				goodsList.setGoodsName(ExcelUtil.replaceValue(row.getCell(4),
						250, "申报品名", map));
				goodsList.setGoodsModel(ExcelUtil.replaceValue(row.getCell(5),
						250, "规格型号", map));

				goodsList.setUnit(ExcelUtil.replaceValue(row.getCell(6), 10,
						"计量单位", map, true));
				goodsList.setCountry(ExcelUtil.replaceValue(row.getCell(7), 3,
						"原产国", map, true));

				goodsList.setPrice(new BigDecimal(ExcelUtil.replaceValue(row
						.getCell(8), 19, "备案价格", map, true)));
				goodsList.setCurrency(ExcelUtil.replaceValue(row.getCell(9), 3,
						"币制", map, true));
				goodsList.setImage(ExcelUtil.replaceValue(row.getCell(10),
						1024, "商品图片", map));
				goodsList.setBarCode(ExcelUtil.replaceValue(row.getCell(11),
						40, "商品条形码", map));
				goodsList.setTaxCode(ExcelUtil.replaceValue(row.getCell(12), 8,
						"行邮税号", map));

				goodsList.setEcpCode(ExcelUtil.replaceValue(row.getCell(13),
						20, "仓储平台代码", map));
				goodsList.setEcpName(ExcelUtil.replaceValue(row.getCell(14),
						100, "仓储平台名称", map));

				goodsList.setIsTax(ExcelUtil.replaceValue(row.getCell(15), 1,
						"是否涉出口税", map));
				goodsList.setSuperviseId(ExcelUtil.replaceValue(
						row.getCell(16), 30, "监管证件代码", map));
				goodsList.setItemNo(ExcelUtil.replaceValue(row.getCell(17),
						100, "海关备案商品编号", map));
				goodsList.setLimitationGoodsCode(ExcelUtil.replaceValue(row
						.getCell(18), 20, "限制商品编码", map));
				goodsList.setBatchNumbers(ExcelUtil.replaceValue(row
						.getCell(19), 100, "批次号", map));

				if (ExcelUtil.rowIsNull(goodsList)) {
					return;
				}
				// goodsList.setListNo(String.valueOf(count));
				count++;

				list.add(goodsList);
			}
		}

		goods.setGoodsHead(goodsHead);
		goods.setGoodsList(list);

		xmlMessageSender.sendMessage(goods, "EcssEnt");
		map.put("error", map.get("error")
				+ "success--------------------->商品备案已成功导入\n");
	}

	@Transactional
	public void impGoodsBatch(Sheet sheet, Map<String, String> map, User user)
			throws Exception {
		sheet = ExcelUtil.cellTypeTOString(sheet);
		Goods goods = null;
		GoodsHead goodsHead = null;
		
		List<GoodsList> list = new ArrayList<GoodsList>();
		int amount = 0;
		int count = 0;
		String cbeCode = null;
		String applyCode = null;
		String dbApplyCode = null;
		String[] cebApplyCodes = null;
		String orgId = user.getOrgId();
		map.put("error", map.get("error") + "=========导入备案商品信息=========一共" + sheet.getLastRowNum() + "行数据\n");
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			/*
			if (!orgId.equals(ExcelUtil.replaceValue(sheet.getRow(rowNum)
					.getCell(0), 10, "仓储代码", map, true))
					&& !orgId.equals(ExcelUtil.replaceValue(sheet
							.getRow(rowNum).getCell(2), 10, "代理申报企业代码", map,
							true))) {
				throw new Exception("您导入的不是您代理企业的数据或者您自己企业的数据！！\n错误数据的行数："+rowNum+1);
			}
			*/
			cbeCode = ExcelUtil.replaceValue(sheet.getRow(rowNum).getCell(0), 10, "电商代码", map, true);
			applyCode = ExcelUtil.replaceValue(sheet.getRow(rowNum).getCell(2), 10, "代理申报企业代码", map, true);
			dbApplyCode = goodsMapper.getApplyCode(cbeCode);
			log.info("cbeCode:[" + cbeCode + "] applyCode:[" + applyCode + "] dbApplyCode:[" + dbApplyCode + "]");
			if (null == dbApplyCode || dbApplyCode.equals(":")) {
				throw new Exception("您导入的电商没有建立商品备案库，请先创建！！");
			} else {
				cebApplyCodes = dbApplyCode.split(":");
				if (!orgId.equals(cebApplyCodes[0]) && !orgId.equals(cebApplyCodes[1])) {
					throw new Exception("您导入的不是您代理企业的数据或者您自己企业的数据！！");
				}
				
				if (!orgId.equals(cbeCode) && !orgId.equals(applyCode)) {
					throw new Exception("您导入的不是您代理企业的数据或者您自己企业的数据！！");
				}
			}
			
		}
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			try {
				if (rowNum == 1
						|| !ExcelUtil.replaceValue(
								sheet.getRow(rowNum).getCell(0), 30, "仓储代码",
								map, true).equals(
								ExcelUtil.replaceValue(sheet.getRow(rowNum - 1)
										.getCell(0)))) {
					goods = new Goods();
					goodsHead = new GoodsHead();
					
					map.put("error", map.get("error") + "备案商品表头:\n");

					cbeCode = ExcelUtil.replaceValue(row.getCell(0), 10,
							"仓储代码", map, true);

					goodsHead.setCbeCode(cbeCode);
					goodsHead.setCbeName(ExcelUtil.replaceValue(row.getCell(1),
							100, "仓储名称", map, true));
					goodsHead.setApplyCode(ExcelUtil.replaceValue(row
							.getCell(2), 10, "代理申报企业代码", map, true));
					goodsHead.setApplyName(ExcelUtil.replaceValue(row
							.getCell(3), 100, "代理申报企业名称", map, true));

					goodsHead.setIeType(ExcelUtil.replaceValue(row.getCell(4),
							1, "进出口标志", map, true));
					goodsHead.setApplyUser(ExcelUtil.replaceValue(row
							.getCell(5), 60, "申请人", map));
				}
				// map.put("error", map.get("error") + "备案商品表体:\n");
				GoodsList goodsList = new GoodsList();
				goodsList.setGoodsNo(ExcelUtil.replaceValue(row.getCell(6), 20,
						"商品货号", map, true));

				goodsList.setShelfGoodsName(ExcelUtil.replaceValue(row
						.getCell(7), 250, "商品上架品名", map, true));
				goodsList.setShelfGoodsNameForeign(ExcelUtil.replaceValue(row
						.getCell(8), 250, "商品上架外文品名", map, false));
				goodsList.setDescribe(ExcelUtil.replaceValue(row.getCell(9),
						250, "商品描述", map));
				goodsList.setCodeTs(ExcelUtil.replaceValue(row.getCell(10), 14,
						"HS编码", map));
				goodsList.setGoodsName(ExcelUtil.replaceValue(row.getCell(11),
						250, "申报品名", map));
				goodsList.setGoodsModel(ExcelUtil.replaceValue(row.getCell(12),
						250, "规格型号", map));

				goodsList.setUnit(ExcelUtil.replaceValue(row.getCell(13), 3,
						"计量单位", map, true));
				goodsList.setUnit1(ExcelUtil.replaceValue(row
						.getCell(14), 3, "法定第一计量单位", map,true));
				goodsList.setUnit2(ExcelUtil.replaceValue(row
						.getCell(15), 3, "法定第二计量单位", map));
				goodsList.setCountry(ExcelUtil.replaceValue(row.getCell(16), 3,
						"原产国", map, true));

				goodsList.setPrice(new BigDecimal(ExcelUtil.replaceValue(row
						.getCell(17), 19, "备案价格", map, true)));
				goodsList.setCurrency(ExcelUtil.replaceValue(row.getCell(18),
						3, "币制", map, true));
				goodsList.setImage(ExcelUtil.replaceValue(row.getCell(19),
						1024, "商品图片", map));
				goodsList.setBarCode(ExcelUtil.replaceValue(row.getCell(20),
						40, "商品条形码", map));
				goodsList.setTaxCode(ExcelUtil.replaceValue(row.getCell(21), 8,
						"行邮税号", map));

				goodsList.setEcpCode(ExcelUtil.replaceValue(row.getCell(22),
						20, "电商平台代码", map));
				goodsList.setEcpName(ExcelUtil.replaceValue(row.getCell(23),
						100, "电商平台名称", map));

				goodsList.setIsTax(ExcelUtil.replaceValue(row.getCell(24), 1,
						"是否涉出口税", map));
				goodsList.setSuperviseId(ExcelUtil.replaceValue(
						row.getCell(25), 30, "监管证件代码", map));
				goodsList.setItemNo(ExcelUtil.replaceValue(row.getCell(26),
						100, "海关备案商品编号", map));
				goodsList.setLimitationGoodsCode(ExcelUtil.replaceValue(row
						.getCell(27), 20, "限制商品编码", map));
				goodsList.setBatchNumbers(ExcelUtil.replaceValue(row
						.getCell(28), 100, "批次号", map));
				
				if (ExcelUtil.rowIsNull(goodsList)) {
					return;
				}
				// goodsList.setListNo(String.valueOf(count));
				count++;

				list.add(goodsList);
				goods.setGoodsHead(goodsHead);
				goods.setGoodsList(list);
				if (rowNum == sheet.getLastRowNum()
						|| !ExcelUtil.replaceValue(
								sheet.getRow(rowNum).getCell(0), 30, "仓储代码",
								map, true).equals(
								ExcelUtil.replaceValue(sheet.getRow(rowNum + 1)
										.getCell(0), 30, "仓储代码", map, true))) {
					xmlMessageSender.sendMessage(goods, "EcssEnt");
					map.put("error", "success--------------------->已成功导入"
							+ (count) + "条商品备案\n");
					goods.setGoodsHead(null);
					list.clear();
				}
			} catch (Exception e) {
				throw new Exception("仓储代码：" + cbeCode + " " + e.getMessage());
			}
		}
	}

}