package com.cneport.ecss.exchange.service.goods.persistence;

public interface GoodsMapper
{
	/*
  void insertGoodsHead(GoodsHead paramGoodsHead);

  void insertGoodsList(GoodsList paramGoodsList);

  int countGoodsHead(Map<String, String> paramMap);

  int countGoodsList(String paramString);

  String queryHeadSEQ(Map<String, String> paramMap);
  
  int queryGoodsList(Map<String,String> paramMap);
  */
	String getApplyCode(String cbeCode);
}