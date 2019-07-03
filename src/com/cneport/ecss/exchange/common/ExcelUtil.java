package com.cneport.ecss.exchange.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.cneport.tophare.util.Convert;

public class ExcelUtil {

    private static final Logger log = Logger.getLogger(ExcelUtil.class);

    public static String replaceValue(Cell cell) {
	String value = "";
	if (cell != null) {
	    /*if ("0".equals(cell.getStringCellValue())) {
		value = "";
	    } else */{
		value = cell.getStringCellValue();
		if (value != null) {
		    value = value.trim();
		}
	    }
	}
	return value;
    }

    public static Sheet cellTypeTOString(Sheet sheet) {
	for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
	    Row row = sheet.getRow(rowNum);
	    for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
		Cell cell = row.getCell(cellNum);
		if (cell != null) {
		    sheet.getRow(rowNum).getCell(cellNum)
			    .setCellType(Cell.CELL_TYPE_STRING);
		}
	    }
	}

	return sheet;
    }

    public static boolean rowIsNull(Object object) throws Exception {
	boolean isNull = true;
	Method metd = null;
	String fdname = null;

	Class clazz = object.getClass();// 获取集合中的对象类型
	Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组

	for (Field field : fds) {// 遍历该数组
	    fdname = field.getName();// 得到字段名，

	    metd = clazz.getMethod("get" + change(fdname), null);// 根据字段名找到对应的get方法，null表示无参数

	    if (metd != null) {// 比较是否在字段数组中存在name字段，如果不存在短路，如果存在继续判断该字段的get方法是否存在，同时存在继续执行
		Object value = metd.invoke(object, null);// 调用该字段的get方法
		if (!"".equals(value) && value != null) {
		    isNull = false;
		}
	    }
	}

	return isNull;
    }

    /**
     * @param src
     *            源字符串
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
     */
    public static String change(String src) {
	if (src != null) {
	    StringBuffer sb = new StringBuffer(src);
	    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
	    return sb.toString();
	} else {
	    return null;
	}
    }

    //
    // public static String replaceValue(Cell cell,int length,String columnName)
    // throws Exception{
    //
    // String value = "";
    // if (cell != null) {
    // if ("0".equals(cell.getStringCellValue())) {
    // value = "";
    // } else {
    // value = cell.getStringCellValue();
    // if (value != null) {
    // value = value.trim();
    // }
    // }
    // }
    //
    // if(value!=null){
    // if(value.getBytes().length>length){
    // throw new Exception(columnName+"超过长度");
    // }
    // }
    //
    // return value;
    // }

    public static String replaceValue(Cell cell, int length, String columnName,
	    Map<String, String> map) throws Exception {

	String value = "";
	if (cell != null) {
	    /*if ("0".equals(cell.getStringCellValue())) {
		value = "";
	    } else*/ {
		value = cell.getStringCellValue();
		if (value != null) {
		    value = value.trim().replace("'", "");
		}
	    }
	}

	if (value != null) {
	    if (value.getBytes().length > length) {
		map.put("success", "false");
		log.debug("当前长度: [" + value.getBytes().length + "] 限制长度: [" + length  + "] " + columnName + "超过长度");
		// 新增
		throw new Exception(columnName + "超过长度");
	    }
	}
	return value;
    }

    public static String replaceValue(Cell cell, int length, String columnName,
	    Map<String, String> map, boolean isRequired) throws Exception {
	String value = "";
	if (cell != null) {
	    /*if ("0".equals(cell.getStringCellValue())) {
		value = "";
	    } else*/ {
		value = cell.getStringCellValue();
		
		if (value != null) {
		    value = value.trim().replace("'", "");
		}
	    }
	}

	if (isRequired && "".equals(value)) {
	    throw new Exception(columnName + "为必填项！");
	}

	if (value != null) {
	    if (value.getBytes().length > length) {
		map.put("success", "false");
		log.debug(columnName + "超过长度");
		// 新增
		throw new Exception(columnName + "超过长度");
	    }
	}
	return value;

    }

	public static String replaceValue(Cell cell, int length, String columnName,
									  Map<String, String> map, boolean isRequired, boolean isNumber,
									boolean isFixed) throws Exception {
		if (isNumber) {
			Pattern pattern = null;
			if (isFixed) {
				pattern = Pattern.compile("^[0-9]{" + length + "}$");
			} else {
				pattern = Pattern.compile("^[0-9]{1," + length + "}$");
			}
			String value = ExcelUtil.replaceValue(cell);
			if (isRequired && "".equals(value)) {
				map.put("success", "false");
				throw new Exception(columnName + "为必填项！");
			}
			if (!"".equals(value)) {
				Matcher matcher = pattern.matcher(value);
				if (!matcher.find()) {
					map.put("success", "false");
					if (isFixed) {
						throw new Exception(columnName + "必须为" + length + "位的数字!");
					} else {
						throw new Exception(columnName + "必须为最大" + length + "位的数字!");
					}
				}
			}
		}

    	return ExcelUtil.replaceValue(cell, length, columnName, map, isRequired);
	}

    public static String replaceValue(Cell cell, int length, String columnName,
    	    Map<String, String> map, int maxPointLength, boolean isRequired) throws Exception {
    	String value = "";
    	if (cell != null) {
    	    /*if ("0".equals(cell.getStringCellValue())) {
    		value = "";
    	    } else*/ {
    		value = cell.getStringCellValue();
    		
    		if (value != null) {
    		    value = value.trim().replace("'", "");
    		}
    	    }
    	}

    	if (isRequired && "".equals(value)) {
    	    throw new Exception(columnName + "为必填项！");
    	}

    	if (value != null) {
    	    if (value.getBytes().length > length) {
    		map.put("success", "false");
    		log.debug(columnName + "超过长度");
    		// 新增
    		throw new Exception(columnName + "超过长度");
    	    }
    	}
    	
    	
    	
    	int pointLength = 0; 
    	
    	//
    	/*
    	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    	
    	BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
    	*/
    	
    	
    	if(value.indexOf(".")!=-1){
    		pointLength = value.length() - value.indexOf(".") - 1;
    		
    		if(pointLength>2){
    			map.put("success", "false");
        		log.debug(columnName + "小数位数过长");
        		// 新增
        		throw new Exception(columnName + "小数位数过长");
    		}
    	}
    	
    	//四舍五入
    	/*
    	int pointLength = 0; 
    	if(value.indexOf(".")!=-1){
    		double dbValue = Double.valueOf(value);
    		//DecimalFormat decimalFormat = new DecimalFormat("#.00");
    		//value = decimalFormat.format(Convert.toDecimal(value));
    		BigDecimal bigDecimal = new BigDecimal(dbValue);
    		double returnValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    		
    		value = Double.toString(returnValue);
    	}
    	*/
    	return value;

        }

//        public static void main(String[] argc) {
//			boolean isFixed = false;
//			int length = 3;
//			String value = "0";
//			Pattern pattern = null;
//			if (isFixed) {
//				pattern = Pattern.compile("^[0-9]{" + length + "}$");
//			} else {
//				pattern = Pattern.compile("^[0-9]{1," + length + "}$");
//			}
//			Matcher matcher = pattern.matcher(value);
//			System.out.println(matcher.find());
//		}
}