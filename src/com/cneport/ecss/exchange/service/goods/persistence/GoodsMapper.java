package com.cneport.ecss.exchange.service.goods.persistence;

import java.util.Map;

import com.cneport.ecss.exchange.service.entity.GoodsHead;
import com.cneport.ecss.exchange.service.entity.GoodsList;

public abstract interface GoodsMapper
{
  public abstract void insertGoodsHead(GoodsHead paramGoodsHead);

  public abstract void insertGoodsList(GoodsList paramGoodsList);

  public abstract int countGoodsHead(Map<String, String> paramMap);

  public abstract int countGoodsList(String paramString);

  public abstract String queryHeadSEQ(Map<String, String> paramMap);
  
  public int queryGoodsList(Map<String,String> paramMap);
}