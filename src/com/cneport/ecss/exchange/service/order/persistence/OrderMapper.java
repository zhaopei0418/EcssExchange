package com.cneport.ecss.exchange.service.order.persistence;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.cneport.ecss.exchange.service.entity.OrderHead;
import com.cneport.ecss.exchange.service.entity.OrderList;
import com.cneport.ecss.exchange.service.entity.OrderPaymentLogistics;

@Transactional
public abstract interface OrderMapper
{
  public abstract int countOrderList(String paramString);

  public abstract void insertOrderHead(OrderHead paramOrderHead);

  public abstract void insertOrderList(OrderList paramOrderList);

  public abstract void insertOrderPayInfo(OrderPaymentLogistics paramOrderPayInfo);

  public abstract int queryOrderHead(Map<String, String> paramMap);
}