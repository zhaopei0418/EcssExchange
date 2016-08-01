package com.cneport.ecss.exchange.service.perDec.persistence;

import com.cneport.ecss.exchange.service.perDec.entity.PerDecHead;
import com.cneport.ecss.exchange.service.perDec.entity.PerDecList;
import java.util.Map;

public abstract interface PerDecMapper
{
  public abstract int countPerDecListNo(String paramString);

  public abstract void insertPerDecHead(PerDecHead paramPerDecHead);

  public abstract void insertPerDecList(PerDecList paramPerDecList);

  public abstract int countPerDecHead(Map<String, String> paramMap);
}