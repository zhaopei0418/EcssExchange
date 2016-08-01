package com.cneport.ecss.exchange.service.trans.persistence;

import com.cneport.ecss.exchange.service.entity.LogisticsInformation;
import java.util.Map;

public abstract interface TransMapper
{
  public abstract void insertTrans(LogisticsInformation logisticsInformation);

  public abstract int countTrans(Map<String, String> paramMap);
}