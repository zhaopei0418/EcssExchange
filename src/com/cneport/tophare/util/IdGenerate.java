/*
 * Copyright (c) 2011, 东方口岸科技有限公司
 * All rights reserved.
 * 文件名称：IdGenerate.java
 * 摘    要：
 * 版本：1.0
 * 作    者：陈乐佳
 * 创建日期：2011-12-1
 */
package com.cneport.tophare.util;

import java.util.UUID;
/**
 * UUID类
 *
 */
public class IdGenerate {

    /**
     * @Name:searchBusEntryPassedUndeal
     * @Description:生成UUID主键
     * @Version:V1.0
     * @Create Date:2011-12-1
     * @Paramters:空
     * @return UUID主键
     */
    public static String getUUIDString() {
        String id = UUID.randomUUID().toString();
        id = id.replace("-", "");
        return id;
    }
}
