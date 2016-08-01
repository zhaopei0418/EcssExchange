package com.cneport.ecss.exchange.service.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.axis2.saaj.util.IDGenerator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cneport.ecss.exchange.common.ExcelUtil;
import com.cneport.ecss.exchange.service.entity.Order;
import com.cneport.ecss.exchange.service.entity.OrderHead;
import com.cneport.ecss.exchange.service.entity.OrderList;
import com.cneport.ecss.exchange.service.entity.OrderPaymentLogistics;
import com.cneport.ecss.exchange.service.order.persistence.OrderMapper;
import com.cneport.ecss.integration.XmlMessageSender;
import com.cneport.ecss.user.User;

@Service
public class OrderService {

	// @Autowired
	private OrderMapper orderMapper;

	@Autowired
	private XmlMessageSender xmlMessageSender;

	private static final Logger log = Logger.getLogger(OrderService.class);

	@Transactional
	public void impOrder(Sheet sheet, Map<String, String> map) throws Exception {
		sheet = ExcelUtil.cellTypeTOString(sheet);
		String uuid = IDGenerator.generateID();
		Order order = new Order();
		OrderHead orderHead = new OrderHead();
		OrderPaymentLogistics orderPaymentLogistics = new OrderPaymentLogistics();
		List<OrderList> list = new ArrayList<OrderList>();
		int count = 1;

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (rowNum == 1) {

				map.put("error", map.get("error") + "订单表头:\n");

				orderHead.setSeqNo(uuid);
				orderHead.setCbeCode(ExcelUtil.replaceValue(row.getCell(0), 40,
						"电商编码", map, true));
				orderHead.setCbeName(ExcelUtil.replaceValue(row.getCell(1),
						100, "电商名称", map, true));
				orderHead.setEcpCode(ExcelUtil.replaceValue(row.getCell(2), 40,
						"电商平台代码", map, true));
				orderHead.setEcpName(ExcelUtil.replaceValue(row.getCell(3),
						100, "电商平台名称", map, true));

				orderHead.setCustomer(ExcelUtil.replaceValue(row.getCell(4),
						60, "客户姓名", map));

				// 新增
				if ("I".equals(ExcelUtil.replaceValue(row.getCell(22), 1,
						"进出口标志", map, true))) {
					orderHead.setIdType(ExcelUtil.replaceValue(row.getCell(5),
							1, "证件类型", map, true));
					orderHead.setCustomerId(ExcelUtil.replaceValue(row
							.getCell(6), 30, "证件号码", map, true));
				} else {
					orderHead.setIdType(ExcelUtil.replaceValue(row.getCell(5),
							1, "证件类型", map));
					orderHead.setCustomerId(ExcelUtil.replaceValue(row
							.getCell(6), 30, "证件号码", map));
				}

				orderHead.setOrderNo(ExcelUtil.replaceValue(row.getCell(7), 30,
						"订单编号", map, true));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(8)))) {
					orderHead.setCharge(new BigDecimal(0));
				} else {
					orderHead.setCharge(new BigDecimal(ExcelUtil.replaceValue(
							row.getCell(8), 19, "总费用", map)));
				}

				if ("".equals(ExcelUtil.replaceValue(row.getCell(9)))) {
					orderHead.setGoodsValue(new BigDecimal(0));
				} else {
					orderHead.setGoodsValue(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(9), 19, "货值", map)));
				}

				if ("".equals(ExcelUtil.replaceValue(row.getCell(10)))) {
					orderHead.setFreight(new BigDecimal(0));
				} else {
					orderHead.setFreight(new BigDecimal(ExcelUtil.replaceValue(
							row.getCell(10), 19, "运费", map)));
				}
				if ("".equals(ExcelUtil.replaceValue(row.getCell(11)))) {
					orderHead.setOther(new BigDecimal(0));
				} else {
					orderHead.setOther(new BigDecimal(ExcelUtil.replaceValue(
							row.getCell(11), 19, "其他费用", map)));
				}
				if ("".equals(ExcelUtil.replaceValue(row.getCell(12)))) {
					orderHead.setTax(new BigDecimal(0));
				} else {
					orderHead.setTax(new BigDecimal(ExcelUtil.replaceValue(row
							.getCell(12), 19, "进口行邮税", map)));
				}

				orderHead.setNote(ExcelUtil.replaceValue(row.getCell(13), 1000,
						"备注", map));
				orderHead.setShipper(ExcelUtil.replaceValue(row.getCell(14),
						60, "发货人姓名", map));
				orderHead.setShipperAddress(ExcelUtil.replaceValue(row
						.getCell(15), 100, "发货人地址", map));
				orderHead.setShipperTelephone(ExcelUtil.replaceValue(row
						.getCell(16), 20, "发货人电话", map));
				orderHead.setShipperCountry(ExcelUtil.replaceValue(row
						.getCell(17), 3, "发货人所在国", map));
				orderHead.setConsignee(ExcelUtil.replaceValue(row.getCell(18),
						60, "收货人姓名", map, true));
				orderHead.setConsigneeAddress(ExcelUtil.replaceValue(row
						.getCell(19), 100, "收货人地址", map, true));
				orderHead.setConsigneeTelephone(ExcelUtil.replaceValue(row
						.getCell(20), 20, "收货人电话", map, true));
				orderHead.setConsigneeCountry(ExcelUtil.replaceValue(row
						.getCell(21), 3, "收货人所在国", map, true));
				orderHead.setIeType(ExcelUtil.replaceValue(row.getCell(22)));

				// orderHead.setIdType("1");// 证件类型

				orderHead.setCurrency("");// 币制

				// 新增
				orderHead.setBatchNumbers(ExcelUtil.replaceValue(row
						.getCell(23), 100, "批次号", map));
				orderHead.setTotalLogisticsNo(ExcelUtil.replaceValue(row
						.getCell(24), 24, "总运单号", map));

				orderHead.setTradeCountry(ExcelUtil.replaceValue(row
						.getCell(25), 3, "贸易国别", map));
				orderHead.setAgentCode(ExcelUtil.replaceValue(row.getCell(26),
						40, "代理企业", map, true));
				orderHead.setAgentName(ExcelUtil.replaceValue(row.getCell(27),
						100, "代理企业名称", map, true));

				// 设置运单关联订单所需的字段
				// map.put("orderNo", orderHead.getOrderNo());
				// map.put("ecpCode", orderHead.getEcpCode());
				// map.put("idType", orderHead.getIdType());
				// map.put("customerId", orderHead.getCustomerId());

			} else if (rowNum == 3) {
				map.put("error", map.get("error") + "订单支付物流信息:\n");

				orderPaymentLogistics.setSeqNo(uuid);
				orderPaymentLogistics.setPaymentCode(ExcelUtil.replaceValue(row
						.getCell(0), 10, "支付企业代码", map));
				orderPaymentLogistics.setPaymentName(ExcelUtil.replaceValue(row
						.getCell(1), 100, "支付企业名称", map));
				orderPaymentLogistics.setPaymentType(ExcelUtil.replaceValue(row
						.getCell(2), 1, "支付类型", map));
				orderPaymentLogistics.setPaymentNo(ExcelUtil.replaceValue(row
						.getCell(3), 40, "支付交易号", map));
				orderPaymentLogistics.setLogisticsCode(ExcelUtil.replaceValue(
						row.getCell(4), 10, "物流企业代码", map, true));
				orderPaymentLogistics.setLogisticsName(ExcelUtil.replaceValue(
						row.getCell(5), 100, "物流企业名称", map, true));
				orderPaymentLogistics.setLogisticsNo(ExcelUtil.replaceValue(row
						.getCell(6), 20, "物流运单号", map));
				orderPaymentLogistics.setTrackNo(ExcelUtil.replaceValue(row
						.getCell(7), 20, "物流跟踪号", map));
			} else if (rowNum >= 5) {
				map.put("error", map.get("error") + "订单表体[" + count + "]:\n");

				OrderList orderList = new OrderList();
				orderList.setSeqNo(uuid);
				orderList.setItemNo(ExcelUtil.replaceValue(row.getCell(0), 100,
						"备案商品编号", map));
				orderList.setGoodsNo(ExcelUtil.replaceValue(row.getCell(1), 20,
						"商品货号", map, true));
				orderList.setCodeTs(ExcelUtil.replaceValue(row.getCell(2), 14,
						"HS编码", map));
				orderList.setShelfGoodsName(ExcelUtil.replaceValue(row
						.getCell(3), 250, "商品上架名称", map, true));
				orderList.setGoodsModel(ExcelUtil.replaceValue(row.getCell(4),
						250, "规格型号", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(5)))) {
					orderList.setPrice(new BigDecimal(0));
				} else {
					orderList.setPrice(new BigDecimal(ExcelUtil.replaceValue(
							row.getCell(5), 19, "成交单价", map)));
				}
				orderList.setCurrency(ExcelUtil.replaceValue(row.getCell(6), 3,
						"币制", map, true));
				orderList.setQuantity(new BigDecimal(ExcelUtil.replaceValue(row
						.getCell(7), 19, "数量", map, true)));
				orderList.setUnit(ExcelUtil.replaceValue(row.getCell(8), 10,
						"计量单位", map, true));
				orderList.setCountry(ExcelUtil.replaceValue(row.getCell(9), 3,
						"原产国", map));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(10)))) {
					orderList.setDiscount(new BigDecimal(0));
				} else {
					orderList.setDiscount(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(10), 9, "折扣优惠", map)));
				}
				orderList.setFlag(ExcelUtil.replaceValue(row.getCell(11), 1,
						"是否赠品", map));
				// orderList.setCodeTs(ExcelUtil.replaceValue(row.getCell(12),
				// 8,
				// "商品条形码", map));
				orderList.setTaxCode(ExcelUtil.replaceValue(row.getCell(13), 8,
						"行邮税号", map));
				// 新增
				orderList.setDescribe(ExcelUtil.replaceValue(row.getCell(14),
						250, "商品描述", map));
				orderList.setGoodsName(ExcelUtil.replaceValue(row.getCell(15),
						250, "申报品名", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(16)))) {
					orderList.setPriceTotal(new BigDecimal(0));
				} else {
					orderList.setPriceTotal(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(16), 19, "成交总价", map)));
				}

				orderList.setPurposeCode(ExcelUtil.replaceValue(
						row.getCell(17), 10, "用途", map));
				orderList.setWasteMaterials(ExcelUtil.replaceValue(row
						.getCell(18), 10, "废旧物品", map, true));

				/*
				 * if("".equals(ExcelUtil.replaceValue(row.getCell(9)))){
				 * orderList.setPriceTotal(new BigDecimal(0)); }else{
				 * orderList.setPriceTotal(new
				 * BigDecimal(ExcelUtil.replaceValue(
				 * row.getCell(6),19,"总价",map))); }
				 * orderList.setGoodsName(ExcelUtil
				 * .replaceValue(row.getCell(14),250,"申报品名",map));
				 * orderList.setDescribe
				 * (ExcelUtil.replaceValue(row.getCell(3),250,"商品描述",map));
				 */
				if (ExcelUtil.rowIsNull(orderList)) {
					return;
				}
				orderList.setListNo(String.valueOf(count));
				count++;

				// 订单表头的币制这个字段，从表体第一条带入
				if (rowNum == 5) {
					orderHead.setCurrency(orderList.getCurrency());
				}

				list.add(orderList);
			}
		}
		order.setOrderHead(orderHead);
		order.setOrderPaymentLogistics(orderPaymentLogistics);
		order.setOrderList(list);
		// xmlMessageSender.sendMessage(order, "EcssEnt");
		xmlMessageSender.sendMessage(order, "EcssCus");
		map.put("error", map.get("error") + "success--------------------->订单:"
				+ order.getOrderHead().getOrderNo() + "已成功导入\n");
	}

	@Transactional
	public void impOrderBatch(Sheet sheet, Map<String, String> map, User user)
			throws Exception {
		sheet = ExcelUtil.cellTypeTOString(sheet);
		String orgId = user.getOrgId();
		//map.put("error", map.get("error") + "=========导入订单信息=========\n");
		Order order = null;
		OrderHead orderHead = null;
		OrderPaymentLogistics orderPaymentLogistics = null;
		List<OrderList> list = new ArrayList<OrderList>();
		int count = 1;
		String orderNo = null;
		// StringBuffer orderNoStr = new StringBuffer("");
		int amount = 0;
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			if (!orgId.equals(ExcelUtil.replaceValue(sheet.getRow(rowNum)
					.getCell(0), 40, "电商编码", map, true))
					&& !orgId
							.equals(ExcelUtil.replaceValue(sheet.getRow(rowNum)
									.getCell(26), 40, "代理企业", map, true))) {
				throw new Exception("您导入的不是您代理企业的数据或者您自己企业的数据！！\n错误数据的行数："+rowNum);
			}
		}
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {

			String uuid = IDGenerator.generateID();
			Row row = sheet.getRow(rowNum);

			try {
				if (rowNum == 1
						|| !ExcelUtil.replaceValue(
								sheet.getRow(rowNum).getCell(7), 30, "订单编号",
								map, true).equals(
								ExcelUtil.replaceValue(sheet.getRow(rowNum - 1)
										.getCell(7)))) {
					order = new Order();
					orderHead = new OrderHead();
					orderPaymentLogistics = new OrderPaymentLogistics();

					orderNo = ExcelUtil.replaceValue(row.getCell(7), 30,
							"订单编号", map, true);

					// 拼接订单编号
					// orderNoStr.append(orderNo + ",");

					orderHead.setSeqNo(uuid);
					orderHead.setCbeCode(ExcelUtil.replaceValue(row.getCell(0),
							40, "电商编码", map, true));
					orderHead.setCbeName(ExcelUtil.replaceValue(row.getCell(1),
							100, "电商名称", map, true));
					orderHead.setEcpCode(ExcelUtil.replaceValue(row.getCell(2),
							40, "电商平台代码", map,true));
					orderHead.setEcpName(ExcelUtil.replaceValue(row.getCell(3),
							100, "电商平台名称", map, true));

					orderHead.setCustomer(ExcelUtil.replaceValue(
							row.getCell(4), 60, "客户姓名", map));
					// 新增
					if ("I".equals(ExcelUtil.replaceValue(row.getCell(22), 1,
							"进出口标志", map, true))) {
						orderHead.setIdType(ExcelUtil.replaceValue(row
								.getCell(5), 1, "证件类型", map, true));
						orderHead.setCustomerId(ExcelUtil.replaceValue(row
								.getCell(6), 30, "证件号码", map, true));
					} else {
						orderHead.setIdType(ExcelUtil.replaceValue(row
								.getCell(5), 1, "证件类型", map));
						orderHead.setCustomerId(ExcelUtil.replaceValue(row
								.getCell(6), 30, "证件号码", map));
					}

					orderHead.setOrderNo(orderNo);
					if ("".equals(ExcelUtil.replaceValue(row.getCell(8)))) {
						orderHead.setCharge(new BigDecimal(0));
					} else {
						orderHead.setCharge(new BigDecimal(ExcelUtil
								.replaceValue(row.getCell(8), 19, "总费用", map)));
					}
					if ("".equals(ExcelUtil.replaceValue(row.getCell(9)))) {
						orderHead.setGoodsValue(new BigDecimal(0));

					} else {
						orderHead.setGoodsValue(new BigDecimal(ExcelUtil
								.replaceValue(row.getCell(9), 19, "货值", map)));

					}
					if ("".equals(ExcelUtil.replaceValue(row.getCell(10)))) {
						orderHead.setFreight(new BigDecimal(0));
					} else {
						orderHead.setFreight(new BigDecimal(ExcelUtil
								.replaceValue(row.getCell(10), 19, "运费", map)));
					}
					if ("".equals(ExcelUtil.replaceValue(row.getCell(11)))) {
						orderHead.setOther(new BigDecimal(0));
					} else {
						orderHead
								.setOther(new BigDecimal(ExcelUtil
										.replaceValue(row.getCell(11), 19,
												"其他费用", map)));
					}
					if ("".equals(ExcelUtil.replaceValue(row.getCell(12)))) {
						orderHead.setTax(new BigDecimal(0));
					} else {
						orderHead.setTax(new BigDecimal(ExcelUtil.replaceValue(
								row.getCell(12), 19, "进口行邮税", map)));
					}
					orderHead.setNote(ExcelUtil.replaceValue(row.getCell(13),
							1000, "备注", map));
					orderHead.setShipper(ExcelUtil.replaceValue(
							row.getCell(14), 60, "发货人姓名", map));
					orderHead.setShipperAddress(ExcelUtil.replaceValue(row
							.getCell(15), 100, "发货人地址", map));
					orderHead.setShipperTelephone(ExcelUtil.replaceValue(row
							.getCell(16), 20, "发货人电话", map));
					orderHead.setShipperCountry(ExcelUtil.replaceValue(row
							.getCell(17), 3, "发货人所在国", map));
					orderHead.setConsignee(ExcelUtil.replaceValue(row
							.getCell(18), 60, "收货人姓名", map, true));
					orderHead.setConsigneeAddress(ExcelUtil.replaceValue(row
							.getCell(19), 100, "收货人地址", map, true));
					orderHead.setConsigneeTelephone(ExcelUtil.replaceValue(row
							.getCell(20), 20, "收货人电话", map, true));
					orderHead.setConsigneeCountry(ExcelUtil.replaceValue(row
							.getCell(21), 3, "收货人所在国", map, true));
					orderHead.setIeType(ExcelUtil.replaceValue(row.getCell(22),
							1, "进出口标志", map, true));
					// orderHead.setIdType("1");// 证件类型
					orderHead.setCurrency("");// 币制
					// 新增
					orderHead.setBatchNumbers(ExcelUtil.replaceValue(row
							.getCell(23), 100, "批次号", map));
					orderHead.setTotalLogisticsNo(ExcelUtil.replaceValue(row
							.getCell(24), 24, "总运单号", map));
					orderHead.setTradeCountry(ExcelUtil.replaceValue(row
							.getCell(25), 3, "贸易国别", map));
					orderHead.setAgentCode(ExcelUtil.replaceValue(row
							.getCell(26), 40, "代理企业", map, true));
					orderHead.setAgentName(ExcelUtil.replaceValue(row
							.getCell(27), 100, "代理企业名称", map, true));
					orderHead.setModifyMark(ExcelUtil.replaceValue(row
				            .getCell(28), 100, "操作类型", map, true));
					orderPaymentLogistics.setSeqNo(uuid);
					orderPaymentLogistics.setPaymentCode(ExcelUtil
							.replaceValue(row.getCell(29), 10, "支付企业代码", map));
					orderPaymentLogistics.setPaymentName(ExcelUtil
							.replaceValue(row.getCell(30), 100, "支付企业名称", map));
					orderPaymentLogistics.setPaymentType(ExcelUtil
							.replaceValue(row.getCell(31), 1, "支付类型", map));
					orderPaymentLogistics.setPaymentNo(ExcelUtil.replaceValue(
							row.getCell(32), 40, "支付交易号", map));
					orderPaymentLogistics.setLogisticsCode(ExcelUtil
							.replaceValue(row.getCell(33), 10, "物流企业代码", map,
									true));
					orderPaymentLogistics.setLogisticsName(ExcelUtil
							.replaceValue(row.getCell(34), 100, "物流企业名称", map,
									true));
					orderPaymentLogistics.setLogisticsNo(ExcelUtil
							.replaceValue(row.getCell(35), 20, "物流运单号", map));
					orderPaymentLogistics.setTrackNo(ExcelUtil.replaceValue(row
							.getCell(36), 20, "物流跟踪号", map));

					// order.setOrderHead(orderHead);
					// order.setOrderPaymentLogistics(orderPaymentLogistics);

				}
				OrderList orderList = new OrderList();
				orderList.setSeqNo(uuid);
				orderList.setItemNo(ExcelUtil.replaceValue(row.getCell(37),
						100, "备案商品编号", map));
				orderList.setGoodsNo(ExcelUtil.replaceValue(row.getCell(38),
						20, "商品货号", map, true));
				orderList.setCodeTs(ExcelUtil.replaceValue(row.getCell(39), 14,
						"HS编码", map));
				orderList.setShelfGoodsName(ExcelUtil.replaceValue(row
						.getCell(40), 250, "商品上架名称", map, true));
				orderList.setGoodsModel(ExcelUtil.replaceValue(row.getCell(41),
						250, "规格型号", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(42)))) {
					orderList.setPrice(new BigDecimal(0));
				} else {
					orderList.setPrice(new BigDecimal(ExcelUtil.replaceValue(
							row.getCell(42), 19, "成交单价", map)));
				}
				orderList.setCurrency(ExcelUtil.replaceValue(row.getCell(43),
						3, "币制", map, true));
				orderList.setQuantity(new BigDecimal(ExcelUtil.replaceValue(row
						.getCell(44), 19, "数量", map, true)));
				orderList.setUnit(ExcelUtil.replaceValue(row.getCell(45), 10,
						"计量单位", map, true));
				orderList.setCountry(ExcelUtil.replaceValue(row.getCell(46), 3,
						"原产国", map));
				if ("".equals(ExcelUtil.replaceValue(row.getCell(47)))) {
					orderList.setDiscount(new BigDecimal(0));
				} else {
					orderList.setDiscount(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(47), 9, "折扣优惠", map)));
				}
				orderList.setFlag(ExcelUtil.replaceValue(row.getCell(48), 1,
						"是否赠品", map));

				// orderList.setCodeTs(ExcelUtil.replaceValue(row.getCell(48),
				// 8,
				// "商品条形码", map));

				orderList.setTaxCode(ExcelUtil.replaceValue(row.getCell(49), 8,
						"行邮税号", map));
				// 新增
				orderList.setDescribe(ExcelUtil.replaceValue(row.getCell(50),
						250, "商品描述", map));
				orderList.setGoodsName(ExcelUtil.replaceValue(row.getCell(51),
						250, "申报品名", map));

				if ("".equals(ExcelUtil.replaceValue(row.getCell(52)))) {
					orderList.setPriceTotal(new BigDecimal(0));
				} else {
					orderList.setPriceTotal(new BigDecimal(ExcelUtil
							.replaceValue(row.getCell(52), 19, "成交总价", map, true)));
				}
				orderList.setPurposeCode(ExcelUtil.replaceValue(
						row.getCell(53), 10, "用途", map));
				orderList.setWasteMaterials(ExcelUtil.replaceValue(row
						.getCell(54), 10, "废旧物品", map, true));

				if (ExcelUtil.rowIsNull(orderList)) {
					return;
				}
				orderList.setListNo(String.valueOf(count));
				count++;

				list.add(orderList);
				order.setOrderList(list);
				if (list.size() > 0) {
					orderHead.setCurrency(list.get(0).getCurrency());
				}
				order.setOrderHead(orderHead);
				order.setOrderPaymentLogistics(orderPaymentLogistics);
				// xmlMessageSender.sendMessage(order, "EcssEnt");

				if (rowNum == sheet.getLastRowNum()
						|| !ExcelUtil.replaceValue(
								sheet.getRow(rowNum).getCell(7), 30, "订单编号",
								map, true).equals(
								ExcelUtil.replaceValue(sheet.getRow(rowNum + 1)
										.getCell(7), 30, "订单编号", map, true))) {
					xmlMessageSender.sendMessage(order, "EcssCus");
					amount++;
					order.setOrderHead(null);
					order.setPaymentInformation(null);
					list.clear();
					map.put("error", map.get("error") + "success--------------------->已成功导入"+ amount + "条订单\n");
				}
			} catch (Exception e) {
				throw new Exception("订单：" + orderNo + " " + e.getMessage());
			}
		}

	}
}