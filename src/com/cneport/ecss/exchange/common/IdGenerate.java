package com.cneport.ecss.exchange.common;

import java.util.UUID;

public class IdGenerate
{
  public static String getUUIDString()
  {
    String id = UUID.randomUUID().toString();

    id = id.replace("-", "");

    return id;
  }
}